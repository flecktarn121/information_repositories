package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.ContractTypecrudGateway;
import uo.ri.persistencia.PersistanceException;

public class ContractTypeCrudGatewayImpl implements ContractTypecrudGateway {
	Configuration conf = Configuration.getInstance();

	@Override
	public void write(String name, double numberOfDays) throws SQLException {
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement prep = conn.prepareStatement(conf.getProperty("SQL_ADD_CONTRACT_TYPE"));) {
			prep.setString(1, name);
			prep.setDouble(2, numberOfDays);
			prep.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<MechanicDTO> list(String name) throws SQLException {
		List<MechanicDTO> types = new ArrayList<MechanicDTO>();
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement prep = conn.prepareStatement(conf.getProperty("SQL_LIST_ALL_CONTRACT_TYPES"))){
				prep.setString(1, name);
				ResultSet rs = prep.executeQuery(); 
			String nameMechanic;
			String surname;
			MechanicDTO myDto = null;
			while (rs.next()) {
				nameMechanic = rs.getString(1);
				surname = rs.getString(2);
				myDto = new MechanicDTO();
				myDto.name = nameMechanic;
				myDto.surname = surname;
				types.add(myDto);
			}
			if (myDto == null) {
				throw new PersistanceException("No existe un tipo de contrato con ese nombre.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return types;
	}

	@Override
	public double sumBaseSalary(String name) throws SQLException {
		int sum = 0;
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(conf.getProperty("SQL_SUM_SALARIO_TRABAJADORES"));) {
			pst.setString(1, name);

			ResultSet rs = pst.executeQuery();
			rs.next();
			sum = rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return sum;
	}

	@Override
	public int sumNumberOfWorkers(String name) throws SQLException {
		int sum = 0;
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(conf.getProperty("SQL_SUM_NUM_TRABAJADORES"));) {
			pst.setString(1, name);

			ResultSet rs = pst.executeQuery();
			rs.next();
			sum = rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return sum;
	}

	@Override
	public void delete(String name) throws SQLException {
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(conf.getProperty("SQL_DELETE_CONTRACT_TYPE"));) {
			pst.setString(1, name.toUpperCase());

			int result = pst.executeUpdate();
			if (result <= 0) {
				throw new PersistanceException("No hay un tipo de contrato con ese nombre.");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(ContractTypeDto dto) throws SQLException {
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(conf.getProperty("SQL_UPDATE_CONTRACT_TYPE"));) {

			pst.setInt(1, dto.compensationDays);
			pst.setString(2, dto.name.toUpperCase());
			int result = pst.executeUpdate();
			if (result <= 0) {
				throw new PersistanceException("No hay un tipo de contrato con ese nombre.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
