package uo.ri.ui.admin.action.contract;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.ServicesFactory;

public class DeleteContract implements Action {

	@Override
	public void execute() throws Exception {
		// Pedir datos
				
				Long id = Console.readLong("Id de contrato");

				ServicesFactory.getContractCrudService().deleteContract(id);

				// Mostrar resultado
				Console.println("Contrato eliminado.");

	}

}
