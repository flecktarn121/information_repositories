package uo.ri.bussiness.serviceLayer.implementation;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.bussiness.mechanic.AddMechanic;
import uo.ri.bussiness.mechanic.DeleteMechanic;
import uo.ri.bussiness.mechanic.ListMechanic;
import uo.ri.bussiness.mechanic.UpdateMechanic;
import uo.ri.bussiness.serviceLayer.IMechanicService;

public class MechanicService implements IMechanicService {

	@Override
	public void addMechanic(MechanicDTO dto) throws BusinessException {
		new AddMechanic().execute(dto.dni, dto.name, dto.surname);
		
	}

	@Override
	public void updateMechanic(MechanicDTO dto) throws BusinessException {
		new UpdateMechanic(dto.name, dto.surname, dto.id);
		
	}

	@Override
	public void deleteMechanic(MechanicDTO dto) throws BusinessException {
		new DeleteMechanic(dto.id);
		
	}

	@Override
	public List<MechanicDTO> listMechanics() throws BusinessException {
		return new ListMechanic().execute();
	}

}
