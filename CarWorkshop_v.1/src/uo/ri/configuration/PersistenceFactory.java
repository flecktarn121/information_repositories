package uo.ri.configuration;

import uo.ri.persistencia.MechanicGateway;
import uo.ri.persistencia.implementation.MechanicGatewayImpl;

public class PersistenceFactory {
	public static MechanicGateway getMechanicGateway() {
		return new MechanicGatewayImpl();
	}
}
