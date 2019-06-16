package uo.ri.ui.admin.action.mechanic;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.MechanicDTO;
import uo.ri.configuration.ServicesFactory;

public class DeleteMechanicAction implements Action {

	@Override
	public void execute() {
		try {
			Long idMecanico = Console.readLong("Id de mecánico");
			MechanicDTO dto = new MechanicDTO();
			dto.id = idMecanico;
			ServicesFactory.getMechanicService().deleteMechanic(dto);

			Printer.print("Se ha eliminado el mecánico");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
