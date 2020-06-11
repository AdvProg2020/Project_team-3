package View.Menus.MenuController.AdminMenu;

import Controller.SceneSwitcher;
import Controller.UserController;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class ManageUserIn {
   private static String username;

   public static void setUsername(String name) {
      username = name;
   }

   public void deleteUser(MouseEvent mouseEvent) {
      String message=UserController.getInstance().deleteUser(username);
      System.out.println(message+"salam");
      if(message.startsWith("Error")) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText(message);
         alert.show();
         return;
      }
      if(message.startsWith("Successful")){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.show();
         back();
      }

   }

   public void back(){
      SceneSwitcher.getInstance().setSceneTo("ManageUsers");
   }
}
