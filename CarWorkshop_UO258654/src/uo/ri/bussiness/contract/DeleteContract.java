package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.BreakdownDTO;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.BreakdownGateway;
import uo.ri.persistencia.ContractCrudGateway;

public class DeleteContract {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private BreakdownGateway bdGateway = PersistenceFactory.getBreakDownGateway();
	private Long id;
	public DeleteContract(Long id) {
		super();
		this.id = id;
	}
	
	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);
			bdGateway.setConnection(connection);
			ContractDto dto = gateway.read(id);
			if(dto==null) {
				throw new BusinessException("No existe contrato con id "+id);
			}
			if(dto.status=="ACTIVE") {
				throw new BusinessException("No se pueden eliminar contratos activos");
			}
			if(hasBreakdowns(dto)) {
				throw new BusinessException("No se pueden eliminar contratos con actividad durante su vigencia");
			}
			
			gateway.delete(id);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(connection);
		}
	}

	private boolean hasBreakdowns(ContractDto dto) {
		List<BreakdownDTO> breakdowns = bdGateway.getBreakdownsByMechanic(dto.mechanicId);
		for(BreakdownDTO breakdown: breakdowns) {
			if(Dates.isDateInWindow(breakdown.date, dto.startDate, dto.endDate)) {
				return true;
			}
		}
		return false;
	}
	
}
