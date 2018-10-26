package uo.ri.model;

public abstract class MedioPago {

	protected double acumulado = 0.0;
	
	public void pagar(double importe) {
		this.acumulado += importe;
	}

}
