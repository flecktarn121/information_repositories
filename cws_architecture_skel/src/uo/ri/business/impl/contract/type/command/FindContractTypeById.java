package uo.ri.business.impl.contract.type.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class FindContractTypeById implements Command<ContractTypeDto> {
	private ContractTypeRepository repo = Factory.repository.forContractTpe();
	private Long id;

	public FindContractTypeById(Long id) {
		this.id = id;
	}

	@Override
	public ContractTypeDto execute() throws BusinessException {
		ContractType type = repo.findById(id);
		if (type == null) {
			return null;
		} else {
			return DtoAssembler.toDto(type);
		}
	}

}
