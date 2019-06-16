package uo.ri.bussiness.contract.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.console.Printer;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractTypecrudGateway;

public class ListMechanicsByContractType {
	private ContractTypeDto dto;
	private ContractTypecrudGateway gateway = PersistenceFactory.getContractTypeCrudGateway();
	private Connection connection;
	public ListMechanicsByContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			if (gateway.read(dto.name) == null) {
				throw new BusinessException("No existe un tipo de contrato con el nombre " + dto.name);
			}
			
			List<MechanicDTO> mechanics = PersistenceFactory.getContractTypeCrudGateway().list(dto.name);
			int numMechanics = PersistenceFactory.getContractTypeCrudGateway().sumNumberOfWorkers(dto.name);
			double sumSalary = PersistenceFactory.getContractTypeCrudGateway().sumBaseSalary(dto.name);
			mechanics.forEach(
					(mechanic) -> Printer.print("Nombre: " + mechanic.name + " Apellidos: " + mechanic.surname));
			Printer.print("Numero de mecanicos: " + numMechanics);
			Printer.print("Suma del salario base de todos los mecanicos: " + sumSalary);
		} catch (SQLException e) {
			throw new BusinessException("No ha sido posible listar los mecanicos segun el tipo de contrato.");
		} finally {
			Jdbc.close(connection);
		}
	}
}
