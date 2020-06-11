package View.Menus.MenuController.AdminMenu;

import Controller.SceneSwitcher;
import Controller.UserController;
import javafx.scene.input.MouseEvent;

public class ManageUserIn {
   private static String username;

   public static void setUsername(String name) {
      username = name;
   }

   public void deleteUser(MouseEvent mouseEvent) {
      String message=UserController.getInstance().deleteUser(username);
      System.out.println(message);
   }

   public void back(){
      SceneSwitcher.getInstance().setSceneTo("ManageUsers");
   }
}
