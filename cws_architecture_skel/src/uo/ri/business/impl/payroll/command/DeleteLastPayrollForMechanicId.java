package uo.ri.business.impl.payroll.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class DeleteLastPayrollForMechanicId implements Command<Void> {
	private PayrollRepository repo = Factory.repository.forPayroll();
	private Long idMechanic;

	public DeleteLastPayrollForMechanicId(Long id) {
		this.idMechanic = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico m = Factory.repository.forMechanic().findById(idMechanic);
		BusinessCheck.isNotNull(m, "There is not a mechanic with that id.");
		Payroll lastPayroll = m.getActiveContract().getLastPayroll();
		Association.Percibir.unLink(m.getActiveContract(), lastPayroll);
		repo.remove(lastPayroll);
		return null;
	}

}
