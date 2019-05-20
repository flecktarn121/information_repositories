package uo.ri.ui.payrolls;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.configuration.ServicesFactory;

public class CheckPayrollAction implements Action {

	@Override
	public void execute() {
		try {
			Long id = Console.readLong("Id de la nómina a consultar: ");
			PayrollDto payroll = ServicesFactory.getPayrollService().findPayrollById(id);
			String[] date = payroll.date.toString().split("-");
			Printer.print("Mes: " + date[1] + ", año: " + date[0]);
			Printer.print("Total bruto: " + payroll.netTotal);
			Printer.print("Total descuentos: " + payroll.discountTotal);
			Printer.print("Desglose: ");
			Printer.print("Salario base -> " + payroll.baseSalary);
			Printer.print("Paga extra -> " + payroll.extraSalary);
			Printer.print("Plus de productividad -> " + payroll.productivity);
			Printer.print("Trienios -> " + payroll.triennium);
			Printer.print("IRPF -> " + payroll.irpf);
			Printer.print("Seguridad Social -> " + payroll.socialSecurity);
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
