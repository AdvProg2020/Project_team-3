package View.Menus.MenuController.AdminMenu;

import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;

public class ManageCategories {
   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
