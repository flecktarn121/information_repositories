package uo.ri.ui.contract.type.action;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.configuration.ServicesFactory;

public class AddContractTypeAction implements Action {

	@Override
	public void execute() {
		try {
			String name = Console.readString("Nombre");
			int numDays = Console.readInt("Dias de compensacion por año");
			ContractTypeDto dto = new ContractTypeDto();
			dto.name = name;
			dto.compensationDays = numDays;
			ServicesFactory.getContractTypeCrudService().addContractType(dto);

			Printer.print("Nuevo tipo de contrato añadido");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
