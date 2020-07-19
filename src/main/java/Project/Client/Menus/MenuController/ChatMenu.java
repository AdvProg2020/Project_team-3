package Project.Client.Menus.MenuController;


import Project.Client.MakeRequest;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChatMenu {
   private static String receiver;
   private static String channelName;
   @FXML ListView channel;
   @FXML TextField message;

   @FXML public void initialize() {
      update();
   }

   public void update() {
      channel.getItems().clear();
      channel.getItems().addAll(MakeRequest.getChannel(channelName).getContent());
      if(channel.getItems().isEmpty()) {
         channel.getItems().add("there are no messages right now");
         return;
      }
   }



   public void send(MouseEvent mouseEvent) {
      String text=message.getText();
      if(text.isEmpty()) return;
      MakeRequest.makeAddMessageToChannel(channelName,text);
      update();
   }


   public static void setReceiver(String receiver) {
      ChatMenu.receiver = receiver;
   }

   public static void setChannelName(String channelName) {
      ChatMenu.channelName = channelName;
   }
}
