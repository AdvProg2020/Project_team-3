package View.Menus.MenuController.AdminMenu;

import Controller.Controller;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;

public class AdminMenuController {
   public ImageView adminImage;


   public void initialize(){
      if(Controller.getInstance().isLogin()==true && Controller.getInstance().getCurrentOnlineUser() instanceof Admin){
         User onlineUser=Controller.getInstance().getCurrentOnlineUser();
         String path=UserController.getInstance().userImagePath(onlineUser.getUsername());
         File file=new File(path);
         try {
            adminImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
         } catch (MalformedURLException e) {
            e.printStackTrace();
         }
      }
   }

   public void manageUsers(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageUsers");
   }

   public void manageDiscountCodes(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageDiscountCodes");
   }

   public void manageRequests(ActionEvent actionEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   public void manageProducts(ActionEvent actionEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageProducts");
   }

   public void addDiscountCode(ActionEvent actionEvent){
    SceneSwitcher.getInstance().setSceneTo("AddDiscountCode");
   }

   public void manageCategories(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageCategories");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void logout(ActionEvent actionEvent) {
      UserController.getInstance().logout();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void addCategory(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("AddCategory");
   }

   public void createAdminProfile(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminRegister");
   }

   public void editPersonalInfo(MouseEvent mouseEvent) {

   }
}
