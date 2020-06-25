package View.Menus.MenuController;

import Controller.UserController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import View.Menus.View;
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
      request.getItems().addAll(UserController.getInstance().getUserByUsername(username).getAllRequests());
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().back();
   }
}
