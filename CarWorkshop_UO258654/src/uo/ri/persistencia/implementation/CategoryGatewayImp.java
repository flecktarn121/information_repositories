package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.CategoryGateway;

public class CategoryGatewayImp implements CategoryGateway {
	private Configuration config = Configuration.getInstance();
	private Connection connection;

	@Override
	public void create(ContractCategoryDto dto) {
		try (Connection c = Jdbc.getConnection();
				PreparedStatement pst = c.prepareStatement(config.getProperty("SQL_INSERT_MECANICO"));) {

			pst.setString(1, dto.name);
			pst.setDouble(2, dto.trieniumSalary);
			pst.setDouble(3, dto.productivityPlus);

			pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void write(ContractCategoryDto dto) {
		try (PreparedStatement prep = connection.prepareStatement(config.getProperty("SQL_ISNERT_CATEGORY"));) {
			prep.setString(1, dto.name);
			prep.setDouble(2, dto.trieniumSalary);
			prep.setDouble(3, dto.productivityPlus);
			prep.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<MechanicDTO> list(String name) {
		List<MechanicDTO> types = new ArrayList<MechanicDTO>();
		try (
				PreparedStatement prep = connection.prepareStatement(config.getProperty("SQL_LIST_MECHANIC_BY_CATEGORY"))) {
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
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return types;

	}

	@Override
	public void delete(ContractCategoryDto dto) {
		try (PreparedStatement pst = connection.prepareStatement(config.getProperty("SQL_DELETE_CATEGORY"));) {
			pst.setString(1, dto.name.toUpperCase());
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<ContractDto> getContractsByCategoryName(String name) {
		List<ContractDto> types = new ArrayList<ContractDto>();
		try (PreparedStatement prep = connection
				.prepareStatement(config.getProperty("SQL_LIST_CONTRACT_BY_CATEGORY"))) {
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			Long id;
			double compensation;
			String status;
			ContractDto myDto = null;
			while (rs.next()) {
				id = rs.getLong(1);
				compensation = rs.getDouble(5);
				status = rs.getString(6);
				myDto = new ContractDto();
				myDto.id = id;
				myDto.compensation = compensation;
				myDto.status = status;
				types.add(myDto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return types;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;

	}

	@Override
	public ContractCategoryDto read(String name) {
		try (PreparedStatement prep = connection.prepareStatement(config.getProperty("SQL_GET_CATEGORY"))) {
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			String catName;
			double trieniumSalary;
			double prodPlus;
			ContractCategoryDto dto = null;
			while (rs.next()) {
				dto = new ContractCategoryDto();
				catName = rs.getString(1);
				trieniumSalary = rs.getDouble(2);
				prodPlus= rs.getDouble(3);
				dto.name = catName;
				dto.trieniumSalary = trieniumSalary;
				dto.productivityPlus = prodPlus;
				dto.id = rs.getLong(4);
			}
			return dto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(ContractCategoryDto dto) {
		try (PreparedStatement pst = connection.prepareStatement(config.getProperty("SQL_UPDATE_CONTRACT_CATEGORY"));) {

			pst.setDouble(1, dto.trieniumSalary);
			pst.setDouble(2, dto.productivityPlus);
			pst.setString(3, dto.name.toUpperCase());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
