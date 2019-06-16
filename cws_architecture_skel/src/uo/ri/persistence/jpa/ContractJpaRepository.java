package uo.ri.persistence.jpa;

import uo.ri.business.repository.ContractRepository;
import uo.ri.model.Contract;
import uo.ri.persistence.jpa.util.BaseRepository;

public class ContractJpaRepository extends BaseRepository<Contract> implements ContractRepository {

	@Override
	public Contract findContractByMechanicId(Long id) {
		
		return null;
	}

}
