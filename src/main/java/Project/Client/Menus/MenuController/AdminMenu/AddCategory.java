package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AddCategory {
   @FXML private  TextField categoryName;
   @FXML private  TextField attribute;
   @FXML private  ChoiceBox fatherCategoryChoice;
   @FXML private  ListView attributeList;
   private ArrayList<String> allAttribute=new ArrayList<>();
   @FXML private AnchorPane pane;
   @FXML public void initialize()
   {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }


   private void setFonts(){
      for (Node child : pane.getChildren()) {
         if(child instanceof javafx.scene.control.Label)
            ((javafx.scene.control.Label) child).setFont(javafx.scene.text.Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
         if(child instanceof TextField)
            ((TextField) child).setFont(javafx.scene.text.Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
         if(child instanceof javafx.scene.control.TextArea)
            ((TextArea) child).setFont(javafx.scene.text.Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
         if(child instanceof Text)
            ((Text) child).setFont(Font.loadFont("file:src/main/resources/fonts/Q.otf", 14));
      }
   }

   public void update(){
      ArrayList<String> allCategories = MakeRequest.makeGetAllCategoryName();
      fatherCategoryChoice.getItems().clear();
      for (String category : allCategories) {
         fatherCategoryChoice.getItems().add(category);
      }
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void add(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
     String text=attribute.getText();
     if((text.isEmpty())||(allAttribute.contains(text))) return;
     allAttribute.add(text);
     attributeList.getItems().add(text);
     attribute.clear();
   }

   public void remove(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=attributeList.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String text=attributeList.getItems().get(index).toString();
      attributeList.getItems().remove(text);
      allAttribute.remove(text);
      attributeList.getSelectionModel().clearSelection();
   }

   public void updateCategoryName(KeyEvent keyEvent) {
      String text=categoryName.getText();
      if(text.contains(" ")){
         categoryName.setStyle("-fx-text-fill: red;");
         return;
      }
      categoryName.setStyle("-fx-text-fill: green;");
   }

   public void reset(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      categoryName.clear();
      attribute.clear();
      fatherCategoryChoice.getItems().clear();
      attributeList.getItems().clear();
      allAttribute.clear();
      update();
   }

   private void showAlertBox(String message,String type){
      if(type.equalsIgnoreCase("error")){
         MusicManager.getInstance().playSound("error");
      }else {
         MusicManager.getInstance().playSound("notify");
      }
      SceneSwitcher.getInstance().sendAlert(type.equalsIgnoreCase("error"),message);

   }

   public void create(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(categoryName.getStyle().contains("red")){
         showAlertBox("category name should not have white space","ERROR");
         return;
      }
      try {
         String name = categoryName.getText();
         String father = fatherCategoryChoice.getValue().toString();
         if (name.isEmpty()) {
            showAlertBox("please enter all fields", "ERROR");
            return;
         }
         String message= MakeRequest.makeAddCategoryRequest(name,father,allAttribute);
         if(message.contains("Error:")){
            showAlertBox(message,"ERROR");
            return;
         }
         if(message.startsWith("Successful:")){
            showAlertBox(message,"INFORMATION");
            reset(null);
            return;
         }
      }catch (Exception e){
         showAlertBox("please enter all fields", "ERROR");
      }

   }
}
