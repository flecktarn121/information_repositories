package uo.ri.bussiness.serviceLayer.implementation;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.contract.crud.AddContractType;
import uo.ri.bussiness.contract.crud.DeleteContractType;
import uo.ri.bussiness.contract.crud.ListMechanicsByContractType;
import uo.ri.bussiness.contract.crud.UpdateContractType;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.persistencia.PersistanceException;

public class ContractTypeCrudService implements uo.ri.bussiness.serviceLayer.ContractTypeCrudService {

	@Override
	public void addContractType(ContractTypeDto dto) throws BusinessException {
		try {
			new AddContractType(dto).execute();
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void updateContractType(ContractTypeDto dto) throws BusinessException {
		try {
			if (dto.compensationDays < 0) {
				throw new BusinessException("Los días de compensación no pueden ser negativos.");
			}
			new UpdateContractType(dto).execute();
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void deleteContractType(ContractTypeDto dto) throws BusinessException {
		try {

			new DeleteContractType(dto).execute();
			;
		} catch (

		PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void listMechanicsByContractType(ContractTypeDto dto) throws BusinessException {
		try {

			new ListMechanicsByContractType(dto).execute();
			;
		} catch (

		PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

}
