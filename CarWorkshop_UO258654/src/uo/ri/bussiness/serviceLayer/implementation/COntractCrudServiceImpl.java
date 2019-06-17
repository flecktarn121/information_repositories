package uo.ri.bussiness.serviceLayer.implementation;

import java.util.Date;
import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.contract.AddContract;
import uo.ri.bussiness.contract.DeleteContract;
import uo.ri.bussiness.contract.FinishContract;
import uo.ri.bussiness.contract.ListContractByMechanic;
import uo.ri.bussiness.contract.PayrollsForContract;
import uo.ri.bussiness.contract.UpdateContract;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.bussiness.serviceLayer.ContractCrudService;

public class COntractCrudServiceImpl implements ContractCrudService {

	@Override
	public void addContract(ContractDto c) throws BusinessException {
		new AddContract(c).execute();
	}

	@Override
	public void updateContract(ContractDto dto) throws BusinessException {
		new UpdateContract(dto).execute();
	}

	@Override
	public void deleteContract(Long id) throws BusinessException {
		new DeleteContract(id).execute();

	}

	@Override
	public void finishContract(Long id, Date endDate) throws BusinessException {
		new FinishContract(id, endDate).execute();
	}

	@Override
	public List<ContractDto> findContractsByMechanicId(Long id) throws BusinessException {
		return new ListContractByMechanic(id).execute();
	}

	@Override
	public List<PayrollDto> findPayrollsForContract(Long id) throws BusinessException {
		return new PayrollsForContract(id).execute();
	}

}
