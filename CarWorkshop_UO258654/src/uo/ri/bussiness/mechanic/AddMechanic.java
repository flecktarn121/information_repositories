package uo.ri.bussiness.mechanic;

import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class AddMechanic {
	public void execute(String dni, String nombre, String apellidos) {
		MechanicDTO mechanic = new MechanicDTO();
		mechanic.dni = dni;
		mechanic.name = nombre;
		mechanic.surname = apellidos;
		PersistenceFactory.getMechanicGateway().create(mechanic);
	}
}
