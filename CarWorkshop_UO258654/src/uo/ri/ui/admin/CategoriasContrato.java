package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.category.AddCategory;
import uo.ri.ui.admin.action.category.DeleteCategory;
import uo.ri.ui.admin.action.category.ListAllMechanicsByCategory;
import uo.ri.ui.admin.action.category.UpdateCategory;

public class CategoriasContrato extends BaseMenu {
	public CategoriasContrato() {
		menuOptions = new Object[][] { { "Administrador > Gestión de categorías", null },

				{ "Añadir categoría", AddCategory.class },
				{ "Modificar datos de la categoría", UpdateCategory.class },
				{ "Eliminar categoría", DeleteCategory.class },
				{ "Listar mecánicos por categoría", ListAllMechanicsByCategory.class } };
	}
}
