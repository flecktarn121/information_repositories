package uo.ri.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.BreakdownDTO;

public interface BreakdownGateway {
	public double consultaImporteManoObra(Long idAveria) throws SQLException;

	public void actualizarImporteAveria(BreakdownDTO dto) throws SQLException;
	
	public void cambiarEstadoAverias(List<Long> idsAveria, String status) throws SQLException;
	public void verificarAveriasTerminadas(List<Long> idsAveria) throws SQLException;
	public void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) throws SQLException;
	void setConnection(Connection connection);
}
