package uo.ri.bussiness.contract.crud;

import java.sql.SQLException;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;

public class UpdateContractType {
	ContractTypeDto dto;

	public UpdateContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			PersistenceFactory.getContractTypeCrudGateway().update(dto);
		} catch (SQLException e) {

			throw new BusinessException("No ha sido posible actualizar el tipo de contrato.");
		}
	}
}
