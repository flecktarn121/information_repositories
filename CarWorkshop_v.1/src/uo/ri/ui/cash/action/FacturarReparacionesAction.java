package uo.ri.ui.cash.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;

public class FacturarReparacionesAction implements Action {

	private static final String SQL_IMPORTE_REPUESTOS = 
		"select sum(s.cantidad * r.precio) " +
		"	from  TSustituciones s, TRepuestos r " +
		"	where s.repuesto_id = r.id " +
		"		and s.intervencion_averia_id = ?";
	
	private static final String SQL_IMPORTE_MANO_OBRA =
		"select sum(i.minutos * tv.precioHora / 60) " +
		"	from TAverias a, TIntervenciones i, TVehiculos v, TTiposVehiculo tv" +
		"	where i.averia_id = a.id " +
		"		and a.vehiculo_id = v.id" +
		"		and v.tipo_id = tv.id" +
		"		and a.id = ?" +
		"		and a.status = 'TERMINADA'";

	private static final String SQL_UPDATE_IMPORTE_AVERIA = 
		"update TAverias set importe = ? where id = ?";

	private static final String SQL_ULTIMO_NUMERO_FACTURA = 
		"select max(numero) from TFacturas";

	private static final String SQL_INSERTAR_FACTURA = 
		"insert into TFacturas(numero, fecha, iva, importe, status) " +
		"	values(?, ?, ?, ?, ?)";

	private static final String SQL_VINCULAR_AVERIA_FACTURA = 
		"update TAverias set factura_id = ? where id = ?";

	private static final String SQL_ACTUALIZAR_ESTADO_AVERIA = 
		"update TAverias set status = ? where id = ?";

	private static final String SQL_VERIFICAR_ESTADO_AVERIA = 
		"select status from TAverias where id = ?";

	private static final String SQL_RECUPERAR_CLAVE_GENERADA = 
		"select id from TFacturas where numero = ?";

	private Connection connection;	
	
	@Override
	public void execute() throws BusinessException {
		List<Long> idsAveria = new ArrayList<Long>();
		
		// pedir las averias a incluir en la factura
		do {
			Long id = Console.readLong("ID de averia");
			idsAveria.add(id);
		} while ( masAverias() );

		try {
			connection = Jdbc.getConnection();
			connection.setAutoCommit(false);
			
			verificarAveriasTerminadas(idsAveria);

			long numeroFactura = generarNuevoNumeroFactura();
			Date fechaFactura = Dates.today();
			double totalFactura = calcularImportesAverias(idsAveria);
			double iva = porcentajeIva(totalFactura, fechaFactura);
			double importe = totalFactura * (1 + iva/100);
			importe = Round.twoCents(importe);
			
			long idFactura = crearFactura(numeroFactura, fechaFactura, iva, importe);
			vincularAveriasConFactura(idFactura, idsAveria);
			cambiarEstadoAverias(idsAveria, "FACTURADA");
			
			mostrarFactura(numeroFactura, fechaFactura, totalFactura, iva, importe);
 
			connection.commit();
		}
		catch (SQLException e) {
			try { connection.rollback(); } catch (SQLException ex) {};
			throw new RuntimeException(e);
		}
		catch (BusinessException e) {
			try { connection.rollback(); } catch (SQLException ex) {};
			throw e;
		}
		finally {
			Jdbc.close(connection);
		}

	}

	private void mostrarFactura(long numeroFactura, Date fechaFactura,
			double totalFactura, double iva, double totalConIva) {
		
		Console.printf("Factura nº: %d\n", numeroFactura);
		Console.printf("\tFecha: %1$td/%1$tm/%1$tY\n", fechaFactura);
		Console.printf("\tTotal: %.2f €\n", totalFactura);
		Console.printf("\tIva: %.1f %% \n", iva);
		Console.printf("\tTotal con IVA: %.2f €\n", totalConIva);
	}

	private void verificarAveriasTerminadas(List<Long> idsAveria) throws SQLException, BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(SQL_VERIFICAR_ESTADO_AVERIA);
			
