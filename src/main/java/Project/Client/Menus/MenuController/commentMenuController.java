package Project.Client.Menus.MenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Users.*;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class commentMenuController {

   public TextField usernameTexField;
   public TextArea commentTextArea;
   public ImageView userImage;
   private static String itemID;
   private static String fatherCommentId=null;
   @FXML
   private AnchorPane pane;
   public static void setItemID(String id){
      itemID=id;
   }
   public static void setFatherCommentId(String id){fatherCommentId=id;}

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("second.wav");
      User user=MakeRequest.makeGetUserRequest();
      userImage.setImage(Client.getInstance().getImageFromServer(user.getUsername(),"user"));
      usernameTexField.setText(user.getUsername());
      usernameTexField.setEditable(false);
   }

   public void sendComment(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      if(commentTextArea.getText().equals("")){
         MusicManager.getInstance().playSound("error");
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setContentText("fill the comment Text area!");
         alert.show();
         return;
      }
      System.out.println(MakeRequest.makeCommentRequest(commentTextArea.getText(),itemID,fatherCommentId));
      MusicManager.getInstance().playSound("notify");
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText("your comment was sent to our admin!");
      alert.show();
      SceneSwitcher.getInstance().closeSecondStage();
   }


}
