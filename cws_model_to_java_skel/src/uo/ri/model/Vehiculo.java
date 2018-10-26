package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class Vehiculo {

	private String marca;
	private String matricula;
	private String modelo;

	private int numAverias = 0;

	private Cliente cliente;
	
	private TipoVehiculo tipo;
	private Set<Averia> averias = new HashSet<Averia>();

	public Vehiculo(String matricula) {
		this.matricula = matricula;
	}

	public Vehiculo(String matricula, String marca, String modelo) {
		this(matricula);
		this.marca = marca;
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public int getNumAverias() {
		return numAverias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Vehiculo other = (Vehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehiculo [marca=" + marca + ", matricula=" + matricula + ", modelo=" + modelo + ", numAverias="
				+ numAverias + "]";
	}

	public Cliente getCliente() {
		return cliente;
	}

	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoVehiculo getTipo() {
		return tipo;
	}

	void _setTipo(TipoVehiculo tipo) {
		this.tipo = tipo;
	}

	public Set<Averia> getAverias() {
		return new HashSet<Averia>( averias);
	}
	
	Set<Averia> _getAverias() {
		return averias;
	}
}
