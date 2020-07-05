package Project.View.Menus.MenuController.AdminMenu;

import Project.Controller.ItemAndCategoryController;

import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EditCategory {
   @FXML TextField categoryNameField;
   @FXML TextField attributeField;
   @FXML ListView info;
   @FXML private AnchorPane pane;
   private static String categoryName;

   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update(){
      info.getItems().clear();
      String message=ItemAndCategoryController.getInstance().getCategoryInfo(categoryName);
      info.getItems().addAll(message.split(","));
   }

   public static void setCategoryName(String name) {
      categoryName = name;
   }

   public void changeName(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
    if(categoryName.equals("Project.Main")){
        showAlertBox("you cant edit Project.Main","ERROR");
        return;
     }
    if(categoryNameField.getStyle().toString().contains("red")){
       showAlertBox("category name should not have white space","ERROR");
       return;
    }
    String text=categoryNameField.getText();
    if(text.isEmpty()){
       return;
    }
    String message= ItemAndCategoryController.getInstance().renameCategory(categoryName,text);
    if(message.startsWith("Successful")){
       showAlertBox(message,"INFORMATION");
       setCategoryName(text);
       update();
       return;
    }
    showAlertBox(message,"ERROR");
   }

   public void addAttribute(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(categoryName.equals("Project.Main")){
         showAlertBox("you cant edit Project.Main","ERROR");
         return;
      }
      String text=attributeField.getText();
      if(text.isEmpty()) return;
      String message= ItemAndCategoryController.getInstance().addAttributeToCategory(categoryName,text);
      if(message.startsWith("Successful")){
         showAlertBox(message,"INFORMATION");
         update();
         return;
      }
      showAlertBox(message,"ERROR");
   }

   private void showAlertBox(String message,String type){
      if(type.equalsIgnoreCase("Error")){
         MusicManager.getInstance().playSound("error");
      }else {
         MusicManager.getInstance().playSound("notify");
      }
      Alert alert = new Alert(Alert.AlertType.valueOf(type));
      alert.setContentText(message);
      alert.showAndWait();
   }

   public void updateCategoryName(KeyEvent keyEvent) {
      String text=categoryNameField.getText();
      if(text.contains(" ")){
         categoryNameField.setStyle("-fx-text-fill: red;");
         return;
      }
      categoryNameField.setStyle("-fx-text-fill: green;");
   }

   public void delete(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message=ItemAndCategoryController.getInstance().removeCategory(categoryName);
      if(message.startsWith("Successful")){
         showAlertBox(message,"INFORMATION");
         SceneSwitcher.getInstance().setSceneTo("ManageCategories");
         return;
      }
      showAlertBox(message,"ERROR");
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("ManageCategories");
   }
}
