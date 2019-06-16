package uo.ri.persistencia;

import java.util.List;

import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;

public interface CategoryGateway {

	void create(ContractCategoryDto dto);
	void write(ContractCategoryDto dto);
	void list();
	void delete(ContractCategoryDto dto);
	/**
	 * Get the contracts asociated to a category. 
	 * @param id
	 * @return the list containing the contracts
	 */
	List<ContractDto> getContractsByCategoryId(Long id);
}
