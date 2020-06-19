package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

public class AddCategory {
   @FXML private  TextField categoryName;
   @FXML private  TextField attribute;
   @FXML private  ChoiceBox fatherCategoryChoice;
   @FXML private  ListView attributeList;
   private ArrayList<String> allAttribute=new ArrayList<>();

   @FXML public void initialize() {
      update();
   }

   public void update(){
      ArrayList<String> allCategories = Database.getInstance().printFolderContent("Categories");
      fatherCategoryChoice.getItems().clear();
      for (String category : allCategories) {
         fatherCategoryChoice.getItems().add(category);
      }
   }

   public void back(ActionEvent actionEvent) { SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void add(MouseEvent mouseEvent) {
     String text=attribute.getText();
     if((text.isEmpty())||(allAttribute.contains(text))) return;
     allAttribute.add(text);
     attributeList.getItems().add(text);
     attribute.clear();
   }

   public void remove(MouseEvent mouseEvent) {
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
         categoryName.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
         return;
      }
      categoryName.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
   }

   public void reset(MouseEvent mouseEvent) {
      categoryName.clear();
      attribute.clear();
      fatherCategoryChoice.getItems().clear();
      attributeList.getItems().clear();
      allAttribute.clear();
      update();
   }

   private void showAlertBox(String message,String type){
      Alert alert = new Alert(Alert.AlertType.valueOf(type));
      alert.setContentText(message);
      alert.showAndWait();
   }

   public void create(MouseEvent mouseEvent) {
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
         String message=ItemAndCategoryController.getInstance().addCategory(name,allAttribute,father);
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
