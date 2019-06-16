package uo.ri.business.impl.contract.command;

import java.util.Date;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FinishContract implements Command<Void> {
	private ContractRepository repo = Factory.repository.forContract();
	private Long id;
	private Date endaDate;
	
	public FinishContract(Long id, Date endaDate) {
		this.id = id;
		this.endaDate = endaDate;
	}
	@Override
	public Void execute() throws BusinessException {
		Contract contract = repo.findById(id);
		BusinessCheck.isNotNull(contract, "No hay contratos con esa id.");
		contract.markAsFinished(endaDate);
		return null;
	}

}
