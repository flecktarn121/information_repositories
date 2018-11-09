package uo.ri.business;

public interface ServiceFactory {

	// Admin services
	MechanicCrudService forMechanicCrudService();

	// Cash use cases
	InvoiceService forInvoice();
	
	// Foreman use cases
	VehicleReceptionService forVehicleReception();


	// Mechanic services
	CloseBreakdownService forClosingBreakdown();

}
