package uo.ri.bussiness.mechanic;

import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class UpdateMechanic {
	private String nombre;
	private String apellidos;
	public long id;

	public UpdateMechanic(String nombre, String apellidos, long id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id = id;
	}

	public void execute() {
		MechanicDTO dto = new MechanicDTO();
		dto.id = id;
		dto.name = nombre;
		dto.surname = apellidos;
		PersistenceFactory.getMechanicGateway().update(dto);
	}
}
