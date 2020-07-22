package Project.Client.Menus.MenuController;


import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;

import Project.Client.Model.Category;
import Project.Client.Model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class FileShopController {
   @FXML ListView allFiles;

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().back();
   }

   public void logout(ActionEvent actionEvent) {
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
   }

   @FXML private void initialize(){
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   private void update(){
      allFiles.getItems().clear();
      Category category=MakeRequest.getCategory("File");

      for (String id : category.getAllItemsID()) {
         Item item=MakeRequest.getItem(id);
         if(item!=null) allFiles.getItems().add(item.showIdWithName());
      }
      if(allFiles.getItems().isEmpty()) {
         allFiles.getItems().add("there are no files right now");
         return;
      }
   }

   public void fileSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=allFiles.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      String fileName=allFiles.getItems().get(index).toString().substring(4,9);
      if(MakeRequest.isThereProductWithId(fileName)) {
        FileMenuController.setFileName(fileName);
        SceneSwitcher.getInstance().setSceneTo("FileMenu");
      }
      allFiles.getSelectionModel().clearSelection();
   }
}
