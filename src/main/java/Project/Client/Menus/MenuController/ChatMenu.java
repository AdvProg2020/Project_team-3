package Project.Client.Menus.MenuController;


import Project.Client.MakeRequest;

import Project.Client.Menus.SceneSwitcher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ChatMenu {
   private static String receiver;
   private static String channelName;
   @FXML ListView channel;
   @FXML TextField message;
   private static  Timeline timeline;

   @FXML public void initialize() {
      update();
      timeline = new Timeline(new KeyFrame(Duration.millis(700),e->update()));
      timeline.setCycleCount(Animation.INDEFINITE); // loop forever
      timeline.play();
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
      message.clear();
      update();
   }


   public static void setReceiver(String receiver) {
      ChatMenu.receiver = receiver;
   }

   public static void setChannelName(String channelName) {
      ChatMenu.channelName = channelName;
   }

   public static Timeline getTimeline() {
      return timeline;
   }
}
