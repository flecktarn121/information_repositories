package uo.ri.bussiness.serviceLayer.implementation;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.contract.category.AddContractCategory;
import uo.ri.bussiness.contract.category.DeleteContractCategory;
import uo.ri.bussiness.contract.category.ListMechanicsByContractCategory;
import uo.ri.bussiness.contract.category.UpdateContractCategory;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.bussiness.serviceLayer.ContractCategoryService;

public class ContractCategoryServiceImpl implements ContractCategoryService {

	@Override
	public void addContractCategory(ContractCategoryDto dto) throws BusinessException {
		new AddContractCategory(dto).execute();
	}

	@Override
	public void deleteContractCategory(String name) throws BusinessException {
		new DeleteContractCategory(name).execute();

	}

	@Override
	public void updateContractCategory(ContractCategoryDto dto) throws BusinessException {
		new UpdateContractCategory(dto).execute();
	}

	@Override
	public List<MechanicDTO> findAllMechanicsByContractCategorie(String name) throws BusinessException {
		return new ListMechanicsByContractCategory(name).execute();
	}

}
