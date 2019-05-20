package uo.ri.bussiness.mechanic;

import java.util.List;

import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.PersistenceFactory;

public class ListMechanic {
	public List<MechanicDTO> execute() {
		return PersistenceFactory.getMechanicGateway().read();
	}
}
