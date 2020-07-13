package Project.Client.Menus.MenuController.AdminMenu;

import Server.Controller.Database;
import Server.Controller.UserController;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageUsers {
   @FXML
   private ListView listView;
   @FXML
   private CheckBox adminCheck;
   @FXML
   private CheckBox buyerCheck;
   @FXML
   private CheckBox sellerCheck;
   @FXML private AnchorPane pane;
   @FXML
   public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
     update(null);
   }

   public void update(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      listView.getItems().clear();
     if(adminCheck.isSelected()){
        for (Object admin : Database.getInstance().getAllUsername("Admin")) {
           listView.getItems().add(admin);
        }
     }
      if(buyerCheck.isSelected()){
         for (Object buyer : Database.getInstance().getAllUsername("Buyer")) {
            listView.getItems().add(buyer);
         }
      }
      if(sellerCheck.isSelected()){
         for (Object seller : Database.getInstance().getAllUsername("Seller")) {
            listView.getItems().add(seller);
         }
      }
      if(listView.getItems().isEmpty())
         listView.getItems().add("no user");
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void userSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=listView.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String username=listView.getItems().get(index).toString();
      System.out.println(username);
      if(UserController.getInstance().isThereUserWithUsername(username)) {
         ManageUserIn.setUsername(username);
         SceneSwitcher.getInstance().setSceneTo("ManageUserIn", 348, 88);
      }
      listView.getSelectionModel().clearSelection();
   }
}
