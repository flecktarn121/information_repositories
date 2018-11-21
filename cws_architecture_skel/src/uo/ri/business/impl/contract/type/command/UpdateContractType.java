package uo.ri.business.impl.contract.type.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class UpdateContractType implements Command<Void> {
	private ContractTypeRepository repo = Factory.repository.forContractTpe();
	private ContractTypeDto dto;

	public UpdateContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType type = repo.findById(dto.id);
		BusinessCheck.isNotNull(type, "The specified contract type does not exist.");
		type.setCompensationDays(dto.compensationDays);
		return null;
	}

}
