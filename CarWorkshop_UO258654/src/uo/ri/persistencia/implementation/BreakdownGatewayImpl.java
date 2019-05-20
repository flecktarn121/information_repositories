package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.BreakdownDTO;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.BreakdownGateway;

public class BreakdownGatewayImpl implements BreakdownGateway {
	Configuration conf = Configuration.getInstance();
	Connection connection;

	@Override
	public double consultaImporteManoObra(Long idAveria) throws SQLException {
		ResultSet rs = null;
		try (PreparedStatement pst = connection.prepareStatement(conf.getProperty("SQL_IMPORTE_MANO_OBRA"))) {
			rs = pst.executeQuery();
			pst.setLong(1, idAveria);
			if (rs.next() == false) {
				throw new RuntimeException("La averia no existe o no se puede facturar");
			}

			return rs.getDouble(1);

		} catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
		}

	}

	@Override
	public void actualizarImporteAveria(BreakdownDTO dto) throws SQLException {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(conf.getProperty("SQL_UPDATE_IMPORTE_AVERIA"));
			pst.setDouble(1, dto.ammount);
			pst.setLong(2, dto.id);
			pst.executeUpdate();
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void cambiarEstadoAverias(List<Long> idsAveria, String status) throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(conf.getProperty("SQL_ACTUALIZAR_ESTADO_AVERIA"));

			for (Long idAveria : idsAveria) {
				pst.setString(1, status);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void verificarAveriasTerminadas(List<Long> idsAveria) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(conf.getProperty("SQL_VERIFICAR_ESTADO_AVERIA"));

			for (Long idAveria : idsAveria) {
				pst.setLong(1, idAveria);

				rs = pst.executeQuery();
				if (rs.next() == false) {
					throw new RuntimeException("No existe la averia " + idAveria);
				}

				String status = rs.getString(1);
				if (!"TERMINADA".equalsIgnoreCase(status)) {
					throw new RuntimeException("No está terminada la avería " + idAveria);
				}

				rs.close();
			}
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(conf.getProperty("SQL_VINCULAR_AVERIA_FACTURA"));

			for (Long idAveria : idsAveria) {
				pst.setLong(1, idFactura);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;

	}

	@Override
	public double getImportePorMecanicoYFecha(Long mechanicId, Date date) {
		double importe=0;
		PreparedStatement pst = null;
		try(Connection conn =Jdbc.getConnection();) {
			
			pst = conn.prepareStatement(conf.getProperty("SQL_AVERIAS_MECANICO_FECHA"));

			Date startMonth =Date.valueOf(date.toLocalDate().withDayOfMonth(1));
			pst.setLong(1, mechanicId);
			pst.setDate(2, startMonth);
			pst.setDate(3, date);
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			importe = rs.getDouble(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
		return importe;
	}

}
