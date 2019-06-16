package uo.ri.business.impl.contract.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FindContractById implements Command<Contract> {
	private ContractRepository repo = Factory.repository.forContract();
	private Long id;

	public FindContractById(Long id) {
		super();
		this.id = id;
	}

	@Override
	public Contract execute() throws BusinessException {
		Contract contract = repo.findById(id);
		BusinessCheck.isNotNull(contract, "No existe un contrato con id " + id);
		return contract;
	}

}
