package uo.ri.business.impl.contract.category.command;

import java.util.List;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class FindAllCategories implements Command<List<ContractCategory>> {
	private ContractCategoryRepository repo = Factory.repository.forContractCategory();
	@Override
	public List<ContractCategory> execute() throws BusinessException {
		
		return repo.findAll();
	}

}
