package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class ListAllMechanic {
	private Connection connection;

	public List<MechanicDTO> execute() {
		try {
			connection = Jdbc.getConnection();
			return PersistenceFactory.getMechanicGateway().readAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
