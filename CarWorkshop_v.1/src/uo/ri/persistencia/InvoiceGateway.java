package uo.ri.persistencia;

import java.sql.SQLException;
import uo.ri.bussiness.dto.InvoiceDTO;

public interface InvoiceGateway {
	public Long generarNuevoNumeroFactura() throws SQLException;
	public long getGeneratedKey(long numeroFactura) throws SQLException;
	public long crearFactura(InvoiceDTO dto) throws SQLException;
}
