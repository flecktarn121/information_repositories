package uo.ri.persistencia;

import java.util.List;

import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.PayrollDto;

public interface PayrollGateway {
	public List<PayrollDto> findAllPayrolls();

	public List<PayrollDto> findPayrollsByMechanicId(Long id);

	public PayrollDto findPayrollById(Long id);

	public void deleteLastPayrollForMechanicId(Long id);

	public int deleteLastGenetaredPayrolls();

	public void generatePayroll(PayrollDto dto,Long contractId);
	
	public void getContracts(List<ContractDto> contracts, List<ContractCategoryDto> categories, List<ContractTypeDto> types);
}
