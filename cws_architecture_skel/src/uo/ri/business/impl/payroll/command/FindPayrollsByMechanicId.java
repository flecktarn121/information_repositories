package uo.ri.business.impl.payroll.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class FindPayrollsByMechanicId implements Command<List<PayrollDto>> {
	private MecanicoRepository repo = Factory.repository.forMechanic();
	private Long idMechanic;

	public FindPayrollsByMechanicId(Long id) {
		this.idMechanic = id;
	}

	@Override
	public List<PayrollDto> execute() throws BusinessException {
		List<Payroll> payrolls = Collections.synchronizedList(new ArrayList<Payroll>());
		Mecanico m = repo.findById(idMechanic);
		if (m == null) {
			return null;
		} else {
			m.getContracts().stream().forEach((contract) -> {
				payrolls.addAll(contract.getPayrolls());
			});
			return DtoAssembler.toPayrollDtoList(payrolls);
		}
	}

}
