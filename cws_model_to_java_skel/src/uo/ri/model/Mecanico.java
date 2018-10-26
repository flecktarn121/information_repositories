package uo.ri.model;

public class Mecanico {

	private String dni;
	private String apellidos;
	private String nombre;
	public Mecanico(String dni) {
		super();
		this.dni = dni;
	}
	public String getDni() {
		return dni;
	}
	public String getApellidos() {
		return apellidos;
	}
	public String getNombre() {
		return nombre;
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
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Mecanico [dni=" + dni + ", apellidos=" + apellidos + ", nombre=" + nombre + "]";
	}
	
}
