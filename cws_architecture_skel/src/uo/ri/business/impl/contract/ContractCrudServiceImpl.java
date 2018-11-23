package uo.ri.business.impl.contract;

import java.util.Date;
import java.util.List;

import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

	@Override
	public void addContract(ContractDto c) throws BusinessException {

	}

	@Override
	public void updateContract(ContractDto dto) throws BusinessException {

	}

	@Override
	public void deleteContract(Long id) throws BusinessException {

	}

	@Override
	public void finishContract(Long id, Date endDate) throws BusinessException {

	}

	@Override
	public ContractDto findContractById(Long id) throws BusinessException {
		return null;
	}

	@Override
	public List<ContractDto> findContractsByMechanicId(Long id) throws BusinessException {
		return null;
	}

}
