package uo.ri.persistence.jpa;

import java.util.Date;
import java.util.List;

import uo.ri.model.Payroll;
import uo.ri.persistence.jpa.util.BaseRepository;

public class PayrollJpaRepository extends BaseRepository<Payroll> implements uo.ri.business.repository.PayrollRepository {

	@Override
	public void add(Payroll t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Payroll t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Payroll findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payroll> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removeByDate(Date date) {
		// TODO Auto-generated method stub
		return 0;
	}

}
