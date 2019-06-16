package uo.ri.bussiness.contract.category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.CategoryGateway;

public class ListMechanicsByContractCategory {
	private Connection connection;
	private CategoryGateway gateway = PersistenceFactory.getCategoryGateway();
	private String name;

	public ListMechanicsByContractCategory(String name) {
		this.name = name;
	}

	public List<MechanicDTO> execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if (gateway.read(name) == null) {
				throw new BusinessException("No existe una categoría de nombre " + name);
			}
			return gateway.list(name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
