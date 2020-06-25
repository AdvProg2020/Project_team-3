package View.Menus.MenuController;

import Controller.*;
import Model.Users.User;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import View.Menus.View;
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
      User user= Controller.getInstance().getCurrentOnlineUser();
      String path=UserController.getInstance().userImagePath(user.getUsername());
      File file=new File(path);
      try {
         userImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
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
      ItemAndCategoryController.getInstance().comment(commentTextArea.getText(),itemID,fatherCommentId);
      MusicManager.getInstance().playSound("notify");
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText("your comment was sent to our admin!");
      alert.show();
      SceneSwitcher.getInstance().closeSecondStage();
   }


}
