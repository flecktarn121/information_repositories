package uo.ri.business.impl.mechanic.command;

import alb.util.exception.NotYetImplementedException;
import uo.ri.business.dto.MechanicDto;
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

	public Void execute() {
		checkDNINotRepeated(dto.dni);
		Mecanico m = EntityAssembler.toEntity(dto);// mapeo de dto a Entity
		repo.add(m);

		return null;
	}

	private void checkDNINotRepeated(String dni) {
		throw new NotYetImplementedException("Implementa el checkeo de DNI vago.");

	}

}
