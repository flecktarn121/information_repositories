package uo.ri.bussiness.serviceLayer.implementation;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.bussiness.mechanic.AddMechanic;
import uo.ri.bussiness.mechanic.DeleteMechanic;
import uo.ri.bussiness.mechanic.ListAllMechanic;
import uo.ri.bussiness.mechanic.ListMechanic;
import uo.ri.bussiness.mechanic.UpdateMechanic;
import uo.ri.bussiness.serviceLayer.IMechanicService;
import uo.ri.persistencia.PersistanceException;

public class MechanicService implements IMechanicService {

	@Override
	public void addMechanic(MechanicDTO dto) throws BusinessException {
		try {
			new AddMechanic().execute(dto.dni, dto.name, dto.surname);
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public void updateMechanic(MechanicDTO dto) throws BusinessException {
		try {
			new UpdateMechanic(dto.name, dto.surname, dto.id);
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void deleteMechanic(MechanicDTO dto) throws BusinessException {
		try {
			new DeleteMechanic(dto.id);
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public List<MechanicDTO> listMechanics() throws BusinessException {
		try {
			return new ListMechanic().execute();
		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public List<MechanicDTO> listAllMechanics() throws BusinessException {
		try {
			return new ListAllMechanic().execute();

		} catch (PersistanceException e) {
			throw new BusinessException(e.getMessage());
		}
	}

}
