package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.BreakdownDTO;
import uo.ri.bussiness.dto.InvoiceDTO;
import uo.ri.configuration.Configuration;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.BreakdownGateway;
import uo.ri.persistencia.InvoiceGateway;

public class InvoiceGatewayImpl implements InvoiceGateway {
	Configuration conf = Configuration.getInstance();
	Connection connection;

	@Override
	public Long generarNuevoNumeroFactura() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			Connection connection = Jdbc.getConnection();
			pst = connection.prepareStatement(conf.getProperty("SQL_ULTIMO_NUMERO_FACTURA"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, el siguiente
			} else { // todavía no hay ninguna
				return 1L;
			}
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public long getGeneratedKey(long numeroFactura) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			Connection connection = Jdbc.getConnection();
			pst = connection.prepareStatement(conf.getProperty("SQL_RECUPERAR_CLAVE_GENERADA"));
			pst.setLong(1, numeroFactura);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public long crearFactura(InvoiceDTO dto) throws SQLException {
		PreparedStatement pst = null;

		try {
			Connection connection = Jdbc.getConnection();
			pst = connection.prepareStatement(conf.getProperty("SQL_INSERTAR_FACTURA"));
			pst.setLong(1, dto.numeroFactura);
			pst.setDate(2, new java.sql.Date(dto.fechaFactura.getTime()));
			pst.setDouble(3, dto.iva);
			pst.setDouble(4, dto.totalConIva);
			pst.setString(5, "SIN_ABONAR");

			pst.executeUpdate();

			return getGeneratedKey(dto.numeroFactura); // Id de la nueva factura generada

		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void facturarResparacion(List<Long> idsAveria, InvoiceDTO dto) {
		try {
			connection = Jdbc.getConnection();
			connection.setAutoCommit(false);

			verificarAveriasTerminadas(idsAveria);

			long numeroFactura = generarNuevoNumeroFactura();
			Date fechaFactura = Dates.today();
			double totalFactura = calcularImportesAverias(idsAveria);
			double iva = porcentajeIva(totalFactura, fechaFactura);
			double importe = totalFactura * (1 + iva / 100);
			importe = Round.twoCents(importe);

			long idFactura = crearFactura(numeroFactura, fechaFactura, iva, importe);
			vincularAveriasConFactura(idFactura, idsAveria);
			cambiarEstadoAverias(idsAveria, "FACTURADA");

			dto.numeroFactura = numeroFactura;
			dto.fechaFactura = fechaFactura;
			dto.totalFactura = totalFactura;
			dto.iva = iva;
			dto.totalConIva = importe;

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			;
			throw new RuntimeException(e);
		} catch (BusinessException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			;
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}

	}

	private void cambiarEstadoAverias(List<Long> idsAveria, String status) throws SQLException {

		BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
		bdGateway.setConnection(connection);
		bdGateway.cambiarEstadoAverias(idsAveria, status);
	}

	private void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) throws SQLException {

		BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
		bdGateway.setConnection(connection);
		bdGateway.vincularAveriasConFactura(idFactura, idsAveria);
	}

	private long crearFactura(long numeroFactura, Date fechaFactura, double iva, double totalConIva)
			throws SQLException {
		InvoiceDTO dto = new InvoiceDTO();
		dto.numeroFactura = numeroFactura;
		dto.fechaFactura = fechaFactura;
		dto.iva = iva;
		dto.totalConIva = totalConIva;
		return crearFactura(dto);
	}

	private double porcentajeIva(double totalFactura, Date fechaFactura) {
		return Dates.fromString("1/7/2012").before(fechaFactura) ? 21.0 : 18.0;
	}

	protected double calcularImportesAverias(List<Long> idsAveria) throws BusinessException, SQLException {

		double totalFactura = 0.0;
		for (Long idAveria : idsAveria) {
			double importeManoObra = consultaImporteManoObra(idAveria);
			double importeRepuestos = consultaImporteRepuestos(idAveria);
			double totalAveria = importeManoObra + importeRepuestos;

			actualizarImporteAveria(idAveria, totalAveria);

			totalFactura += totalAveria;
		}
		return totalFactura;
	}

	private void actualizarImporteAveria(Long idAveria, double totalAveria) throws SQLException {
		BreakdownDTO dto = new BreakdownDTO();
		dto.id = idAveria;
		dto.ammount = totalAveria;
		BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
		bdGateway.setConnection(connection);
		bdGateway.actualizarImporteAveria(dto);
	}

	private double consultaImporteRepuestos(Long idAveria) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(conf.getProperty("SQL_IMPORTE_REPUESTOS"));
			pst.setLong(1, idAveria);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0; // La averia puede no tener repuestos
			}

			return rs.getDouble(1);

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private double consultaImporteManoObra(Long idAveria) throws BusinessException, SQLException {
		BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
		bdGateway.setConnection(connection);
		return bdGateway.consultaImporteManoObra(idAveria);

	}

	private void verificarAveriasTerminadas(List<Long> idsAveria) throws SQLException, BusinessException {
		try {
			BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
			bdGateway.setConnection(connection);
			bdGateway.verificarAveriasTerminadas(idsAveria);
		} catch (RuntimeException e) {
			throw new BusinessException(e);
		}

	}

}
