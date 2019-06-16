package uo.ri.business.impl.contract.category.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class DeleteCategory implements Command<Void> {
	private Long id;
	private ContractCategoryRepository repo = Factory.repository.forContractCategory();
	
	
	
	public DeleteCategory(Long id) {
		super();
		this.id = id;
	}



	@Override
	public Void execute() throws BusinessException {
		ContractCategory category = repo.findById(id); //can be null
		checkCanBeDeleted(category);
		repo.remove(category);
		return null;
	}



	private void checkCanBeDeleted(ContractCategory category) throws BusinessException {
		BusinessCheck.isNotNull(category, "La categoría con id "+id+ " no existe.");
		BusinessCheck.isTrue(category.getContracts().isEmpty(), "No se puede borrar la categoría, aún tiene contratos asignados.");
	}

}
