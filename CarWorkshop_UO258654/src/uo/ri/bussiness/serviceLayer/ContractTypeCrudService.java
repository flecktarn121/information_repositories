package uo.ri.bussiness.serviceLayer;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;

public interface ContractTypeCrudService {

	/**
	 * 
	 * @param dto,
	 *            the id field is meaningless for this operation
	 * @throws BusinessException
	 *             if: - another type of contract with the same name already exist,
	 *             or - the number of compensation days is negative
	 */
	void addContractType(ContractTypeDto dto) throws BusinessException;

	/**
	 * Updates just the compensation days
	 * 
	 * @param dto
	 * @throws BusinessException
	 *             if: - the contract type does not exist, or - the number of
	 *             compensation days is negative
	 */
	void updateContractType(ContractTypeDto dto) throws BusinessException;

	/**
	 * @param dto,
	 *            of the contract type
	 * @throws BusinessException
	 *             if the contract type does not exist
	 */
	void deleteContractType(ContractTypeDto dto) throws BusinessException;

	/**
	 * Lists the mechanics which happen to have a contract type similar to the one
	 * specified in the dto
	 * 
	 * @param dto
	 * @throws BusinessException if the type does not exist.
	 */
	void listMechanicsByContractType(ContractTypeDto dto) throws BusinessException;
}
