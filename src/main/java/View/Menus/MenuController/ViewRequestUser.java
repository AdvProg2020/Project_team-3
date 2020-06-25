package View.Menus.MenuController;

import Controller.UserController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewRequestUser {
   @FXML ListView request;
   private static String username;

   public static void setUsername(String username) {
      ViewRequestUser.username = username;
   }

   public void initialize(){
      MusicManager.getInstance().setSongName("second.wav");
      request.getItems().addAll(UserController.getInstance().getUserByUsername(username).getAllRequests());
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().back();
   }
}