			for (Long idAveria : idsAveria) {
				pst.setLong(1, idAveria);
				
				rs = pst.executeQuery();
				if (rs.next() == false) {
					throw new BusinessException("No existe la averia " + idAveria);
				}
				
				String status = rs.getString(1); 
				if (! "TERMINADA".equalsIgnoreCase(status) ) {
					throw new BusinessException("No está terminada la avería " + idAveria);
				}
				
				rs.close();
			}
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	private void cambiarEstadoAverias(List<Long> idsAveria, String status) throws SQLException {
		
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_ACTUALIZAR_ESTADO_AVERIA);
			
			for (Long idAveria : idsAveria) {
				pst.setString(1, status);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	private void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) throws SQLException {
		
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_VINCULAR_AVERIA_FACTURA);

			for (Long idAveria : idsAveria) {
				pst.setLong(1, idFactura);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	private long crearFactura(long numeroFactura, Date fechaFactura,
			double iva, double totalConIva) throws SQLException {
		
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(SQL_INSERTAR_FACTURA);
			pst.setLong(1, numeroFactura);
			pst.setDate(2, new java.sql.Date(fechaFactura.getTime()));
			pst.setDouble(3, iva);
			pst.setDouble(4, totalConIva);
			pst.setString(5, "SIN_ABONAR");

			pst.executeUpdate();

			return getGeneratedKey(numeroFactura); // Id de la nueva factura generada
			
		} finally {
			Jdbc.close(pst);
		}
	}

	private long getGeneratedKey(long numeroFactura) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(SQL_RECUPERAR_CLAVE_GENERADA);
			pst.setLong(1, numeroFactura);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);
			
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private Long generarNuevoNumeroFactura() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(SQL_ULTIMO_NUMERO_FACTURA);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, el siguiente
			} else {  // todavía no hay ninguna
				return 1L;
			}
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	private double porcentajeIva(double totalFactura, Date fechaFactura) {
		return Dates.fromString("1/7/2012").before(fechaFactura) ? 21.0 : 18.0;
	}

	protected double calcularImportesAverias(List<Long> idsAveria)
			throws BusinessException, SQLException {
		
		double totalFactura = 0.0;
		for(Long idAveria : idsAveria) {
			double importeManoObra = consultaImporteManoObra(idAveria);
			double importeRepuestos = consultaImporteRepuestos(idAveria);
			double totalAveria = importeManoObra + importeRepuestos;
			
			actualizarImporteAveria(idAveria, totalAveria);
			
			totalFactura += totalAveria; 
		}
		return totalFactura;
	}

	private void actualizarImporteAveria(Long idAveria, double totalAveria) throws SQLException {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(SQL_UPDATE_IMPORTE_AVERIA);
			pst.setDouble(1, totalAveria);
			pst.setLong(2, idAveria);
			pst.executeUpdate();
		}	
		finally {
			Jdbc.close(pst);
		}
	}

	private double consultaImporteRepuestos(Long idAveria) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = connection.prepareStatement(SQL_IMPORTE_REPUESTOS);
			pst.setLong(1, idAveria);
			
			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0; // La averia puede no tener repuestos
			}
			
			return rs.getDouble(1);
			
		}
		finally {
			Jdbc.close(rs, pst);
		}
	}

	private double consultaImporteManoObra(Long idAveria) throws BusinessException, SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = connection.prepareStatement(SQL_IMPORTE_MANO_OBRA);
			pst.setLong(1, idAveria);
			
			rs = pst.executeQuery();
			if (rs.next() == false) {
				throw new BusinessException("La averia no existe o no se puede facturar");
			}
			
			return rs.getDouble(1);
			
		} catch (BusinessException e) {
			throw e;
		}
		finally {
			Jdbc.close(rs, pst);
		}
		
	}

	private boolean masAverias() {
		return Console.readString("¿Añadir más averias? (s/n) ").equalsIgnoreCase("s");
	}

}
