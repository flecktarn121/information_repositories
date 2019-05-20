package uo.ri.bussiness.contract.crud;

import java.sql.SQLException;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.PersistenceFactory;

public class AddContractType {
	ContractTypeDto dto;

	public AddContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			PersistenceFactory.getContractTypeCrudGateway().write(dto.name, dto.compensationDays);
		} catch (SQLException e) {
			throw new BusinessException("No ha sido posible añadir el tipo de contrato.");
		}
	}
}
