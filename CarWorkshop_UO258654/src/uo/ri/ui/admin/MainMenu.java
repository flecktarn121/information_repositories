package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { { "Administrador", null }, { "Gestión de mecánicos", MecanicosMenu.class },
				{ "Gestión de repuestos", RepuestosMenu.class },
				{ "Gestión de tipos de vehículo", TiposVehiculoMenu.class },
				{ "Gestion de tipos de contrato", TiposContrato.class },
				{"Gestión de categorías de contrato", CategoriasContrato.class},
				{ "Gestión de nóminas", NominasMenu.class }};
	}

	public static void main(String[] args) {
		try {
			new MainMenu().execute();
		} catch (Exception e) {
			System.err.println("Ha habido un fallo interno, contacte al servicio técnico.");
			e.printStackTrace(System.out);
			System.out.println(e.getMessage());
		}
	}

}
