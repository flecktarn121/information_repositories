package uo.ri.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import uo.ri.bussiness.dto.BreakdownDTO;

public interface BreakdownGateway {
	public double consultaImporteManoObra(Long idAveria) throws SQLException;

	public void actualizarImporteAveria(BreakdownDTO dto) throws SQLException;
	
	public void cambiarEstadoAverias(List<Long> idsAveria, String status) throws SQLException;
	public void verificarAveriasTerminadas(List<Long> idsAveria) throws SQLException;
	public void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) throws SQLException;
	void setConnection(Connection connection);
	/**
	 * Calcula el importe de las averias acaecidas en el mes indicado por la fecha, en las que ha participado este mecanico
	 * 
	 * @param mechanicId el mecanico que ha participado
	 * @param date fecha del ultimo dia del mes a comprobar
	 * @return la suma del importe de esas averias
	 */
	public double getImportePorMecanicoYFecha(Long mechanicId, Date date);
	
	public List<BreakdownDTO> getBreakdownsByMechanic(Long mechanicId);
}
