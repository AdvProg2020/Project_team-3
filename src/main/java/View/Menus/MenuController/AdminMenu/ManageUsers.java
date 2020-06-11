package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import Controller.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;


import java.util.ArrayList;

public class ManageUsers {
   @FXML
   private ListView listView;
   @FXML
   private CheckBox adminCheck;
   @FXML
   private CheckBox buyerCheck;
   @FXML
   private CheckBox sellerCheck;

   @FXML
   public void initialize() {
     update(null);
   }

   public void update(MouseEvent mouseEvent) {
      listView.getItems().clear();
      int counter=0;
     if(adminCheck.isSelected()){
        for (Object admin : Database.getInstance().getAllUsername("Admin")) {
           listView.getItems().add(admin);
           counter++;
        }
     }
      if(buyerCheck.isSelected()){
         for (Object buyer : Database.getInstance().getAllUsername("Buyer")) {
            listView.getItems().add(buyer);
            counter++;
         }
      }
      if(sellerCheck.isSelected()){
         for (Object seller : Database.getInstance().getAllUsername("Seller")) {
            listView.getItems().add(seller);
            counter++;
         }
      }
      if(counter==0)
         listView.getItems().add("no user");
   }

   public void back(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void test(MouseEvent mouseEvent) {
      int index=listView.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String username=listView.getItems().get(index).toString();
      System.out.println(username);
      listView.getSelectionModel().clearSelection();
   }
}
