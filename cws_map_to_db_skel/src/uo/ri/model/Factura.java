package uo.ri.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name="TFACTURAS")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long numero;
	@Temporal(TemporalType.DATE)private Date fecha;
	private double importe;
	private double iva;
	@Enumerated(EnumType.STRING)private FacturaStatus status = FacturaStatus.SIN_ABONAR;

	@OneToMany(mappedBy="factura")
	private Set<Averia> averias = new HashSet<Averia>();
	@OneToMany(mappedBy="factura")
	private Set<Cargo> cargos = new HashSet<Cargo>();

	Factura() {

	}

	public Factura(long l, Date today) {
		this(l);
		fecha = new Date(today.getTime());
	}

	public Factura(long numero) {
		this.numero = numero;
	}

	public Factura(long numero, List<Averia> averias) {
		this.numero = numero;
		this.comprobarAverias(averias);
		this.averias = new HashSet<Averia>(averias);
		this.status = FacturaStatus.SIN_ABONAR;
	}

	public Factura(long numero, Date fecha, List<Averia> averias) {
		this(numero, fecha);
		this.averias = new HashSet<Averia>(averias);
	}

	private void comprobarAverias(List<Averia> averias2) {
		averias.parallelStream().forEach((averia) -> {
			if (!averia.getStatus().equals(AveriaStatus.TERMINADA)) {
				throw new IllegalStateException("Una de las averías no está cerrada.");
			}
		});

	}

	/**
	 * Añade la averia a la factura y actualiza el importe e iva de la factura
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si la factura no está en estado SIN_ABONAR
	 */
	public void addAveria(Averia averia) {
		if (!this.status.equals(FacturaStatus.SIN_ABONAR)) {
			throw new IllegalStateException("La factura ya está abonada");
		}
		Association.Facturar.link(this, averia);
		this.calcularImporte();
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de
	 * factura
	 */
	void calcularImporte() {
		this.importe = averias.parallelStream().map(Averia::getImporte).reduce(0.0, ((a, b) -> a + b));

		Date antesRajoy = new GregorianCalendar(2012, Calendar.JULY, 1).getTime();

		if (!new Date().equals(antesRajoy)) {
			this.iva = 0.18;
		} else {
			this.iva = 0.21;
		}
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula el
	 * importe
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si la factura no está en estado SIN_ABONAR
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		// desenlazar factura y averia
		// retornar la averia al estado FINALIZADA ( averia.markBackToFinished() )
		// volver a calcular el importe
	}

	/**
	 * Marks the invoice as ABONADA, but
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             if - Is already settled, or - the amounts paid with charges to
	 *             payment means does not cover the total of the invoice
	 */
	public void settle() {
		if (status.equals(FacturaStatus.ABONADA)) {
			throw new IllegalStateException("La factura ya está abonada.");
		} else if (this.sumarCargos() != importe) {
			throw new IllegalStateException("Los pagos hehcos no cubren el total del importe de la factura.");
		} else {
			this.status = FacturaStatus.ABONADA;
		}
	}

	private double sumarCargos() {

		return this.cargos.parallelStream().map(Cargo::getImporte).reduce(0.0, ((a, b) -> a + b));
	}

	public Set<Averia> getAverias() {
		return new HashSet<Averia>(averias);
	}

	Set<Averia> _getAverias() {
		return averias;
	}

	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return new Date(fecha.getTime());
	}

	public double getImporte() {
		return importe;
	}

	public double getIva() {
		return iva;
	}

	public FacturaStatus getStatus() {
		return status;
	}

	public void setFecha(Date now) {
		this.fecha = new Date(now.getTime());

	}

	public Long getId() {
		return id;
	}

}
