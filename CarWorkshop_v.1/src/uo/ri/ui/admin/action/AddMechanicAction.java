package uo.ri.ui.admin.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.mechanic.AddMechanic;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		String dni = Console.readString("Dni"); 
		String nombre = Console.readString("Nombre"); 
		String apellidos = Console.readString("Apellidos");
		
		// Procesar
		new AddMechanic().execute(dni, nombre, apellidos);
		
		// Mostrar resultado
		Console.println("Nuevo mecánico añadido");
	}

}
