package uo.ri.business.impl.contract.command;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FindContractsByMechanicId implements Command<List<Contract>> {
	private ContractRepository repo = Factory.repository.forContract();
	private MecanicoRepository repoMechanic = Factory.repository.forMechanic();
	private Long mechanicId;

	public FindContractsByMechanicId(Long mechanicId) {
		this.mechanicId = mechanicId;
	}

	@Override
	public List<Contract> execute() throws BusinessException {
		BusinessCheck.isNotNull(repoMechanic.findById(mechanicId), "No hay mecánico con ese id.");
		return repo.findAll()
				.parallelStream()
				.filter(contract -> contract.getMechanic().getId() == mechanicId)
				.collect(Collectors.toList());
	}

}
