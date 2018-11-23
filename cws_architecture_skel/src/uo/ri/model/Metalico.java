package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TMETALICOS")
public class Metalico extends MedioPago {

	Metalico() {

	}

	public Metalico(Cliente cliente) {
		Association.Pagar.link(cliente, this);
	}

	@Override
	public String toString() {
		return "Metalico [toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		MedioPago other = (MedioPago) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}
}
