package uo.ri.ui.admin.action.mechanic;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() {
		try {
			// Pedir datos
			Long id = Console.readLong("Id del mecánico");
			String nombre = Console.readString("Nombre");
			String apellidos = Console.readString("Apellidos");
			MechanicDTO dto = new MechanicDTO();
			dto.id = id;
			dto.name = nombre;
			dto.surname = apellidos;
			// Procesar
			ServicesFactory.getMechanicService().updateMechanic(dto);

			// Mostrar resultado
			Printer.print("Mecánico actualizado");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
