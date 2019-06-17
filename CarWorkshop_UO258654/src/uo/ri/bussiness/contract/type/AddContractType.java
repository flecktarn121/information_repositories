package uo.ri.bussiness.contract.type;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractTypecrudGateway;
import uo.ri.persistencia.PersistanceException;

public class AddContractType {
	private ContractTypeDto dto;
	private ContractTypecrudGateway gateway = PersistenceFactory.getContractTypeCrudGateway();
	private Connection connection;
	public AddContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if(gateway.read(dto.name) != null) {
				throw new BusinessException("Ya existe un tipo de contrato con nombre "+dto.name);
			}
			gateway.write(dto.name, dto.compensationDays);
		} catch (SQLException e) {
			throw new PersistanceException("Problemas al conectar a la base de datos.");
		}finally {
			Jdbc.close(connection);
		}
	}
}
