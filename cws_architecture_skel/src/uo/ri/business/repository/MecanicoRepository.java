package uo.ri.business.repository;

import uo.ri.model.Mecanico;

public interface MecanicoRepository extends Repository<Mecanico> {

	/**
	 * @param dni
	 * @return the mechanic identified by the dni or null if none 
	 */
	Mecanico findByDni(String dni);
	
}
