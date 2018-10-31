package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import uo.ri.model.types.AveriaStatus;

public class Averia {

	private String descripcion;
	private Date fecha;
	private double importe = 0.0;
	private AveriaStatus status = AveriaStatus.ABIERTA;

	private Vehiculo vehiculo;
	private Factura factura;
	private Mecanico mecanico;

	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();

	public Averia(Vehiculo vehiculo) {
		super();
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Averia(Vehiculo vehiculo2, String descripcion) {
		this(vehiculo2);
		this.descripcion = descripcion;
	}

	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException
	 *             si - La avería no está en estado ABIERTA, o - La avería ya está
	 *             enlazada con otro mecánico
	 */
	public void assignTo(Mecanico mecanico) {
		// Solo se puede asignar una averia que está ABIERTA
		// linkado de averia y mecanico
		// la averia pasa a ASIGNADA
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el importe y
	 * pasa a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La avería no está en estado ASIGNADA, o - La avería no está
	 *             enlazada con un mecánico
	 */
	public void markAsFinished() {
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
		// Se verifica que está en estado TERMINADA
		// Se pasa la averia a ABIERTA
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
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria() Marca
	 * la averia como FACTURADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException
	 *             si - La averia no está en estado TERMINADA, o - La avería no está
	 *             enlazada con una factura
	 */
	public void markAsInvoiced() {
		if (factura == null) {
			throw new IllegalStateException("Esta avería no tiene factura asignada.");
		} else if (this.status.equals(AveriaStatus.TERMINADA)) {
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

		return intervenciones.parallelStream().map(Intervencion::getImporte).reduce(0.0, ((acc, pasta) -> acc + pasta));

	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	void setStatus(AveriaStatus status) {
		this.status = status;
	}

}
