package uo.ri.bussiness.contract.crud;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractTypecrudGateway;

public class DeleteContractType {
	private Connection connection;
	private String name;
	private ContractTypecrudGateway gateway = PersistenceFactory.getContractTypeCrudGateway();

	public DeleteContractType(ContractTypeDto dto) {
		this.name = dto.name;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if (gateway.read(name) == null) {
				throw new BusinessException("No existe un tipo de contrato con el nombre " + name);
			}
			if(!gateway.list(name).isEmpty()) {
				throw new BusinessException("Aún existen contratos activos del tipo " + name);
			}
			gateway.delete(name);
		} catch (SQLException e) {
			throw new BusinessException("No ha sido posible borrar el tipo de contrato.");
		} finally {
			Jdbc.close(connection);
		}
	}
}
