package uo.ri.ui.contract.type;

import alb.util.menu.BaseMenu;
import uo.ri.bussiness.contract.crud.ListMechanicsByContractType;
import uo.ri.ui.contract.type.action.AddContractTypeAction;
import uo.ri.ui.contract.type.action.DeleteContractTypeAction;
import uo.ri.ui.contract.type.action.UpdateContractTypeAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Tipos de Contrato", null },
			{ "Listar mecanicos por tipo de contrato", ListMechanicsByContractType.class }, 
			{ "Añadir tipo de contrato", AddContractTypeAction.class },
			{ "Eliminar tipo de contrato", 	DeleteContractTypeAction.class },
			{ "Modificar tipo de Contrato", UpdateContractTypeAction.class },
		};
	}

	public static void main(String[] args) {
		try {
			new MainMenu().execute();
		} catch (Exception e) {
			System.err.println("Ha habido un fallo interno, contacte al servicio técnico.");
		}
	}

}
