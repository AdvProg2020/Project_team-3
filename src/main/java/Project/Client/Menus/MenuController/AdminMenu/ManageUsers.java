package Project.Client.Menus.MenuController.AdminMenu;
import Project.Client.MakeRequest;
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
      String userList="";
     if(adminCheck.isSelected()){
       userList+=MakeRequest.makeGetAllUserRequest("Admin");
     }
      if(buyerCheck.isSelected()){
         userList+=MakeRequest.makeGetAllUserRequest("Buyer");
      }
      if(sellerCheck.isSelected()){
         userList+=MakeRequest.makeGetAllUserRequest("Seller");
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
      System.out.println(index);
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
}
