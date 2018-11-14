package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TCARGOS", uniqueConstraints = { @UniqueConstraint(columnNames = { "FACTURA_ID", "MEDIOPAGO_ID" }) })
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Factura factura;
	@ManyToOne
	private MedioPago medioPago;
	private double importe = 0.0;

	Cargo() {

	}

	public Cargo(Factura factura, MedioPago medioPago, double importe) {
		medioPago.pagar(importe);
		this.importe = importe;
		Association.Cargar.link(medioPago, this, factura);
		// incrementar el importe en el acumulado del medio de pago
		// guardar el importe
		// enlazar (link) factura, este cargo y medioDePago
	}

	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago Solo se puede
	 * hacer si la factura no está abonada Decrementar el acumulado del medio de
	 * pago Desenlazar el cargo de la factura y el medio de pago
	 * 
	 * @throws IllegalStateException
	 *             if the invoice is already settled
	 */
	public void rewind() {
		if (this.factura.getStatus().equals(FacturaStatus.ABONADA)) {
			throw new IllegalStateException("La factura esta abonada");
		} else {
			this.medioPago.pagar(-this.importe);
			Association.Cargar.unlink(this);
		}
		// verificar que la factura no esta ABONADA
		// decrementar acumulado en medio de pago (medioPago.pagar( -importe ))
		// desenlazar factura, cargo y medio de pago
	}

	public Factura getFactura() {
		return factura;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public double getImporte() {
		return this.importe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * result + ((medioPago == null) ? 0 : medioPago.hashCode());
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals(other.medioPago))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cargo [factura=" + factura + ", medioPago=" + medioPago + ", importe=" + importe + "]";
	}

	public Long getId() {
		return id;
	}

}
