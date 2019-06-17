package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractCrudGateway;

public class FinishContract {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private Long id;
	private Date date;

	public FinishContract(Long id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}

	public void execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);

			ContractDto dto = gateway.read(id);
			if (dto == null) {
				throw new BusinessException("No existe contrato con id " + id);
			}
			if(Dates.isBefore(date, new Date())) {
				throw new BusinessException("Fecha inválida.");
			}

			gateway.finish(id, Dates.lastDayOfMonth(date));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}

}
