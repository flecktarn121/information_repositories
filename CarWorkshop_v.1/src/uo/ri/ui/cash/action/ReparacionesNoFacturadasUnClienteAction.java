package uo.ri.ui.cash.action;

import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;

public class ReparacionesNoFacturadasUnClienteAction implements Action {

	/**
	 * Proceso:
	 * 
	 *   - Pide el DNI del cliente
	 *    
	 *   - Muestra en pantalla todas sus averias no facturadas 
	 *   		(status <> 'FACTURADA'). De cada avería muestra su 
	 *   		id, fecha, status, importe y descripción
	 */
	@Override
	public void execute() throws BusinessException {
		// TODO ...
	}

}
