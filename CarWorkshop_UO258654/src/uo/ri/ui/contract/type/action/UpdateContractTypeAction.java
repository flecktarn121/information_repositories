package uo.ri.ui.contract.type.action;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.serviceLayer.implementation.ContractTypeCrudService;

public class UpdateContractTypeAction implements Action {
	@Override
	public void execute() {
		try {
			String name = Console.readString("Nombre");
			int days = Console.readInt("Dias de compensacion por año");
			ContractTypeDto dto = new ContractTypeDto();
			dto.name = name;
			dto.compensationDays = days;
			new ContractTypeCrudService().updateContractType(dto);

			Printer.print("Tipo de contrato actualizado");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}
}
