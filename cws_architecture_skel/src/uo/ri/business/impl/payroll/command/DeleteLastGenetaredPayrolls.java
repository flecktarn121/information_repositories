package uo.ri.business.impl.payroll.command;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Payroll;

public class DeleteLastGenetaredPayrolls implements Command<Integer> {
	private PayrollRepository repo = Factory.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		Date date = Dates.lastDayOfMonth(Dates.subMonths(new Date(), 1));
		AtomicInteger counter = new AtomicInteger(0);
		List<Payroll> payrolls = repo.findAll();
		payrolls.stream().forEach((payroll) -> {
			if (Dates.isSameMonth(payroll.getDate(), date)) {
				repo.remove(payroll);
				counter.incrementAndGet();
			}
		});
		return counter.get();
	}

}
