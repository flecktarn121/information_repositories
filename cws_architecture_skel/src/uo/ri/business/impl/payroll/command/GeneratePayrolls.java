package uo.ri.business.impl.payroll.command;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;
import uo.ri.model.types.ContractStatus;

public class GeneratePayrolls implements Command<Integer> {
	MecanicoRepository repo = Factory.repository.forMechanic();
	PayrollRepository pRepo = Factory.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		List<Mecanico> mechanics = repo.findAll();
		AtomicInteger counter = new AtomicInteger(0);
		for (Mecanico mechanic : mechanics) {
			Contract con = mechanic.getActiveContract();
			if (con != null) {
				Date date = Dates.lastDayOfMonth(Dates.subMonths(new Date(), 1));
				if (!Dates.isSameMonth(date, con.getLastPayroll().getDate())) {
					if (con.getStatus().equals(ContractStatus.ACTIVE) || (Dates.isSameMonth(con.getEndDate(), date))) {
						double totalInterventions = con.getMechanic().getIntervenciones().stream()
								.map(Intervencion::getImporte).reduce(0.0, (a, b) -> a + b);
						Payroll payroll = new Payroll(con, new Date(), totalInterventions);
						pRepo.add(payroll);
						Association.Percibir.link(con, payroll);
						counter.incrementAndGet();
					}
				}
			}
		}
		return counter.get();
	}

}
