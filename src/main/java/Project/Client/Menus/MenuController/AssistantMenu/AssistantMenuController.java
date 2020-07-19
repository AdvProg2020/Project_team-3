package Project.Client.Menus.MenuController.AssistantMenu;


import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Users.Admin;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Users.Assistant;
import Server.Controller.UserController;
import Project.Client.CLI.View;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class AssistantMenuController {
   @FXML public ImageView userImage;
   @FXML private Label personalInfo;
   @FXML AnchorPane pane;

   public void initialize(){
      MakeRequest.makeUpdateDateAndTimeRequest();
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      Image image= Client.getInstance().getImageFromServer(MakeRequest.makeGetUserRequest().getUsername(),"user");
      userImage.setImage(image);
      personalInfoUpdate();
   }


   public void personalInfoUpdate(){
      Assistant assistant=(Assistant) MakeRequest.makeGetUserRequest();
      personalInfo.setText(assistant.getPersonalInfo());
   }


   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().back();
   }

   public void logout(ActionEvent actionEvent) {
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }



   public void viewEditPersonalInfo(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminEditPersonalInfo");
   }
}
