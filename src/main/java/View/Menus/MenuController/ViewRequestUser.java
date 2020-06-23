package View.Menus.MenuController;

import Controller.UserController;
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
      String requestString=UserController.getInstance().getUserByUsername(username).getAllRequests().toString().replace(",","\n");
      request.getItems().addAll(requestString);
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().back();
   }
}
