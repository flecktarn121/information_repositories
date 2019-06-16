package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.CategoryGateway;
import uo.ri.persistencia.PersistanceException;

public class CategoryGatewayImp implements CategoryGateway {
	Configuration config = Configuration.getInstance();

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
		// TODO Auto-generated method stub

	}

	@Override
	public void list() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ContractCategoryDto dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ContractDto> getContractsByCategoryId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
