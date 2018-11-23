package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class AddMechanic implements Command<Void> {

	private MechanicDto dto;
	private MecanicoRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	public Void execute() throws BusinessException {
		checkDNINotRepeated(dto.dni);
		Mecanico m = EntityAssembler.toEntity(dto);// mapeo de dto a Entity
		repo.add(m);

		return null;
	}

	private void checkDNINotRepeated(String dni) throws BusinessException {
		boolean isRepeated = repo.findAll().parallelStream().anyMatch((mechanic) -> mechanic.getDni().equals(dto.dni));
		BusinessCheck.isFalse(isRepeated, "There is already a mechanic with that DNI.");

	}

}
