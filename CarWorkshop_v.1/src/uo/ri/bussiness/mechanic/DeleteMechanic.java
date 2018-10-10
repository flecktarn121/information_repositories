package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;

public class DeleteMechanic {
	private static String SQL = "delete from TMecanicos where id = ?";
	
	private Long idMecanico;

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public void execute() {
		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {
			pst.setLong(1, idMecanico);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
