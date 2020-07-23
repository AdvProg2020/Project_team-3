package Project.Client.Menus.MenuController.AssistantMenu;

import Project.Client.MakeRequest;
import Project.Client.Menus.MenuController.ChatMenu;
import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ManageChats {
   @FXML ListView chats;

   @FXML public void initialize() {
      update();
   }

   public void update() {
      chats.getItems().clear();
      chats.getItems().addAll(MakeRequest.getAssistantChannel());
      if(chats.getItems().isEmpty()) {
         chats.getItems().add("there are no chat right now");
         return;
      }
   }

   public void refresh(ActionEvent actionEvent) {
      update();
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AssistantMenu");
   }

   public void chat(MouseEvent mouseEvent) {
      int index=chats.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      String username=chats.getItems().get(index).toString();
      if(MakeRequest.isThereUserWithUsername(username)) {
         ChatMenu.setReceiver(username);
         ChatMenu.setChannelName(username+'#'+MakeRequest.makeGetUserRequest().username);
         SceneSwitcher.getInstance().setSceneAndWait("ChatMenu", 600, 800);
         ChatMenu.getTimeline().stop();
      }
      chats.getSelectionModel().clearSelection();
   }

}
