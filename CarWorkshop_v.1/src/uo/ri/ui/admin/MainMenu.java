package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { { "Administrador", null }, { "Gestión de mecánicos", MecanicosMenu.class },
				{ "Gestión de repuestos", RepuestosMenu.class },
				{ "Gestión de tipos de vehículo", TiposVehiculoMenu.class }, };
	}

	public static void main(String[] args) {
		try {
			new MainMenu().execute();
		} catch (Exception e) {
			System.err.println("Ha habido un fallo interno, contacte al servicio técnico.");
		}
	}

}