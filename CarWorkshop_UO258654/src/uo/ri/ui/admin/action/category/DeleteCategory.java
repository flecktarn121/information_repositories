package uo.ri.ui.admin.action.category;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.configuration.ServicesFactory;

public class DeleteCategory implements Action {

	@Override
	public void execute() throws Exception {
		try {
			// Pedir datos
			String nombre = Console.readString("Nombre");
			// Procesar
			ServicesFactory.getCategoryService().deleteContractCategory(nombre);

			// Mostrar resultado
			Printer.print("Se ha borrado la categoría.");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}

	}

}
