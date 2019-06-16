package uo.ri.business.impl.contract.category.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class AddCategory implements Command<Void> {

	private ContractCategoryDto dto;
	private ContractCategoryRepository repo = Factory.repository.forContractCategory();
	
	public AddCategory(ContractCategoryDto dto) {
		this.dto = dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		ContractCategory category = EntityAssembler.toEntity(dto);
		checkIsRepeated(category);
		repo.add(category);
		return null;
	}

	private void checkIsRepeated(ContractCategory category) throws BusinessException {
		boolean isRepeated = repo.findAll().parallelStream().anyMatch(cat -> cat.equals(category));
		BusinessCheck.isFalse(isRepeated, "Ya existe una categoría de estas características.");
		
	}

}
