package View.Menus.MenuController;

import Controller.*;
import Model.Users.User;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class commentMenuController {

   public TextField usernameTexField;
   public TextArea commentTextArea;
   public ImageView userImage;
   private static String itemID;
   private static String fatherCommentId=null;
   @FXML private AnchorPane anchorPane;

   public static void setItemID(String id){
      itemID=id;
   }
   public static void setFatherCommentId(String id){fatherCommentId=id;}

   public void initialize(){
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
      setFont();
   }

   private void setFont(){
      for(Node node:anchorPane.getChildren()){
         if(node instanceof Label){
            ((Label)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
         }
         if(node instanceof Text){
            ((Text)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
         }
         if(node instanceof TextArea){
            ((TextArea)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
         }
         if(node instanceof TextField){
            ((TextField)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
         }
      }
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
