package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ManageProducts {
   @FXML
   private ListView listView;

   @FXML
   public void initialize() {
      update();
   }

   public void update() {
      listView.getItems().clear();
      for (Object requests : Database.getInstance().printFolderContent("Items")) {
         listView.getItems().add(requests);
      }
      if(listView.getItems().isEmpty())
         listView.getItems().add("there are no products right now");
   }

   public void back(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}



