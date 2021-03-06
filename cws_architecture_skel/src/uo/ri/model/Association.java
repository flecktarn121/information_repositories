package uo.ri.model;

import uo.ri.model.types.AveriaStatus;

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

		public static void link(Cliente cliente, MedioPago medio) {
			medio._setCliente(cliente);
			cliente._getMediosDePago().add(medio);

		}

		public static void unlink(Cliente cliente, MedioPago medio) {
			cliente._getMediosDePago().remove(medio);
			medio._setCliente(null);

		}

	}

	public static class Averiar {

		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo(vehiculo);
			vehiculo._getAverias().add(averia);
			vehiculo.incrementarNumAverias();

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
			averia.setStatus(AveriaStatus.FACTURADA);

		}

		public static void unlink(Factura factura, Averia averia) {
			factura._getAverias().remove(averia);
			averia._setFactura(null);
		}
	}

	public static class Cargar {

		public static void link(MedioPago medio, Cargo cargo, Factura factura) {
			cargo._setFactura(factura);
			cargo._setMedioPago(medio);
			medio._getCargos().add(cargo);
			factura._getCargos().add(cargo);

		}

		public static void unlink(MedioPago medio, Cargo cargo, Factura factura) {
			medio._getCargos().remove(cargo);
			factura._getCargos().remove(cargo);
			cargo._setFactura(null);
			cargo._setMedioPago(null);

		}

		public static void unlink(Cargo cargo) {
			cargo.getMedioPago()._getCargos().remove(cargo);
			cargo.getFactura()._getCargos().remove(cargo);
			cargo._setFactura(null);
			cargo._setMedioPago(null);
		}
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

		public static void link(Repuesto repuesto, Intervencion intrvencion, Sustitucion sustitucion) {
			sustitucion.setIntervencion(intrvencion);
			sustitucion.setRepuesto(repuesto);

			repuesto._getSustituciones().add(sustitucion);
			intrvencion._getSustituciones().add(sustitucion);
		}

		public static void unlink(Sustitucion sustitucion) {
			sustitucion.getIntervencion()._getSustituciones().remove(sustitucion);
			sustitucion.getRepuesto()._getSustituciones().remove(sustitucion);
			sustitucion.setIntervencion(null);
			sustitucion.setRepuesto(null);

		}
	}

	public static class Categorize {

		public static void link(Contract c, ContractCategory category) {
			c._setCategory(category);
			category._getContracts().add(c);

		}

	}

	public static class Typefy {

		public static void link(Contract c, ContractType type) {
			c._setType(type);
			type._getContracts().add(c);

		}

	}

	public static class Vinculate {
		public static void link(Contract c, Mecanico m) {
			c._setMechanic(m);
			m._getContracts().add(c);
		}

		public static void unLink(Contract c, Mecanico m) {
			m._getContracts().remove(c);
			c._setMechanic(null);
		}
	}

	public static class Percibir {
		public static void link(Contract c, Payroll n) {
			n.setContract(c);
			c._getPayrolls().add(n);
		}

		public static void unLink(Contract c, Payroll n) {
			c._getPayrolls().remove(n);
			n.setContract(c);
		}
	}

}
