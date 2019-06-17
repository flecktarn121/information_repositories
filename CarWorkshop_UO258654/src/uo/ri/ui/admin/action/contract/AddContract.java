package uo.ri.ui.admin.action.contract;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.configuration.ServicesFactory;

public class AddContract implements Action {

	@Override
	public void execute() throws BusinessException {

		// Pedir datos
		ContractDto c = new ContractDto();
		c.mechanicId = Console.readLong("Id de mecanico");

		c.typeName = Console.readString("Nombre de tipo de contrato");

		c.categoryName = Console.readString("Nombre de categoria");

		c.startDate = askForDate("Fecha de inicio (dd/mm/aaaa)");
		c.endDate = askOptionalForDate("Fecha de fin (dd/mm/aaaa)");
		c.yearBaseSalary = Console.readLong("Salario base anual");
		c.status = "ACTIVE";
		// Procesar
		ServicesFactory.getContractCrudService().addContract(c);
		// Mostrar resultado
		Console.println("Nuevo contrato añadido");
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
