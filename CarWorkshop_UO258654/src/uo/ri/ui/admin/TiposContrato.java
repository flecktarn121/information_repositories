package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.contract.type.action.AddContractTypeAction;
import uo.ri.ui.contract.type.action.DeleteContractTypeAction;
import uo.ri.ui.contract.type.action.ListMechanicsByContractTypeAction;
import uo.ri.ui.contract.type.action.UpdateContractTypeAction;

public class TiposContrato extends BaseMenu {

	public TiposContrato() {
		menuOptions = new Object[][] { 
			{ "Tipos de Contrato", null },
			{ "Listar mecanicos por tipo de contrato", 		ListMechanicsByContractTypeAction.class }, 
			{ "Añadir tipo de contrato", 		AddContractTypeAction.class },
			{ "Eliminar tipo de contrato", 	DeleteContractTypeAction.class },
			{ "Modificar tipo de Contrato", 				UpdateContractTypeAction.class },
		};
	}

}
