package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class UpdateMechanic implements Command<Void> {
	private MecanicoRepository repor = Factory.repository.forMechanic();

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		Mecanico m = repor.findById(dto.id);
		BusinessCheck.isNotNull(m, "No existe.");
		return null;
	}

}
