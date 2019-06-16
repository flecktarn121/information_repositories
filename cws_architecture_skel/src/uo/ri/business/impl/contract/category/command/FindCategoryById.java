package uo.ri.business.impl.contract.category.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class FindCategoryById implements Command<ContractCategory> {
	private Long id;
	private ContractCategoryRepository repo = Factory.repository.forContractCategory();
	
	public FindCategoryById(Long id) {
		super();
		this.id = id;
	}

	@Override
	public ContractCategory execute() throws BusinessException {
		ContractCategory cateogry = repo.findById(id);
		BusinessCheck.isNotNull(cateogry, "No existe una categoría de contrato con id "+id);
		return cateogry;
	}

}
