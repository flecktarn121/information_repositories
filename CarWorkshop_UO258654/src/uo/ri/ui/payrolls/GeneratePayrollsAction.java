package uo.ri.ui.payrolls;

import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.configuration.ServicesFactory;

public class GeneratePayrollsAction implements Action {

	@Override
	public void execute() {
		try {
			Printer.print("Generando nóminas...");

			int counter = ServicesFactory.getPayrollService().generatePayrolls();

			Printer.print("Se han generado " + counter + " nóminas.");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
