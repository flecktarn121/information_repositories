package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.AveriaStatus;

@Entity
@Table(name = "TAVERIAS", uniqueConstraints = { @UniqueConstraint(columnNames = { "VEHICULO_ID", "FECHA" }) })
public class Averia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;

	@ManyToOne
	private Vehiculo vehiculo;
	@ManyToOne
	private Factura factura;
	@ManyToOne
	private Mecanico mecanico;

	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();

	Averia() {
	}

	public Averia(Vehiculo vehiculo) {
		super();
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Averia(Vehiculo vehiculo2, String descripcion) {
		this(vehiculo2);
		this.setDescripcion(descripcion);
	}

	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException
	 *             si - La avería no está en estado ABIERTA, o - La avería ya
	 *             está enlazada con otro mecánico
	 */
	public void assignTo(Mecanico mecanico) {
		if (!this.status.equals(AveriaStatus.ABIERTA) || this.mecanico != null) {
			throw new IllegalStateException("La averia no esta en estado abierta.");
		} else {
			Association.Asignar.link(mecanico, this);
			this.status = AveriaStatus.ASIGNADA;
		}
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el importe y
	 * pasa a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La avería no está en estado ASIGNADA, o - La avería no
	 *             está enlazada con un mecánico
	 */
	public void markAsFinished() {
		if (!this.status.equals(AveriaStatus.ASIGNADA) || this.mecanico == null) {
			throw new IllegalStateException("La averia no esta en estado asignada o no tiene mecanico.");
		} else {
			this.importe = this.calcularImporte();
			this.status = AveriaStatus.TERMINADA;
			Association.Asignar.unlink(this.mecanico, this);
		}
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico (p.e., el
	 * primero no ha hecho bien la reparación), pero debe ser pasada a ABIERTA
	 * primero
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La avería no está en estado TERMINADA
	 */
	public void reopen() {
		if (!this.status.equals(AveriaStatus.TERMINADA)) {
			throw new IllegalStateException("La averia no esta terminada.");

		} else {
			this.status = AveriaStatus.ABIERTA;
		}

	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.removeAveria()
	 * Retrocede la averia a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La averia no está en estado FACTURADA, o - La avería aún
	 *             está enlazada con la factura
	 */
	public void markBackToFinished() {
		if (!this.status.equals(AveriaStatus.FACTURADA) || this.factura != null) {
			throw new IllegalStateException("La averia no está facturada o aun tiene la factura asignada");
		} else {
			this.status = AveriaStatus.TERMINADA;
		}
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria() Marca
	 * la averia como FACTURADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La averia no está en estado TERMINADA, o - La avería no
	 *             está enlazada con una factura
	 */
	public void markAsInvoiced() {
		if (factura == null) {
			throw new IllegalStateException("Esta avería no tiene factura asignada.");
		} else if (!this.status.equals(AveriaStatus.TERMINADA)) {
			throw new IllegalStateException("Esta avería no está terminada.");
		} else {
			this.status = AveriaStatus.FACTURADA;
		}
	}

	/**
	 * Desvincula la avería en estado ASIGNADA del mecánico y la pasa a ABIERTA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La averia no está en estado ASIGNADA, o
	 */
	public void desassign() {
		if (!this.status.equals(AveriaStatus.ASIGNADA)) {
			throw new IllegalStateException("La averia no esta asignada");
		} else {
			Association.Asignar.unlink(this.mecanico, this);
		}
	}

	public AveriaStatus getStatus() {
		return status;
	}

	public void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;

	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>(intervenciones);
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public double getImporte() {
		this.importe = this.calcularImporte();
		return this.importe;

	}

	private double calcularImporte() {
		return intervenciones.parallelStream()
				.map(Intervencion::getImporte)
				.reduce(0.0, ((acc, pasta) -> acc + pasta));
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	void setStatus(AveriaStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
