package uo.ri.bussiness.contract.category;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.CategoryGateway;

public class DeleteContractCategory {
	private Connection connection;
	private CategoryGateway gateway = PersistenceFactory.getCategoryGateway();
	private String name;

	public DeleteContractCategory(String name) {
		super();
		this.name = name;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if (gateway.read(name) == null) {
				throw new BusinessException("No existe una categoría de nombre " + name);
			}
			if (!gateway.getContractsByCategoryName(name).isEmpty()) {
				throw new BusinessException("La categoría aún tiene contratos asociados.");
			}
			ContractCategoryDto dto = new ContractCategoryDto();
			dto.name = name;
			gateway.delete(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
