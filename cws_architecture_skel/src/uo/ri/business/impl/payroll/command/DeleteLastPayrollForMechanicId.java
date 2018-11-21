package uo.ri.business.impl.payroll.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;

public class DeleteLastPayrollForMechanicId implements Command<Void> {
	private PayrollRepository repo = Factory.repository.forPayroll();
	private Long idMechanic;

	public DeleteLastPayrollForMechanicId(Long id) {
		this.idMechanic = id;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.isNotNull(Factory.repository.forMechanic().findById(idMechanic),
				"There is not a mechanic with that id.");
		repo.removeByMechanicId(idMechanic);
		return null;
	}

}
