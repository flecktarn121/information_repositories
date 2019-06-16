package uo.ri.bussiness.serviceLayer;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.MechanicDTO;

public interface ContractCategoryService {
	/**
	 * Add a new category with the given data
	 * 
	 * @param dto, the id value, if any, will be ignored
	 * @throws BusinessException if: - another category with the same name already
	 *                           exists, or - triennium salary is negative, or -
	 *                           productivity plus is negative
	 */
	void addContractCategory(ContractCategoryDto dto) throws BusinessException;

	/**
	 * Removes the given category
	 * 
	 * @param name, of the category
	 * @throws BusinessException if: - the category does not exist, or - the
	 *                           category has contracts assigned
	 */
	void deleteContractCategory(String name) throws BusinessException;

	/**
	 * Updates only the productivityPlus and the triennium salary
	 * 
	 * @param dto, only id, trienniumSalary and productivitySalary fields are useful
	 *             for this operation, the rest will be ignored
	 * @throws BusinessException if: - the category does not exist, or - the new
	 *                           trienniumSalary is negative, or - the new
	 *                           productivitySalary is negative
	 */
	void updateContractCategory(ContractCategoryDto dto) throws BusinessException;

	/**
	 * @return the list of all mechanics with a contract in that category, or an
	 *         empty list if there is none
	 * @throws BusinessException DOES NOT
	 */
	List<MechanicDTO> findAllMechanicsByContractCategorie(String name) throws BusinessException;

}
