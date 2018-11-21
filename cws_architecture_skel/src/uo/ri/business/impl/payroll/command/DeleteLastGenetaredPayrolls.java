package uo.ri.business.impl.payroll.command;

import java.util.Date;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;

public class DeleteLastGenetaredPayrolls implements Command<Integer> {
	private PayrollRepository repo = Factory.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		Date date = Dates.lastDayOfMonth(Dates.subMonths(new Date(), 1));
		return repo.removeByDate(date);
	}

}
