package uo.ri.model;

import java.util.Date;

import uo.ri.model.types.AveriaStatus;

public class Averia {

	private String descripcion;
	private Date fecha;
	private double importe = 0.0;
	private AveriaStatus status = AveriaStatus.ABIERTA;
	
	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado ABIERTA, o
	 *  - La avería ya está enlazada con otro mecánico
	 */
	public void assignTo(Mecanico mecanico) {
		// Solo se puede asignar una averia que está ABIERTA
		// linkado de averia y mecanico
		// la averia pasa a ASIGNADA
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el 
	 * importe y pasa a TERMINADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado ASIGNADA, o
	 *  - La avería no está enlazada con un mecánico
	 */
	public void markAsFinished() {
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico
	 * (p.e., el primero no ha hecho bien la reparación), pero debe ser pasada 
	 * a ABIERTA primero
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La avería no está en estado TERMINADA
	 */
	public void reopen() {
		// Se verifica que está en estado TERMINADA
		// Se pasa la averia a ABIERTA
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.removeAveria()
	 * Retrocede la averia a TERMINADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado FACTURADA, o 
	 *  - La avería aún está enlazada con la factura
	 */
	public void markBackToFinished() {
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria()
	 * Marca la averia como FACTURADA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado TERMINADA, o 
	 *  - La avería no está enlazada con una factura
	 */
	public void markAsInvoiced() {
	}

	/**
	 * Desvincula la avería en estado ASIGNADA del mecánico y la pasa a ABIERTA
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si
	 * 	- La averia no está en estado ASIGNADA, o 
	 */
	public void desassign() {
	}

}
