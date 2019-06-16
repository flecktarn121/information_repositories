package uo.ri.ui.admin.action.payroll;

import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.configuration.ServicesFactory;

public class DeleteLastMonthPayrolls implements Action {

	public DeleteLastMonthPayrolls() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			Printer.print("Borrando nóminas del pasado mes...");
			int num = ServicesFactory.getPayrollService().deleteLastGenetaredPayrolls();
			Printer.print("Se han eliminado " + num + "nóminas");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
