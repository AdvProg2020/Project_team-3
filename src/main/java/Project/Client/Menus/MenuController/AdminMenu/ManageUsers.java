package Project.Client.Menus.MenuController.AdminMenu;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import Server.Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageUsers {
   @FXML private ListView listView;
   @FXML private CheckBox adminCheck;
   @FXML private CheckBox buyerCheck;
   @FXML private CheckBox sellerCheck;
   @FXML private CheckBox assistantCheck;
   @FXML private CheckBox online;
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
      String userList="";
     if(adminCheck.isSelected()){
       userList+=MakeRequest.makeGetAllUserRequest("Admin",online.isSelected());
     }
      if(buyerCheck.isSelected()){
         userList+=MakeRequest.makeGetAllUserRequest("Buyer",online.isSelected());
      }
      if(sellerCheck.isSelected()){
         userList+=MakeRequest.makeGetAllUserRequest("Seller",online.isSelected());
      }
      if(assistantCheck.isSelected()){
         userList+=MakeRequest.makeGetAllUserRequest("Assistant",online.isSelected());
      }
      userList.replace("\n\n","\n");
      if(userList.equals("\n")==false)
      listView.getItems().addAll(userList.split("\n"));
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
      if(index==-1)
         return;
      String username=listView.getItems().get(index).toString();
      System.out.println(username);
      if(MakeRequest.isThereUserWithUsername(username)) {
         ManageUserIn.setUsername(username);
         SceneSwitcher.getInstance().setSceneTo("ManageUserIn", 348, 88);
      }
      listView.getSelectionModel().clearSelection();
   }

   public void refresh(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageUsers",600,400);
   }
}
