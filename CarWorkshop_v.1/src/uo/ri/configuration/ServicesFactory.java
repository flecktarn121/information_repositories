package uo.ri.configuration;

import uo.ri.bussiness.serviceLayer.IInvoiceService;
import uo.ri.bussiness.serviceLayer.IMechanicService;
import uo.ri.bussiness.serviceLayer.implementation.InvoiceService;
import uo.ri.bussiness.serviceLayer.implementation.MechanicService;

public class ServicesFactory {
	public static IMechanicService getMechanicService() {
		return new MechanicService();
	}
	
	public static IInvoiceService getInvoiceService() {
		return new InvoiceService();
	}
}
