package uo.ri.bussiness.serviceLayer;

import java.util.List;

import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;

public interface IMechanicService {
	/**
	 * Add a new mechanic to the system with the data specified in the dto. The id
	 * value will be ignored
	 * 
	 * @param mecanico
	 *            dto
	 * @throws BusinessException
	 *             if there already exist another mechanic with the same dni
	 */
	public void addMechanic(MechanicDTO dto) throws BusinessException;

	/**
	 * Updates values for the mechanic specified by the id field, just name and
	 * surname will be updated
	 * 
	 * @param mecanico
	 *            dto, the id field, name and surname cannot be null
	 * @throws BusinessException
	 *             if the mechanic does not exist
	 */
	public void updateMechanic(MechanicDTO dto) throws BusinessException;

	/**
	 * @param MechanicDTO
	 * @throws BusinessException
	 *             if the mechanic does not exist
	 */
	public void deleteMechanic(MechanicDTO dto) throws BusinessException;

	/**
	 * @return the list of mechanics with active contract, or an empty list DO
	 *         NOT @throws BusinessException
	 */
	public List<MechanicDTO> listMechanics() throws BusinessException;

	/**
	 * @return the list of all mechanics registered in the system without regarding
	 *         their contract status or if they have contract or not. It might be an
	 *         empty list if there is no mechanic
	 * 
	 *         DO NOT @throws BusinessException
	 */
	public List<MechanicDTO> listAllMechanics() throws BusinessException;
}
