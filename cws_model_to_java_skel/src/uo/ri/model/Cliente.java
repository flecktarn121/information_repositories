package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import uo.ri.model.types.Address;

public class Cliente {

	private String dni;
	private String nombre;
	private String apellidos;
	private String email;
	private String phone;
	private Address address;

	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();

	public Cliente(String dni) {
		this.dni = dni;
	}

	public Cliente(String dni, String nombre, String apellidos) {
		this(dni);
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;

	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + "]";
	}

	public Set<Vehiculo> getVehiculos() {
		return new HashSet<Vehiculo>(_getVehiculos());
	}

	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

}
