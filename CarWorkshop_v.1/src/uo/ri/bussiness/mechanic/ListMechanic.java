package uo.ri.bussiness.mechanic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.console.Console;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;

public class ListMechanic {
	private static String SQL = "select id, dni, nombre, apellidos from TMecanicos";

	public List<MechanicDTO> execute() {
		List<MechanicDTO> mechanics = new ArrayList<MechanicDTO>();
		MechanicDTO mechanic;
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(SQL);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				mechanic = new MechanicDTO();
				mechanic.id = rs.getLong(1);
				mechanic.dni = rs.getString(2);
				mechanic.name = rs.getString(3);
				mechanic.surname = rs.getString(4);
				
				mechanics.add(mechanic);
				// Console.printf("\t%d %s %s %s\n", rs.getLong(1), rs.getString(2),
				// rs.getString(3), rs.getString(4));
			}
			return mechanics;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
