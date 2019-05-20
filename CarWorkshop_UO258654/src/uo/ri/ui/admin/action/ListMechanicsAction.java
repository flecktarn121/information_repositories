package uo.ri.ui.admin.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class ListMechanicsAction implements Action {

	@Override
	public void execute() {
		try {
			Printer.print("\nListado de mecánicos\n");

			List<MechanicDTO> mechanics = ServicesFactory.getMechanicService().listMechanics();

			mechanics.forEach((mechanic) -> {
				Printer.print("Nombre: " + mechanic.name + " apellido: " + mechanic.surname);
			});
		} catch (BusinessException e) {
			Console.println(e.getMessage());
		}
	}
}
