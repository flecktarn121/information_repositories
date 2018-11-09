package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.model.MedioPago;
import uo.ri.persistence.jpa.util.BaseRepository;

public class MedioPagoJpaRepository
		extends BaseRepository<MedioPago> 
		implements MedioPagoRepository {

	@Override
	public List<MedioPago> findPaymentMeansByClientId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
