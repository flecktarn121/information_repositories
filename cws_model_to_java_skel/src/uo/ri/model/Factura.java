package uo.ri.model;

import java.util.Date;

import uo.ri.model.types.FacturaStatus;

public class Factura {

	private Long numero;
	private Date fecha;
	private double importe;
	private double iva;
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;
	
	/**
	 * Añade la averia a la factura y actualiza el importe e iva de la factura
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void addAveria(Averia averia) {
		// Verificar que la factura está en estado SIN_ABONAR
		// Verificar que La averia está TERMINADA
		// linkar factura y averia
		// marcar la averia como FACTURADA ( averia.markAsInvoiced() )
		// calcular el importe
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de 
	 * factura
	 */
	void calcularImporte() {
		// iva = ...
		// importe = ...
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula 
	 * el importe
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		// desenlazar factura y averia
		// retornar la averia al estado FINALIZADA ( averia.markBackToFinished() )
		// volver a calcular el importe
	}

	/**
	 * Marks the invoice as ABONADA, but
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException if
	 * 	- Is already settled, or 
	 *  - the amounts paid with charges to payment means does not cover 
	 *  	the total of the invoice  
	 */
	public void settle() {
	}

}
