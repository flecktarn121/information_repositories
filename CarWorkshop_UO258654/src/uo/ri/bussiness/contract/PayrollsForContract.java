package uo.ri.bussiness.contract;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.configuration.PersistenceFactory;
import uo.ri.persistencia.ContractCrudGateway;

public class PayrollsForContract {
	private Connection connection;
	private ContractCrudGateway gateway = PersistenceFactory.getContractCrudGateway();
	private Long id;

	public PayrollsForContract(Long id) {
		super();
		this.id = id;
	}

	public List<PayrollDto> execute() throws BusinessException {
		try {
			connection = Jdbc.getConnection();
			gateway.setConnection(connection);

			if (gateway.read(id) == null) {
				throw new BusinessException("No hay un contrato con id " + id);
			}
			
			return gateway.getPayrollsForContractId(id);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
