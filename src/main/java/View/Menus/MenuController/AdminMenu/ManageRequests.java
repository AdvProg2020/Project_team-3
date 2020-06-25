package View.Menus.MenuController.AdminMenu;

import Controller.CommercialController;
import Controller.Database;
import Controller.RequestController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageRequests {
   @FXML private ListView listView;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      listView.getItems().clear();
      for (Object requests : Database.getInstance().printFolderContent("Requests")) {
         listView.getItems().add(requests);
      }
      for (String id : CommercialController.getInstance().getCommercialItemRequest()) {
         listView.getItems().add("commercial request for item "+id);
      }
      if(listView.getItems().isEmpty())
         listView.getItems().add("there are no request right now");

   }

   public void requestSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;

      if(listView.getItems().get(index).toString().contains("commercial request for item")){
         String itemId=listView.getItems().get(index).toString().substring(28,33);
         ManageCommercialIn.setRequestId(itemId);
         SceneSwitcher.getInstance().setSceneAndWait("ManageCommercialIn",392,173);
      }

      String requestId=listView.getItems().get(index).toString().substring(4,9);
      listView.getSelectionModel().clearSelection();
      if(RequestController.getInstance().isThereRequestWithId(requestId)) {
         ManageRequestIn.setRequestId(requestId);
         SceneSwitcher.getInstance().setSceneAndWait("ManageRequestIn", 392, 173);
      }
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
