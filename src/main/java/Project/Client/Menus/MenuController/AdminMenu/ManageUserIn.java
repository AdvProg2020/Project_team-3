package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;

import Project.Client.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageUserIn {
   private static String username;
   @FXML private AnchorPane pane;
   public static void setUsername(String name) {
      username = name;
   }

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");}

   public void deleteUser(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message=MakeRequest.makeDeleteUserRequest(username);
      if(message.startsWith("Error")) {
         SceneSwitcher.getInstance().sendAlert(true,message);
         return;
      }
      if(message.startsWith("Successful")){
         SceneSwitcher.getInstance().sendAlert(false,message);
         back(null);
      }

   }

   public void back(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("ManageUsers");
   }

   public void viewUser(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(MakeRequest.makeViewUserRequest(username));
      alert.show();
   }
}
