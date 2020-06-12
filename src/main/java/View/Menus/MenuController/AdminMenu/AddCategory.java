package View.Menus.MenuController.AdminMenu;

import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCategory {
   @FXML private  TextField categoryName;

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
