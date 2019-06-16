package uo.ri.ui.admin.action.category;

import alb.util.console.Console;
import alb.util.console.Printer;
import alb.util.menu.Action;
import uo.ri.bussiness.BusinessException;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.configuration.ServicesFactory;

public class AddCategory implements Action {

	@Override
	public void execute() throws Exception {
		try {
			// Pedir datos
			String nombre = Console.readString("Nombre");
			double trienios = Console.readDouble("Importe de los trienios");
			double prodPlus = Console.readDouble("Plus de productividad");
			ContractCategoryDto dto = new ContractCategoryDto();
			dto.name = nombre;
			dto.trieniumSalary = trienios;
			dto.productivityPlus = prodPlus;

			// Procesar
			ServicesFactory.getCategoryService().addContractCategory(dto);

			// Mostrar resultado
			Printer.print("Nueva categoría añadida");
		} catch (BusinessException e) {
			Printer.printBusinessException(e);
		}
	}

}
