package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;

public class UpdateMechanic {
	private static String SQL = "update TMecanicos " + "set nombre = ?, apellidos = ? " + "where id = ?";

	private String nombre;
	private String apellidos;
	public long id;

	public UpdateMechanic(String nombre, String apellidos, long id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id = id;
	}

	public void execute() {

		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {

			pst.setString(1, nombre);
			pst.setString(2, apellidos);
			pst.setLong(3, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
