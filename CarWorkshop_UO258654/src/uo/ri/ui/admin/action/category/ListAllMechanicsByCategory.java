package uo.ri.ui.admin.action.category;

import java.util.List;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class ListAllMechanicsByCategory implements Action {

	@Override
	public void execute() throws Exception {
		try {
			// Pedir datos
			String nombre = Console.readString("Nombre");
			// Procesar
			List<MechanicDTO> mechanics = ServicesFactory.getCategoryService()
					.findAllMechanicsByContractCategorie(nombre);
			Printer.print("Mecánicos dentro de la categoría " + nombre + ":");
			mechanics.forEach((mechanic) -> {
				Printer.print("ID: " + mechanic.id + " DNI: " + mechanic.dni + " Nombre: " + mechanic.name
						+ " apellido: " + mechanic.surname);
			});
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
