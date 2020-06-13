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

   public void createAdminProfile(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminRegister");
   }
}
