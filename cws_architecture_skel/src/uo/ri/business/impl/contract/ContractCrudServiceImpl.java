package uo.ri.business.impl.contract;

import java.util.Date;
import java.util.List;

import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.contract.command.AddContract;
import uo.ri.business.impl.contract.command.DeleteContract;
import uo.ri.business.impl.contract.command.FindContractById;
import uo.ri.business.impl.contract.command.FindContractsByMechanicId;
import uo.ri.business.impl.contract.command.FinishContract;
import uo.ri.business.impl.contract.command.UpdateContract;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.conf.Factory;

public class ContractCrudServiceImpl implements ContractCrudService {
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void addContract(ContractDto c) throws BusinessException {
		executor.execute(new AddContract(c));
	}

	@Override
	public void updateContract(ContractDto dto) throws BusinessException {
		executor.execute(new UpdateContract(dto));
	}

	@Override
	public void deleteContract(Long id) throws BusinessException {
		executor.execute(new DeleteContract(id));
	}

	@Override
	public void finishContract(Long id, Date endDate) throws BusinessException {
		executor.execute(new FinishContract(id, endDate));
	}

	@Override
	public ContractDto findContractById(Long id) throws BusinessException {
		return DtoAssembler.toCOntractDto(executor.execute(new FindContractById(id)));
	}

	@Override
	public List<ContractDto> findContractsByMechanicId(Long id) throws BusinessException {
		return DtoAssembler.toContractDtoList(executor.execute(new FindContractsByMechanicId(id)));
	}

}
