package uo.ri.model;

public class Bono extends MedioPago {

	private String codigo;
	private double disponible = 0.0;
	private String descripcion;

	public Bono(String codigo) {
		super();
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public double getDisponible() {
		return disponible;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Bono other = (Bono) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bono [codigo=" + codigo + ", disponible=" + disponible + ", descripcion=" + descripcion
				+ ", toString()=" + super.toString() + "]";
	}

}
