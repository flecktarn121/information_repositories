package uo.ri.persistencia;

import java.sql.SQLException;
import java.util.List;

import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.MechanicDTO;

public interface ContractTypecrudGateway {
	public void write(String name, double numberOfDays) throws SQLException;
	public List<MechanicDTO> list(String name) throws SQLException;
	public double sumBaseSalary(String name)throws SQLException;
	public int sumNumberOfWorkers(String name) throws SQLException;
	public void delete(String name) throws SQLException;
	public void update(ContractTypeDto dto) throws SQLException;
}
