package uo.ri.ui.cash.action;

import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;

public class LiquidarFacturaAction implements Action {

	/**
	 * Proceso:
	 * 
	 *  - Pedir al usuario el nº de factura
	 *  - Recuperar los datos de la factura
	 *  - Mostrar la factura en pantalla
	 *  - Verificar que está sin abonar (status <> 'ABONADA')
	 *  - Listar en pantalla los medios de pago registrados para el cliente
	 *  - Mostrar los medios de pago
	 *  - Pedir el importe a cargar en cada medio de pago. 
	 *  	Verificar que la suma de los cargos es igual al importe de la factura
	 *  - Registrar los cargos en la BDD
	 *  - Incrementar el acumulado de cada medio de pago
	 *  - Si se han empleado bonos, decrementar el saldo disponible 
	 *  - Finalmente, marcar la factura como abonada 
	 *  
	 */
	@Override
	public void execute() throws BusinessException {
		// TODO ...
	}

}
