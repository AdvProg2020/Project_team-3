package View.Menus.MenuController.AdminMenu;

import Controller.Controller;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;

public class AdminMenuController {
    public ImageView adminImage;
   @FXML private Label personalInfo;


   public void initialize(){
         String path=UserController.getInstance().userImagePath(UserController.getInstance().getCurrentOnlineUserUsername());
         File file=new File(path);
         try {
            adminImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
         } catch (MalformedURLException e) {
            e.printStackTrace();
         }
      personalInfoUpdate();
   }

   public void personalInfoUpdate(){
      String message=UserController.getInstance().viewPersonalInfo(UserController.getInstance().getCurrentOnlineUser().getUsername());
      personalInfo.setText(message);
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
      SceneSwitcher.getInstance().back();
   }

   public void logout(ActionEvent actionEvent) {
      UserController.getInstance().logout();
      SceneSwitcher.getInstance().clearRecentScene();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   public void addCategory(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("AddCategory");
   }

   public void createAdminProfile(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminRegister");
   }

   public void editPersonalInfo(MouseEvent mouseEvent) {
   SceneSwitcher.getInstance().setSceneTo("AdminEditPersonalInfo");
   }

   public void manageCommercials(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageCommercials");
   }
}
