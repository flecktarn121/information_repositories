package uo.ri.bussiness.contract.crud;

import java.sql.SQLException;
import java.util.List;

import alb.util.console.Printer;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class ListMechanicsByContractType {
	ContractTypeDto dto;

	public ListMechanicsByContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			List<MechanicDTO> mechanics = PersistenceFactory.getContractTypeCrudGateway().list(dto.name);
			int numMechanics = PersistenceFactory.getContractTypeCrudGateway().sumNumberOfWorkers(dto.name);
			double sumSalary = PersistenceFactory.getContractTypeCrudGateway().sumBaseSalary(dto.name);
			mechanics.forEach(
					(mechanic) -> Printer.print("Nombre: " + mechanic.name + " Apellidos: " + mechanic.surname));
			Printer.print("Numero de mecanicos: " + numMechanics);
			Printer.print("Suma del salario base de todos los mecanicos: " + sumSalary);
		} catch (SQLException e) {
			throw new BusinessException("No ha sido posible listar los mecanicos segun el tipo de contrato.");
		}
	}
}
