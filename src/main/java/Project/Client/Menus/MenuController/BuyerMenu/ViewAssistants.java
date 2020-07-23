package Project.Client.Menus.MenuController.BuyerMenu;
import Project.Client.MakeRequest;
import Project.Client.Menus.MenuController.AdminMenu.ManageRequestIn;
import Project.Client.Menus.MenuController.ChatMenu;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ViewAssistants {
   @FXML private ListView listView;
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
      listView.getItems().addAll(MakeRequest.makeGetAllUserRequest("Assistant",online.isSelected()));
      if(listView.getItems().isEmpty())
         listView.getItems().add("no user");
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
   }

   public void userSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      String username=listView.getItems().get(index).toString();
      System.out.println(username);
      if(MakeRequest.isThereUserWithUsername(username)) {
         ChatMenu.setReceiver(username);
         ChatMenu.setChannelName(MakeRequest.makeGetUserRequest().username+'#'+username);
         SceneSwitcher.getInstance().setSceneAndWait("ChatMenu", 600, 800);
         ChatMenu.getTimeline().stop();
      }
      listView.getSelectionModel().clearSelection();
   }

   public void refresh(ActionEvent actionEvent) {
      update(null);
   }
}
