package uo.ri.ui.admin.action.contract;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.ServicesFactory;

public class ListContracts implements Action {

	@Override
	public void execute() throws Exception {
		// Pedir datos

		Long id = Console.readLong("Id del mecánico");
		Console.println("Contratos del trabajador: \n");
		// Procesar
		List<ContractDto> contracts = ServicesFactory.getContractCrudService().findContractsByMechanicId(id);
		for (ContractDto dto : contracts) {
			Console.print(dto.id + " / " + dto.yearBaseSalary + " / " + dto.status);
			int payrolls = ServicesFactory.getContractCrudService().findPayrollsForContract(dto.id).size();
			Console.print("\tnóminas: " + payrolls);
			Console.print("\n");
		}

		// Mostrar resultado

	}

}
