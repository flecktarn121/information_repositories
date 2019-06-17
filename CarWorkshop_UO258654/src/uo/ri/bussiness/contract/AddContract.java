package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.CategoryGateway;
import uo.ri.persistencia.ContractCrudGateway;
import uo.ri.persistencia.ContractTypecrudGateway;
import uo.ri.persistencia.MechanicGateway;

public class AddContract {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private MechanicGateway gwMechanic = PersistenceFactory.getMechanicGateway();
	private CategoryGateway gwCat = PersistenceFactory.getCategoryGateway();
	private ContractTypecrudGateway gwType = PersistenceFactory.getContractTypeCrudGateway();
	private ContractDto dto;

	public AddContract(ContractDto dto) {
		super();
		this.dto = dto;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			gwMechanic.setConnection(connection);
			gwCat.setConnection(connection);
			gwType.setConnection(connection);

			ContractCategoryDto cat = gwCat.read(dto.categoryName);
			ContractTypeDto type = gwType.read(dto.typeName);
			MechanicDTO mechanic = getMechanic(dto.mechanicId);

			if (mechanic == null) {
				throw new BusinessException("No existe el mecánico con id " + dto.mechanicId);
			}
			if (cat == null) {
				throw new BusinessException("No existe la categoría " + dto.categoryName);
			}
			if (type == null) {
				throw new BusinessException("No existe el tipo " + dto.typeName);
			}
			if (Dates.isBefore(dto.startDate, new Date())) {
				throw new BusinessException("La fecha de inicio es inválida.");
			}
			if (dto.endDate != null && Dates.isBefore(dto.endDate, new Date())) {
				throw new BusinessException("La fecha de finalización es inválida.");
			}
			if (dto.yearBaseSalary < 0) {
				throw new BusinessException("El salario base no puede ser negativo.");
			}
			dto.mechanicId = mechanic.id;
			dto.categoryId = cat.id;
			dto.typeId = type.id;
			gateway.write(dto);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
		
		

	}

	private MechanicDTO getMechanic(Long mechanicId) {
		List<MechanicDTO> mechanics = gwMechanic.readAll();
		for (MechanicDTO mechanic : mechanics) {
			if (mechanic.id == mechanicId) {
				return mechanic;
			}
		}
		return null;
	}

}
