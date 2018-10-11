package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class AddMechanic {
	private static String SQL = "insert into TMecanicos(dni, nombre, apellidos) values (?, ?, ?)";

	public void execute(String dni, String nombre, String apellidos) {
		MechanicDTO mechanic = new MechanicDTO();
		mechanic.dni = dni;
		mechanic.name = nombre;
		mechanic.surname = apellidos;
		PersistenceFactory.getMechanicGateway().create(mechanic);
	}
}
