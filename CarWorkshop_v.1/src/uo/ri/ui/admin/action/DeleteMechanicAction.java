package uo.ri.ui.admin.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.mechanic.DeleteMechanic;

public class DeleteMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		new DeleteMechanic(idMecanico).execute();
		
		Console.println("Se ha eliminado el mecánico");
	}

}
