package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;


import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
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
      String requestList=MakeRequest.makeGetAllRequestsRequest();
      if(requestList.isEmpty()==false) listView.getItems().addAll(requestList.split("\n"));
     // for (String id : CommercialController.getInstance().getCommercialItemRequest()) {
      //   listView.getItems().add("commercial request for item "+id);
     // }
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
      if(MakeRequest.makeIsThereRequestWithId(requestId)) {
         ManageRequestIn.setRequestId(requestId);
         SceneSwitcher.getInstance().setSceneAndWait("ManageRequestIn", 392, 173);
      }
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void refresh(ActionEvent actionEvent) {
      update();
   }

}
