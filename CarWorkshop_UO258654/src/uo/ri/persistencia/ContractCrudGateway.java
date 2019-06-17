package uo.ri.persistencia;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.PayrollDto;

public interface ContractCrudGateway {
	void setConnection(Connection connection);
	
	void write(ContractDto dto);

	ContractDto read(Long id);

	void delete(Long id);

	void update(ContractDto dto);
	
	List<ContractDto> getContractsForMechanicId(Long id);
	
	void finish(Long id, Date endDate);
	
	ContractCategoryDto getCategoryByContractId(Long id);
	
	List<PayrollDto> getPayrollsForContractId(Long id);
	
}
