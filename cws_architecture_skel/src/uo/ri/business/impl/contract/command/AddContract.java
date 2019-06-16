package uo.ri.business.impl.contract.command;

import java.util.Date;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;

public class AddContract implements Command<Void> {
	private ContractRepository repo = Factory.repository.forContract();
	private MecanicoRepository repoMechanic = Factory.repository.forMechanic();
	private ContractDto dto;

	public AddContract(ContractDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico mechanic = repoMechanic.findById(dto.mechanicId);
		if (mechanic.getActiveContract() != null) {
			mechanic.getActiveContract().markAsFinished(new Date(System.currentTimeMillis()));
		}
		
		if(dto.endDate == null) {
			repo.add(new Contract(mechanic, dto.startDate, dto.yearBaseSalary));
		}else {
			repo.add(new Contract(mechanic, dto.startDate, dto.endDate, dto.yearBaseSalary));
		}
		return null;
	}

}
