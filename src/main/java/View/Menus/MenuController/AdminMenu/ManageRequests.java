package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import Controller.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ManageRequests {
   @FXML
   private ListView listView;

   @FXML
   public void initialize() {
      update();
   }

   public void update() {
      listView.getItems().clear();
      int counter=0;
      for (Object requests : Database.getInstance().printFolderContent("Requests")) {
         listView.getItems().add(requests);
         counter++;
      }
      if(counter==0)
         listView.getItems().add("there are no request right now");
   }

   public void back(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
