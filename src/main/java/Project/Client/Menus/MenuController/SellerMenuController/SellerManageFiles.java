package Project.Client.Menus.MenuController.SellerMenuController;


import Project.Client.Client;
import Project.Client.MakeRequest;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.SortAndFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class SellerManageFiles {
   @FXML ListView listView;


   @FXML public void initialize() {
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      listView.getItems().clear();
      listView.getItems().addAll(MakeRequest.makeGetAllSellerFiles());
      SortAndFilter.getInstance().disableFilterSellerName();
      if(listView.getItems().isEmpty()) {
         listView.getItems().add("there are no files right now");
         return;
      }
   }


   public void fileSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;

      String  fileId=listView.getItems().get(index).toString().substring(4,9);
      listView.getSelectionModel().clearSelection();
      if(MakeRequest.isThereProductWithId(fileId)) {
         SellerManageFileIn.setFileName(fileId);
         SceneSwitcher.getInstance().setSceneTo("ManageFileInSeller");
      }

   }

   public void logout(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().back();
   }
}

