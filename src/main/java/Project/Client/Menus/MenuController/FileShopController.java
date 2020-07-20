package Project.Client.Menus.MenuController;


import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;

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
   }

   @FXML private void initialize(){
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   private void update(){
      allFiles.getItems().addAll(MakeRequest.getAllFiles());
      if(allFiles.getItems().isEmpty()) allFiles.getItems().add("there are no file right now");
   }

   public void fileSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=allFiles.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      String fileName=allFiles.getItems().get(index).toString();
      if(MakeRequest.isThereFileWithName(fileName)) {
        FileMenuController.setFileName(fileName);
        SceneSwitcher.getInstance().setSceneTo("FileMenu");
      }
      allFiles.getSelectionModel().clearSelection();
   }
}
