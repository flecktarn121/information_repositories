package uo.ri.bussiness.mechanic;

import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class DeleteMechanic {	
	private Long idMecanico;

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public void execute() {
		MechanicDTO dto = new MechanicDTO();
		dto.id = idMecanico;
		PersistenceFactory.getMechanicGateway().delete(dto);
	}
}
