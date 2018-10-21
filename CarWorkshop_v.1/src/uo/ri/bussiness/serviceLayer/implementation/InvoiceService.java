package uo.ri.bussiness.serviceLayer.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import uo.ri.bussiness.dto.InvoiceDTO;
import uo.ri.bussiness.serviceLayer.IInvoiceService;
import uo.ri.configuration.PersistenceFactory;

public class InvoiceService implements IInvoiceService {
	@Override
	public void facturarResparacion() {
		List<Long> idsAveria = new ArrayList<Long>();

		// pedir las averias a incluir en la factura
		do {
			Long id = Console.readLong("ID de averia");
			idsAveria.add(id);
		} while (masAverias());

		InvoiceDTO dto = new InvoiceDTO();
		PersistenceFactory.getInvoiceGateway().facturarResparacion(idsAveria, dto);
		
		mostrarFactura(dto.numeroFactura, dto.fechaFactura, dto.totalConIva, dto.iva, dto.totalFactura);

	}

	private void mostrarFactura(long numeroFactura, Date fechaFactura, double totalFactura, double iva,
			double totalConIva) {

		Console.printf("Factura nº: %d\n", numeroFactura);
		Console.printf("\tFecha: %1$td/%1$tm/%1$tY\n", fechaFactura);
		Console.printf("\tTotal: %.2f €\n", totalFactura);
		Console.printf("\tIva: %.1f %% \n", iva);
		Console.printf("\tTotal con IVA: %.2f €\n", totalConIva);
	}

	private boolean masAverias() {
		return Console.readString("¿Añadir más averias? (s/n) ").equalsIgnoreCase("s");
	}

}
