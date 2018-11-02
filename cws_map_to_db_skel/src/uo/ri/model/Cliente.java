package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uo.ri.model.types.Address;

@Entity
@Table(name="TCLIENTES")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String dni;
	private String nombre;
	private String apellidos;
	private String email;
	private String phone;
	private Address address;

	@OneToMany(mappedBy = "cliente")
	private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();
	@OneToMany(mappedBy = "cliente")
	private Set<MedioPago> mediosDePago = new HashSet<MedioPago>();

	Cliente() {

	}

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

	public Set<MedioPago> getMediosDePago() {
		return new HashSet<MedioPago>(mediosDePago);
	}

	Set<MedioPago> _getMediosDePago() {
		return mediosDePago;
	}

	public Set<MedioPago> getMediosPago() {

		return new HashSet<MedioPago>(mediosDePago);
	}

	public Long getId() {
		return id;
	}

	public void setAddress(Address address2) {
		this.address = address2;

	}

}
