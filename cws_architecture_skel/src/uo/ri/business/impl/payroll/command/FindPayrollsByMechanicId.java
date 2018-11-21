package uo.ri.business.impl.payroll.command;

import java.util.List;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;

public class FindPayrollsByMechanicId implements Command<List<PayrollDto>> {

	public FindPayrollsByMechanicId(Long id) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PayrollDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
