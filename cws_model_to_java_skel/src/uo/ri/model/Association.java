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

		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo._setTipo(tipoVehiculo);
			tipoVehiculo._getVehiculos().add(vehiculo);

		}

		public static void unlink(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			tipoVehiculo._getVehiculos().remove(vehiculo);
			vehiculo._setTipo(null);

		}
	}

	public static class Pagar {
	}

	public static class Averiar {

		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo(vehiculo);
			vehiculo._getAverias().add(averia);

		}

		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove(averia);
			averia._setVehiculo(null);

		}
	}

	public static class Facturar {

		public static void link(Factura factura, Averia averia) {
			averia._setFactura(factura);
			factura._getAverias().add(averia);

		}

		public static void unlink(Factura factura, Averia averia) {
			factura._getAverias().remove(averia);
			averia._setFactura(null);
		}
	}

	public static class Cargar {
	}

	public static class Asignar {

		public static void link(Mecanico mecanico, Averia averia) {
			averia._setMecanico(mecanico);
			mecanico._getAverias().add(averia);

		}

		public static void unlink(Mecanico mecanico, Averia averia) {
			mecanico._getAverias().remove(averia);
			averia._setMecanico(null);
		}
	}

	public static class Intervenir {

		public static void link(Averia averia, Intervencion intervencion, Mecanico mecanico) {
			intervencion._setAveria(averia);
			intervencion._setMecanico(mecanico);

			mecanico._getIntervenciones().add(intervencion);
			averia._getIntervenciones().add(intervencion);

		}

		public static void unlink(Intervencion intervencion) {
			Mecanico mecanico = intervencion.getMecanico();
			mecanico._getIntervenciones().remove(intervencion);
			Averia averia = intervencion.getAveria();
			averia._getIntervenciones().remove(intervencion);

			intervencion._setAveria(null);
			intervencion._setMecanico(null);

		}
	}

	public static class Sustituir {
	}

}
