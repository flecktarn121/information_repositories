package uo.ri.business.repository;

import uo.ri.model.Contract;

public interface ContractRepository extends Repository<Contract>{
	/**
	 * Finds a contract given its mechanic id
	 * @param id
	 * @return the contract with that mechanic's id
	 */
	public Contract findContractByMechanicId(Long id);
}
