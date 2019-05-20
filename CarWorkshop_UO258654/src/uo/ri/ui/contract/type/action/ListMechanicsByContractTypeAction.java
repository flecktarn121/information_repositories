package uo.ri.ui.contract.type.action;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.serviceLayer.implementation.ContractTypeCrudService;

public class ListMechanicsByContractTypeAction implements Action {
	@Override
	public void execute() {
		try {
			String name = Console.readString("Nombre");
			ContractTypeDto dto = new ContractTypeDto();
			dto.name = name;
			new ContractTypeCrudService().listMechanicsByContractType(dto);
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}
}
