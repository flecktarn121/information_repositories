package uo.ri.ui.admin.action.contract;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.ServicesFactory;

public class UpdateContract implements Action {

	@Override
	public void execute() throws Exception {
		// Pedir datos
		ContractDto c = new ContractDto();
		c.id = Console.readLong("Id de contrato");
		c.endDate = askOptionalForDate("Fecha de fin (dd/mm/aaaa)");
		c.yearBaseSalary = Console.readLong("Salario base anual");

		// Procesar
		ServicesFactory.getContractCrudService().updateContract(c);

		// Mostrar resultado
		Console.println("Contrato actualizado.");
	}

	private Date askOptionalForDate(String msg) {
		while (true) {
			try {
				Console.print(msg + "[optional]: ");
				String asString = Console.readString();

				return ("".equals(asString)) ? null : Dates.fromString(asString);
			} catch (NumberFormatException nfe) {
				Console.println("--> Fecha inválida");
			}
		}
	}
}
