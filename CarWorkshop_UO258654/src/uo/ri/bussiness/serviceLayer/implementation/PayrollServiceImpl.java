package uo.ri.bussiness.serviceLayer.implementation;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.bussiness.serviceLayer.PayrollService;
import uo.ri.configuration.PersistenceFactory;

public class PayrollServiceImpl implements PayrollService {
	private static final int THIRTY_PERCENT_IRPF = 60000;
	private static final int TWENTY_PERCENT_IRPF = 50000;
	private static final int FIFTEEN_PERCENT_IRPF = 40000;
	private static final int TEN_PERCENT_IRPF = 30000;
	private static final int ZERO_PERCENT_IRPF = 12000;

	@Override
	public List<PayrollDto> findAllPayrolls() throws BusinessException {
		return PersistenceFactory.getPayrollGateway().findAllPayrolls();
	}

	@Override
	public List<PayrollDto> findPayrollsByMechanicId(Long id) throws BusinessException {
		return PersistenceFactory.getPayrollGateway().findPayrollsByMechanicId(id);
	}

	@Override
	public PayrollDto findPayrollById(Long id) throws BusinessException {

		return PersistenceFactory.getPayrollGateway().findPayrollById(id);
	}

	@Override
	public void deleteLastPayrollForMechanicId(Long id) throws BusinessException {
		PersistenceFactory.getPayrollGateway().deleteLastPayrollForMechanicId(id);
	}

	@Override
	public int deleteLastGenetaredPayrolls() throws BusinessException {
		return PersistenceFactory.getPayrollGateway().deleteLastGenetaredPayrolls();
	}

	@Override
	public int generatePayrolls() throws BusinessException {
		List<ContractDto> contracts = new ArrayList<ContractDto>();
		List<ContractCategoryDto> categories = new ArrayList<ContractCategoryDto>();
		List<ContractTypeDto> types = new ArrayList<ContractTypeDto>();

		PersistenceFactory.getPayrollGateway().getContracts(contracts, categories, types);
		int counter = 0;
		for (int i = 0; i < contracts.size(); i++) {
			generatePayroll(contracts.get(i), types.get(i), categories.get(i));
			counter++;
		}
		return counter;
	}

	private void generatePayroll(ContractDto contract, ContractTypeDto type, ContractCategoryDto category) {
		double baseSalary = contract.yearBaseSalary / 14;
		LocalDate date = new Date(System.currentTimeMillis()).toLocalDate().minusMonths(1);// a date to get the previous
		double estraSalary = 0; // moth
		if (date.getMonth().equals(Month.JUNE) || date.getMonth().equals(Month.DECEMBER)) {
			estraSalary = baseSalary;
		}
		double productivity = calculateProductivity(contract.mechanicId, category);
		double trieniums = calculateTrieniums(contract, category);
		double irpf = calulateIRPF(baseSalary, estraSalary, productivity, trieniums);
		double socialSecurity = baseSalary * 0.05 / 12;

		PayrollDto dto = new PayrollDto();
		dto.baseSalary = baseSalary;
		dto.extraSalary = estraSalary;
		dto.productivity = productivity;
		dto.triennium = trieniums;
		dto.irpf = irpf;
		dto.socialSecurity = socialSecurity;
		dto.grossTotal = baseSalary + estraSalary + productivity + trieniums;
		dto.netTotal = dto.grossTotal - (irpf + socialSecurity);

		PersistenceFactory.getPayrollGateway().generatePayroll(dto, contract.id);

	}

	private double calulateIRPF(double baseSalary, double estraSalary, double productivity, double trieniums) {
		double total = baseSalary + estraSalary + productivity + trieniums;
		double irpf;
		if (baseSalary < ZERO_PERCENT_IRPF) {
			irpf = 0;
		} else if (baseSalary < TEN_PERCENT_IRPF) {
			irpf = 0.10;
		} else if (baseSalary < FIFTEEN_PERCENT_IRPF) {
			irpf = 0.15;
		} else if (baseSalary < TWENTY_PERCENT_IRPF) {
			irpf = 0.20;
		} else if (baseSalary < THIRTY_PERCENT_IRPF) {
			irpf = 0.30;
		} else {
			irpf = 0.40;
		}
		return irpf * total;
	}

	private double calculateTrieniums(ContractDto contract, ContractCategoryDto category) {
		LocalDate date = new Date(System.currentTimeMillis()).toLocalDate().minusMonths(1);
		int contractStartYear = new Date(contract.startDate.getTime()).toLocalDate().getYear();
		int value = (date.getYear() - contractStartYear) / 3;
		return category.trieniumSalary * value;
	}

	private double calculateProductivity(Long mechanicId, ContractCategoryDto category) {
		Date date = Date.valueOf(new Date(System.currentTimeMillis()).toLocalDate().minusMonths(1));
		return PersistenceFactory.getBreakDownGateway().getImportePorMecanicoYFecha(mechanicId, date);

	}

}
