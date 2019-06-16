package uo.ri.business.impl.contract.command;

import java.util.Date;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Intervencion;
import uo.ri.model.Payroll;

public class DeleteContract implements Command<Void> {
	private ContractRepository repo = Factory.repository.forContract();
	private Long id;

	public DeleteContract(Long id) {
		super();
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract contract = repo.findById(id);
		BusinessCheck.isNotNull(contract, "No existe el contrato con id " + id);
		BusinessCheck.isFalse(!contract.isFinished(), "No se puede borrar un contrato en vigencia.");
		Date startDate = contract.getStartDate();
		Date endDate = contract.getEndDate();

		checkInterventions(contract, startDate, endDate);

		checkPayrolls(contract, startDate, endDate);

		repo.remove(contract);

		return null;
	}

	private void checkPayrolls(Contract contract, Date startDate, Date endDate) throws BusinessException {
		for (Payroll payroll : contract.getPayrolls()) {
			boolean duringContract = Dates.isDateInWindow(payroll.getDate(), startDate, endDate);
			BusinessCheck.isFalse(duringContract,
					"No se puede borrar un contrato que ha tenido nóminas durante su vigencia.");
		}
	}

	private void checkInterventions(Contract contract, Date startDate, Date endDate) throws BusinessException {
		for (Intervencion intervencion : contract.getMechanic().getIntervenciones()) {
			Date interventionDate = intervencion.getAveria().getFecha();
			boolean duringContract = Dates.isDateInWindow(interventionDate, startDate, endDate);
			
			BusinessCheck.isFalse(duringContract,
					"No se puede borrar un contrato cuando ha tenido intervenciones durante su vigencia.");
		}
	}

}
