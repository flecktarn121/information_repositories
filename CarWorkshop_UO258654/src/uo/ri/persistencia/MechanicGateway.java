package uo.ri.persistencia;

import java.util.List;

import uo.ri.bussiness.dto.MechanicDTO;

public interface MechanicGateway {
	/**
	 * Creates a new mechanic
	 * @param mecanico dto
	 * @throws PersistanceException if there already exist another 
	 * 		mechanic with the same dni
	 */
	void create(MechanicDTO dto)throws PersistanceException;
	/**
	 * @return the list of mechanics with active contract, or an empty list
	 * DO NOT @throws PersistanceException
	 */
	List<MechanicDTO> read();
	/**
	 * Updates values for the mechanic specified by the id field, 
	 * 		just name and surname will be updated
	 * @param mecanico dto, the id field, name and surname cannot be null
	 * @throws PersistanceException if the mechanic does not exist
	 */
	void update(MechanicDTO dto)throws PersistanceException;
	/**
	 * Deletes a mechanic using the id of the dto.
	 * 
	 * @param dto
	 * @throws PersistanceException if the mechanic does not exist
	 */
	void delete(MechanicDTO dto)throws PersistanceException;
	/**
	 * @return the list of all mechanics registered in the system
	 * 	without regarding their contract status or if they have 
	 * 	contract or not. It might be an empty list if there is no mechanic
	 * 
	 * DO NOT @throws PersistanceException
	 */
	List<MechanicDTO> readAll();
}
