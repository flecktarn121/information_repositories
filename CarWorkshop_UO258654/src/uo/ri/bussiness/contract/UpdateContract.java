package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractCrudGateway;

public class UpdateContract {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private ContractDto dto;

	public UpdateContract(ContractDto dto) {
		super();
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);

			if (gateway.read(dto.id) == null) {
				throw new BusinessException("No existe un contrato con id " + dto.id);
			}
			
			gateway.update(dto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}

	}
}
