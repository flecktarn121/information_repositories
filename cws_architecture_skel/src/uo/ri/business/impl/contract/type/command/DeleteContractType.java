package uo.ri.business.impl.contract.type.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class DeleteContractType implements Command<Void> {

	private Long id;
	private ContractTypeRepository repo = Factory.repository.forContractTpe();

	public DeleteContractType(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType type = repo.findById(id);
		checkCanBeDeleted(type);
		repo.remove(type);
		return null;
	}

	private void checkCanBeDeleted(ContractType type) throws BusinessException {
		BusinessCheck.isNotNull(type, "The contract type does not exist.");
		BusinessCheck.isTrue(type.getContracts().isEmpty(),"There are still active contracts of this type." );
	}

}
