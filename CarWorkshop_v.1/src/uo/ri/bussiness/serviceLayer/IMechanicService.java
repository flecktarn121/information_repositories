package uo.ri.bussiness.serviceLayer;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;

public interface IMechanicService {
	public void addMechanic(MechanicDTO dto)throws BusinessException;
	public void updateMechanic(MechanicDTO dto)throws BusinessException;
	public void deleteMechanic(MechanicDTO dto)throws BusinessException;
	public List<MechanicDTO> listMechanics()throws BusinessException;
}
