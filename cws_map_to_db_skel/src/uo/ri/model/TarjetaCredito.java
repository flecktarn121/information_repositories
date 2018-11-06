package uo.ri.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TarjetaCredito extends MedioPago {

	protected String numero;
	protected String tipo;
	@Temporal(TemporalType.DATE)
	protected Date validez;

	TarjetaCredito() {

	}

	public TarjetaCredito(String numero) {
		super();
		this.numero = numero;
	}

	public TarjetaCredito(String string, String string2, Date tomorrow) {

		this.numero = string;
		this.tipo = string2;
		this.validez = new Date(tomorrow.getTime());
	}

	@Override
	public void pagar(double importe) {
		if (new Date(System.currentTimeMillis()).after(this.validez)) {
			throw new IllegalStateException("La tarjeta de credito esta caducada.");
		}
		super.pagar(importe);
	}

	public String getNumero() {
		return numero;
	}

	public String getTipo() {
		return tipo;
	}

	public Date getValidez() {
		return validez;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo + ", validez=" + validez + ", toString()="
				+ super.toString() + "]";
	}

}
