package uo.ri.business.impl.contract.type.command;

import java.util.List;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class AddContractType implements Command<Void> {
	private ContractTypeDto dto;
	private ContractTypeRepository repo = Factory.repository.forContractTpe();

	public AddContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		List<ContractType> types = repo.findAll();
		checkRepeatedName(types);
		repo.add(EntityAssembler.toEntity(dto));
		return null;
	}

	private void checkRepeatedName(List<ContractType> types) throws BusinessException {
		boolean isRepeated = types.parallelStream().anyMatch((type)->{
			return type.getName().equalsIgnoreCase(dto.name);
		});
		if(isRepeated) {
			throw new BusinessException("A contract type with that name already exists.");
		}
	}

}
