package uo.ri.bussiness.contract.crud;

import java.sql.SQLException;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;

public class DeleteContractType {

	String name;

	public DeleteContractType(ContractTypeDto dto) {
		this.name = dto.name;
	}

	public void execute() throws BusinessException {
		try {
			PersistenceFactory.getContractTypeCrudGateway().delete(name);
		} catch (SQLException e) {
			throw new BusinessException("No ha sido posible borrar el tipo de contrato.");
		}
	}
}
