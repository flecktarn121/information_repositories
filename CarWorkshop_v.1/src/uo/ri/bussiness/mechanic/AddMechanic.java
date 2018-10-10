package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;

public class AddMechanic {
	private static String SQL = "insert into TMecanicos(dni, nombre, apellidos) values (?, ?, ?)";

	public void execute(String dni, String nombre, String apellidos) {
		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {

			pst.setString(1, dni);
			pst.setString(2, nombre);
			pst.setString(3, apellidos);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
