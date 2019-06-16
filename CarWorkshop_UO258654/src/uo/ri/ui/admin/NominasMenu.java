package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.payroll.CheckPayrollAction;
import uo.ri.ui.admin.action.payroll.CheckPayrollByMechanic;
import uo.ri.ui.admin.action.payroll.DeleteLastMonthPayrolls;
import uo.ri.ui.admin.action.payroll.DeleteOnePayrollAction;
import uo.ri.ui.admin.action.payroll.GeneratePayrollsAction;
import uo.ri.ui.admin.action.payroll.ListAllPayrollsAction;

public class NominasMenu extends BaseMenu {

	public NominasMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de nóminas", null},
			
			{ "Generar nóminas", 				GeneratePayrollsAction.class }, 
			{ "Consultar nomina", 	CheckPayrollAction.class }, 
			{ "Consultar nomina por empleado", 	CheckPayrollByMechanic.class }, 
			{ "Eliminar nomina", 				DeleteOnePayrollAction.class },
			{ "Eliminar nominas del último mes",DeleteLastMonthPayrolls.class },
			{ "Listar todas las nominas", 		ListAllPayrollsAction.class },
		};
	}

}
