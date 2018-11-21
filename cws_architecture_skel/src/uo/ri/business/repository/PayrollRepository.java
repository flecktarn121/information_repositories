package uo.ri.business.repository;

import java.util.Date;

import uo.ri.model.Payroll;

public interface PayrollRepository extends Repository<Payroll> {
	public int removeByDate(Date date);

	public void removeByMechanicId(Long id);
}
