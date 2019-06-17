package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.contract.AddContract;
import uo.ri.ui.admin.action.contract.DeleteContract;
import uo.ri.ui.admin.action.contract.FinishContract;
import uo.ri.ui.admin.action.contract.ListContracts;
import uo.ri.ui.admin.action.contract.UpdateContract;


public class Contratos extends BaseMenu {
	public Contratos() {
		menuOptions = new Object[][] { { "Administrador > Gestión de contratos", null },

				{ "Añadir contrato", AddContract.class },
				{ "Modificar datos del contrato", UpdateContract.class },
				{ "Eliminar contrato", DeleteContract.class },
				{ "Finiquitar contrato", FinishContract.class },
				{ "Listar contratos por mecánico", ListContracts.class } };
	}
}
