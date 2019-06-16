package uo.ri.ui.admin.action.mechanic;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class AddMechanicAction implements Action {

	@Override
	public void execute() {
		try {
			// Pedir datos
			String dni = Console.readString("Dni");
			String nombre = Console.readString("Nombre");
			String apellidos = Console.readString("Apellidos");
			MechanicDTO dto = new MechanicDTO();
			dto.dni = dni;
			dto.name = nombre;
			dto.surname = apellidos;
			// Procesar
			ServicesFactory.getMechanicService().addMechanic(dto);

			// Mostrar resultado
			Printer.print("Nuevo mecánico añadido");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
