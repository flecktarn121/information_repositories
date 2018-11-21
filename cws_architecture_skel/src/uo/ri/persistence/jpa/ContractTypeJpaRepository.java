package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.model.ContractType;
import uo.ri.persistence.jpa.util.BaseRepository;

public class ContractTypeJpaRepository extends BaseRepository<ContractType> implements ContractTypeRepository {

	@Override
	public void add(ContractType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(ContractType t) {
		// TODO Auto-generated method stub

	}

	@Override
	public ContractType findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContractType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
