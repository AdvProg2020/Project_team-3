package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
      for (Object requests : Database.getInstance().printFolderContent("Requests")) {
         listView.getItems().add(requests);
      }
      if(listView.getItems().isEmpty())
         listView.getItems().add("there are no request right now");
   }

   public void requestSelect(MouseEvent mouseEvent) {
      int index=listView.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String requestId=listView.getItems().get(index).toString().substring(4,9);
      listView.getSelectionModel().clearSelection();
      ManageRequestIn.setRequestId(requestId);
      SceneSwitcher.getInstance().setSceneTo("ManageRequestIn",392,173);
   }

   public void back(ActionEvent actionEvent)  {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
