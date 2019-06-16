package uo.ri.business.impl.contract.category;

import java.util.List;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.contract.category.command.AddCategory;
import uo.ri.business.impl.contract.category.command.DeleteCategory;
import uo.ri.business.impl.contract.category.command.FindAllCategories;
import uo.ri.business.impl.contract.category.command.FindCategoryById;
import uo.ri.business.impl.contract.category.command.UpdateCategory;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.conf.Factory;

public class ContractCategoryCrudServiceImpl implements uo.ri.business.ContractCategoryCrudService {
	CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void addContractCategory(ContractCategoryDto dto) throws BusinessException {
		executor.execute(new AddCategory(dto));
	}

	@Override
	public void deleteContractCategory(Long id) throws BusinessException {
		executor.execute(new DeleteCategory(id));
	}

	@Override
	public void updateContractCategory(ContractCategoryDto dto) throws BusinessException {
		executor.execute(new UpdateCategory(dto));
	}

	@Override
	public ContractCategoryDto findContractCategoryById(Long id) throws BusinessException {
		return DtoAssembler.toDto(executor.execute(new FindCategoryById(id)));
	}

	@Override
	public List<ContractCategoryDto> findAllContractCategories() throws BusinessException {
		return DtoAssembler.toDtoList(executor.execute(new FindAllCategories()));
	}

}
