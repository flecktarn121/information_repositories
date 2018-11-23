package uo.ri.business.impl;

import uo.ri.business.CloseBreakdownService;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.ContractCrudService;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.InvoiceService;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.PayrollService;
import uo.ri.business.ServiceFactory;
import uo.ri.business.VehicleReceptionService;
import uo.ri.business.impl.contract.ContractCrudServiceImpl;
import uo.ri.business.impl.contract.category.ContractCategoryCrudServiceImpl;
import uo.ri.business.impl.contract.type.ContractTypeCrudServiceImpl;
import uo.ri.business.impl.invoice.InvoiceServiceImpl;
import uo.ri.business.impl.mechanic.MechanicCrudServiceImpl;
import uo.ri.business.impl.payroll.PayrollServiceImpl;

public class BusinessFactory implements ServiceFactory {

	@Override
	public MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	@Override
	public InvoiceService forInvoice() {
		return new InvoiceServiceImpl();
	}

	@Override
	public VehicleReceptionService forVehicleReception() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public CloseBreakdownService forClosingBreakdown() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ContractCategoryCrudService forContractCategoryCrud() {
		return new ContractCategoryCrudServiceImpl();
	}

	@Override
	public ContractCrudService forContractCrud() {
		return new ContractCrudServiceImpl();
	}

	@Override
	public ContractTypeCrudService forContractTypeCrud() {
		return new ContractTypeCrudServiceImpl();
	}

	@Override
	public PayrollService forPayroll() {
		return new PayrollServiceImpl();
	}

}
