package Project.Client.Menus.MenuController;

import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ViewRequestUser {
   @FXML ListView request;
   private static String username;
   @FXML private AnchorPane pane;
   public static void setUsername(String username) {
      ViewRequestUser.username = username;
   }

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("second.wav");
      request.getItems().addAll(MakeRequest.makeGetAllUserRequestInfo().split("\n"));
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().back();
   }
}
