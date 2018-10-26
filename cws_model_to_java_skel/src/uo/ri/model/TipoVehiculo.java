package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class TipoVehiculo {
	private String nombre;
	private double precioHora;

	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();

	public TipoVehiculo(String nombre) {
		super();
		this.nombre = nombre;
	}

	public TipoVehiculo(String nombre, double precioHora) {
		this(nombre);
		this.precioHora = precioHora;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecioHora() {
		return precioHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoVehiculo other = (TipoVehiculo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoVehiculo [nombre=" + nombre + ", precioHora=" + precioHora + "]";
	}

	public Set<Vehiculo> getVehiculos() {
		return new HashSet<Vehiculo>(vehiculos);
	}

	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

}
