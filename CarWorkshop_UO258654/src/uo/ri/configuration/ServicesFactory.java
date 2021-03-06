package uo.ri.configuration;

import uo.ri.bussiness.serviceLayer.ContractCategoryService;
import uo.ri.bussiness.serviceLayer.ContractCrudService;
import uo.ri.bussiness.serviceLayer.ContractTypeCrudService;
import uo.ri.bussiness.serviceLayer.IInvoiceService;
import uo.ri.bussiness.serviceLayer.IMechanicService;
import uo.ri.bussiness.serviceLayer.PayrollService;
import uo.ri.bussiness.serviceLayer.implementation.COntractCrudServiceImpl;
import uo.ri.bussiness.serviceLayer.implementation.ContractCategoryServiceImpl;
import uo.ri.bussiness.serviceLayer.implementation.InvoiceService;
import uo.ri.bussiness.serviceLayer.implementation.MechanicService;
import uo.ri.bussiness.serviceLayer.implementation.PayrollServiceImpl;

public class ServicesFactory {
	public static IMechanicService getMechanicService() {
		return new MechanicService();
	}
	
	public static IInvoiceService getInvoiceService() {
		return new InvoiceService();
	}
	public static ContractTypeCrudService getContractTypeCrudService() {
		return new uo.ri.bussiness.serviceLayer.implementation.ContractTypeCrudService();
	}
	
	public static PayrollService getPayrollService() {
		return new PayrollServiceImpl();
	}
	
	public static ContractCategoryService getCategoryService() {
		return new ContractCategoryServiceImpl();
	}
	
	public static ContractCrudService getContractCrudService() {
		return new COntractCrudServiceImpl();
	}
}
