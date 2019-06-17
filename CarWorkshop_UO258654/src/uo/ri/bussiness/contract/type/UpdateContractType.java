package uo.ri.bussiness.contract.type;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractTypecrudGateway;

public class UpdateContractType {
	private ContractTypeDto dto;
	private ContractTypecrudGateway gateway = PersistenceFactory.getContractTypeCrudGateway();
	private Connection connection;

	public UpdateContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			gateway.update(dto);
		} catch (SQLException e) {

			throw new BusinessException("No ha sido posible actualizar el tipo de contrato.");
		} finally {
			Jdbc.close(connection);
		}
	}
}
