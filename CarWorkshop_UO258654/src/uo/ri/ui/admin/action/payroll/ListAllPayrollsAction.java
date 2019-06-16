package uo.ri.ui.admin.action.payroll;

import java.util.List;

import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.configuration.ServicesFactory;

public class ListAllPayrollsAction implements Action {

	@Override
	public void execute() {
		try {
			Printer.print("Mostrando todas las nóminas");
			List<PayrollDto> payrolls = ServicesFactory.getPayrollService().findAllPayrolls();
			payrolls.forEach((payroll) -> {
				String[] date = payroll.date.toString().split("-");
				Printer.print("Mes: " + date[1] + ", año: " + date[1] + ", total neto: " + payroll.netTotal);
			});
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
