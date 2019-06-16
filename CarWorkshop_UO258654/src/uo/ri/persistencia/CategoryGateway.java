package uo.ri.persistencia;

import java.sql.Connection;
import java.util.List;

import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.MechanicDTO;

public interface CategoryGateway {

	void create(ContractCategoryDto dto);

	void write(ContractCategoryDto dto);

	List<MechanicDTO> list(String name);

	void delete(ContractCategoryDto dto);

	/**
	 * Get the contracts asociated to a category.
	 * 
	 * @param id
	 * @return the list containing the contracts
	 */
	List<ContractDto> getContractsByCategoryName(String name);

	void setConnection(Connection connection);
	
	ContractCategoryDto read(String name);
	
	void update(ContractCategoryDto dto);
}
