package View.Menus.MenuController.AdminMenu;

import View.Menus.SceneSwitcher;
import javafx.scene.input.MouseEvent;

public class ManageProductsIn {
   public void exit(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageProducts");
   }
}
