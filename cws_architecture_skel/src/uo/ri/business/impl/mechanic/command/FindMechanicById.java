package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class FindMechanicById implements Command<MechanicDto> {
	private MecanicoRepository repor = Factory.repository.forMechanic();
	private Long id;

	public FindMechanicById(Long id) {
		this.id = id;
	}

	public MechanicDto execute() throws BusinessException {
		Mecanico m = repor.findById(id);
		BusinessCheck.isNotNull(m, "No existe el mecánico con el id " + id);
		return DtoAssembler.toDto(m);
	}

}
