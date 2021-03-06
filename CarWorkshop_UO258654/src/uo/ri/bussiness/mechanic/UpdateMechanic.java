package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class UpdateMechanic {
	private Connection connection;
	private String nombre;
	private String apellidos;
	public long id;

	public UpdateMechanic(String nombre, String apellidos, long id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id = id;
	}

	public void execute() {
		try {
			connection = Jdbc.getConnection();
			MechanicDTO dto = new MechanicDTO();
			dto.id = id;
			dto.name = nombre;
			dto.surname = apellidos;
			PersistenceFactory.getMechanicGateway().update(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
