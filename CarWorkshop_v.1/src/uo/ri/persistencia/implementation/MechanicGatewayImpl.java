package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.persistencia.MechanicGateway;

public class MechanicGatewayImpl implements MechanicGateway {

	@Override
	public void create(MechanicDTO dto) {
		String SQL = "insert into TMecanicos(dni, nombre, apellidos) values (?, ?, ?)";
		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {

			pst.setString(1, dto.dni);
			pst.setString(2, dto.name);
			pst.setString(3, dto.surname);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<MechanicDTO> read() {
		String SQL = "select id, dni, nombre, apellidos from TMecanicos";
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

	@Override
	public void update(MechanicDTO dto) {
		String SQL = "update TMecanicos " + "set nombre = ?, apellidos = ? " + "where id = ?";
		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {

			pst.setString(1, dto.name);
			pst.setString(2, dto.surname);
			pst.setLong(3, dto.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(MechanicDTO dto) {
		String SQL = "delete from TMecanicos where id = ?";
		try (Connection c = Jdbc.getConnection(); PreparedStatement pst = c.prepareStatement(SQL);) {
			pst.setLong(1, dto.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
