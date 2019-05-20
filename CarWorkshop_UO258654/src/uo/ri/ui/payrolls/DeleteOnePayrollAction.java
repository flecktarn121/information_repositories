package uo.ri.ui.payrolls;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.configuration.ServicesFactory;

public class DeleteOnePayrollAction implements Action {

	@Override
	public void execute() {
		try {
			Long id = Console.readLong("ID del mecánico: ");
			ServicesFactory.getPayrollService().deleteLastPayrollForMechanicId(id);
			Printer.print("Nómina borrada");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
