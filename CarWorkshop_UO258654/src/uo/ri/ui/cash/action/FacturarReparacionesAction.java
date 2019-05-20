package uo.ri.ui.cash.action;

import alb.util.menu.Action;
import uo.ri.configuration.ServicesFactory;

public class FacturarReparacionesAction implements Action {

	@Override
	public void execute() throws Exception {
		ServicesFactory.getInvoiceService().facturarResparacion();
		
	}

	

}
