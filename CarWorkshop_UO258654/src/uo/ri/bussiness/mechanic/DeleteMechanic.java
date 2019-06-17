package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class DeleteMechanic {
	private Long idMecanico;
	private Connection connection;

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public void execute() {
		try {
			connection = Jdbc.getConnection();
			MechanicDTO dto = new MechanicDTO();
			dto.id = idMecanico;
			PersistenceFactory.getMechanicGateway().delete(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
