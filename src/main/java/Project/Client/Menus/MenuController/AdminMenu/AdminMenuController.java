package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Users.Admin;
import Server.Controller.Controller;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Server.Controller.UserController;
import Project.Client.CLI.View;


import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class AdminMenuController {
    public ImageView adminImage;
   @FXML private Label personalInfo;
   @FXML AnchorPane pane;

   public void initialize(){
       Controller.getInstance().updateDateAndTime();
         View.setFonts(pane);
         MusicManager.getInstance().setSongName("first.wav");
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
      Admin admin=(Admin) MakeRequest.makeGetUserRequest();
      personalInfo.setText(admin.getPersonalInfo());
   }
   public void manageUsers(ActionEvent actionEvent) {

       SceneSwitcher.getInstance().setSceneTo("ManageUsers",600,400);
   }

   public void manageDiscountCodes(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageDiscountCodes",592,400);
   }

   public void manageRequests(ActionEvent actionEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageRequests",600,400);
   }

   public void manageProducts(ActionEvent actionEvent) {
     SceneSwitcher.getInstance().setSceneTo("ManageProducts",898,537);
   }

   public void addDiscountCode(ActionEvent actionEvent){
    SceneSwitcher.getInstance().setSceneTo("AddDiscountCode",707,445);
   }

   public void manageCategories(ActionEvent actionEvent) {
    SceneSwitcher.getInstance().setSceneTo("ManageCategories",600,400);
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
    SceneSwitcher.getInstance().setSceneTo("AddCategory",600,400);
   }

   public void createAdminProfile(ActionEvent actionEvent){
       SceneSwitcher.getInstance().saveScene("AdminMenu");
       SceneSwitcher.getInstance().setSceneTo("AdminRegister");
   }

   public void editPersonalInfo(MouseEvent mouseEvent) {
   SceneSwitcher.getInstance().setSceneTo("AdminEditPersonalInfo");
   }

   public void manageCommercials(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageCommercials",705,483);
   }

}
