package uo.ri.bussiness.contract.category;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.CategoryGateway;

public class AddContractCategory {
	private Connection connection;
	private CategoryGateway gateway = PersistenceFactory.getCategoryGateway();
	private ContractCategoryDto dto;

	public AddContractCategory(ContractCategoryDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if (gateway.read(dto.name) != null) {
				throw new BusinessException("Ya existe una categoría con nombre: " + dto.name);
			}
			if(dto.trieniumSalary < 0 || dto.productivityPlus <0) {
				throw new BusinessException("No se admiten valores negativos.");
			}
			gateway.write(dto);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}

}
