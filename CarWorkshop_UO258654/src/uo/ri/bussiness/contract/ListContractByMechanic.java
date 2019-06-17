package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractCrudGateway;
import uo.ri.persistencia.MechanicGateway;

public class ListContractByMechanic {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private Long id;
	
	public ListContractByMechanic(Long id) {
		super();
		this.id = id;
	}

	public List<ContractDto> execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);

			MechanicDTO dto = getMechanic(id);
			if(dto==null) {
				throw new BusinessException("No existe mecánico con id "+id);
			}
			
			return gateway.getContractsForMechanicId(id);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}

	private MechanicDTO getMechanic(Long id2) {
		MechanicGateway mechanicGateway = PersistenceFactory.getMechanicGateway();
		mechanicGateway.setConnection(connection);
		List<MechanicDTO> mechanics = mechanicGateway.readAll();
		for (MechanicDTO dto : mechanics) {
			if (dto.id == id2) {
				return dto;
			}
		}
		return null;
	}
}
