package View.Menus.MenuController.AdminMenu;

import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class AdminMenuController {
   public void manageUsers(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageUsers");
   }

   public void manageDiscountCodes(MouseEvent mouseEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageDiscountCodes");
   }

   public void manageRequests(MouseEvent mouseEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   public void manageProducts(MouseEvent mouseEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageProducts");
   }

   public void addDiscountCode(MouseEvent mouseEvent){
    SceneSwitcher.getInstance().setSceneTo("AddDiscountCode");
   }

   public void manageCategories(MouseEvent mouseEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageCategories");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void logout(ActionEvent actionEvent) {
      UserController.getInstance().logout();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void addCategory(MouseEvent mouseEvent) {
    SceneSwitcher.getInstance().setSceneTo("AddCategory");
   }
}
