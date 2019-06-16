package uo.ri.business.impl.contract.category.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class UpdateCategory implements Command<Void> {
	private ContractCategoryDto dto;
	private ContractCategoryRepository repo = Factory.repository.forContractCategory();

	public UpdateCategory(ContractCategoryDto dto) {
		super();
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory category = repo.findById(dto.id);
		BusinessCheck.isNotNull(category, "LA categoría con id " + dto.id + " no existe.");
		return null;
	}
}
