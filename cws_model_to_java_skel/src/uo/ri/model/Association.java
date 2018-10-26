package uo.ri.model;

public class Association {

	public static class Poseer {

		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo._setCliente(cliente);
			cliente._getVehiculos().add(vehiculo);

		}

		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			cliente._getVehiculos().remove(vehiculo);
			vehiculo._setCliente(null);

		}
	}

	public static class Clasificar {
	}

	public static class Pagar {
	}

	public static class Averiar {
	}

	public static class Facturar {
	}

	public static class Cargar {
	}

	public static class Asignar {
	}

	public static class Intervenir {
	}

	public static class Sustituir {
	}

}
