package uo.ri.business.impl.contract.command;

import alb.util.date.Dates;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class UpdateContract implements Command<Contract> {
	private ContractRepository repo = Factory.repository.forContract();
	private ContractDto dto;

	public UpdateContract(ContractDto dto) {
		super();
		this.dto = dto;
	}

	@Override
	public Contract execute() throws BusinessException {
		Contract contract = repo.findById(dto.id);
		BusinessCheck.isNotNull(contract, "No existe un contrato con id " + dto.id);
		BusinessCheck.isFalse(contract.isFinished(), "No se puede modificar un contrato ya finalizado.");
		BusinessCheck.isFalse(dto.yearBaseSalary < 0, "No se puede establecer un salario base negativo");
		BusinessCheck.isFalse(Dates.isBefore(dto.endDate, contract.getStartDate()),
				"La fecha de finalización del contrato no puede ser anterior a la de comienzo");
		contract.setEndDate(dto.endDate);
		contract.setSalary(dto.yearBaseSalary);
		return null;
	}

}
