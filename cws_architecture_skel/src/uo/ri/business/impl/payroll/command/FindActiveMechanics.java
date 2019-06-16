package uo.ri.business.impl.payroll.command;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class FindActiveMechanics implements Command<List<MechanicDto>> {
	private MecanicoRepository repo = Factory.repository.forMechanic();

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<Mecanico> mecanicos = repo.findAll();
		mecanicos = mecanicos.parallelStream()
				.filter((mechanic) -> mechanic.getActiveContract() != null && !mechanic.getActiveContract().isFinished())
				.collect(Collectors.toList());
		return DtoAssembler.toMechanicDtoList(mecanicos);
	}

}
