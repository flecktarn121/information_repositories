package uo.ri.ui.admin.action.contract;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.ServicesFactory;

public class FinishContract implements Action {

	@Override
	public void execute() throws Exception {
		// Pedir datos
		ContractDto c = new ContractDto();
		c.id = Console.readLong("Id de contrato");
		c.endDate = askForDate("Fecha de fin (dd/mm/aaaa)");

		ServicesFactory.getContractCrudService().finishContract(c.id, c.endDate);

		// Mostrar resultado
		Console.println("Contrato finiqitado.");
	}

	private Date askForDate(String msg) {
		while (true) {
			try {
				String asString = Console.readString(msg);
				return Dates.fromString(asString);
			} catch (NumberFormatException nfe) {
				Console.println("--> Fecha inválida");
			}
		}
	}

}
