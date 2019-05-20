package uo.ri.ui.admin.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import alb.util.console.Printer;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class ListAllMechanicsAction implements Action {
	@Override
	public void execute() {
		try {
			Printer.print("\nListado de mecánicos\n");

			List<MechanicDTO> mechanics = ServicesFactory.getMechanicService().listAllMechanics();

			mechanics.forEach((mechanic) -> {
				Printer.print("ID: " + mechanic.id + " DNI: " + mechanic.dni + " Nombre: " + mechanic.name
						+ " apellido: " + mechanic.surname);
			});
		} catch (BusinessException e) {
			Console.println(e.getMessage());
		}
	}
}
