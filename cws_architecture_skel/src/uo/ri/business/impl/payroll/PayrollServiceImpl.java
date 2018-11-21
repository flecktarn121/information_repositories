package uo.ri.business.impl.payroll;

import java.util.List;

import uo.ri.business.PayrollService;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.payroll.command.DeleteLastGenetaredPayrolls;
import uo.ri.business.impl.payroll.command.DeleteLastPayrollForMechanicId;
import uo.ri.business.impl.payroll.command.FindAllPayrolls;
import uo.ri.business.impl.payroll.command.FindPayrollById;
import uo.ri.business.impl.payroll.command.FindPayrollsByMechanicId;
import uo.ri.business.impl.payroll.command.GeneratePayrolls;
import uo.ri.conf.Factory;

public class PayrollServiceImpl implements PayrollService {
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<PayrollDto> findAllPayrolls() throws BusinessException {
		return executor.execute(new FindAllPayrolls());
	}

	@Override
	public List<PayrollDto> findPayrollsByMechanicId(Long id) throws BusinessException {
		return executor.execute(new FindPayrollsByMechanicId(id));
	}

	@Override
	public PayrollDto findPayrollById(Long id) throws BusinessException {

		return executor.execute(new FindPayrollById(id));
	}

	@Override
	public void deleteLastPayrollForMechanicId(Long id) throws BusinessException {
		executor.execute(new DeleteLastPayrollForMechanicId(id));

	}

	@Override
	public int deleteLastGenetaredPayrolls() throws BusinessException {
		return executor.execute(new DeleteLastGenetaredPayrolls());
	}

	@Override
	public int generatePayrolls() throws BusinessException {
		return executor.execute(new GeneratePayrolls());
	}

}
