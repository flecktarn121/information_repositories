package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.MechanicGateway;
import uo.ri.persistencia.PersistanceException;

public class MechanicGatewayImpl implements MechanicGateway {
	Configuration config = Configuration.getInstance();

	@Override
	public void create(MechanicDTO dto) {
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_INSERT_MECANICO"));) {

			pst.setString(1, dto.dni);
			pst.setString(2, dto.name);
			pst.setString(3, dto.surname);

			pst.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException integrity) {
			throw new PersistanceException("Ya existe un mecánico con DNI " + dto.dni);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<MechanicDTO> read() {
		List<MechanicDTO> mechanics = new ArrayList<MechanicDTO>();
		MechanicDTO mechanic;
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_LISTAR_MECANICOS"));
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				mechanic = new MechanicDTO();
				mechanic.id = rs.getLong(1);
				mechanic.name = rs.getString(2);
				mechanic.surname = rs.getString(3);

				mechanics.add(mechanic);
			}
			return mechanics;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(MechanicDTO dto) {
		if (dto.name == null || dto.surname == null) {
			throw new IllegalArgumentException("name and surname must be non null");
		}
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_UPDATE_MECANICO"));) {

			pst.setString(1, dto.name);
			pst.setString(2, dto.surname);
			pst.setLong(3, dto.id);

			int result = pst.executeUpdate();
			if (result <= 0) {
				throw new PersistanceException("No existe tal mecánico.");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(MechanicDTO dto) {
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_DELETE_MECANICOS"));) {
			pst.setLong(1, dto.id);

			int result = pst.executeUpdate();
			if (result <= 0) {
				throw new PersistanceException("No existe tal mecánico.");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<MechanicDTO> readAll() {
		List<MechanicDTO> mechanics = new ArrayList<MechanicDTO>();
		MechanicDTO mechanic;
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_LISTAR_TODOS_MECANICOS"));
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				mechanic = new MechanicDTO();
				mechanic.id = rs.getLong(1);
				mechanic.name = rs.getString(2);
				mechanic.surname = rs.getString(3);

				mechanics.add(mechanic);
			}
			return mechanics;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
