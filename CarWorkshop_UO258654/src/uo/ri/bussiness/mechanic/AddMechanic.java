package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class AddMechanic {
	private Connection connection;

	public void execute(String dni, String nombre, String apellidos) {
		try {
			connection = Jdbc.getConnection();

			MechanicDTO mechanic = new MechanicDTO();
			mechanic.dni = dni;
			mechanic.name = nombre;
			mechanic.surname = apellidos;
			PersistenceFactory.getMechanicGateway().create(mechanic);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(connection);
		}
	}
}
