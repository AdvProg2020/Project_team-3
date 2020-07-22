package Server.Controller;

import Server.Model.Chat.Channel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ChatController {
   private static ChatController chatController;

   private ChatController(){

   }

   public static ChatController getInstance(){
      if(chatController==null) chatController=new ChatController();
      return chatController;
   }

   public Channel getChannel(String name){
      String path = "src/main/resources/Channels";
      String fileName = name + ".json";
      File file = new File(path + File.separator + fileName);
      if (!file.exists()) {
         Channel channel=new Channel(name);
         return channel;
      }else {
         Gson gson = new Gson();
         try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, Channel.class);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return null;
   }

   public void sendMessageToChannel(String channelName,String username,String message){
      Channel channel=getChannel(channelName);
      if(channel==null) return;
      channel.addMessage(username,message);
      Database.getInstance().saveChannel(channel);

   }

   public String getChannelContent(String channelName){
      Channel channel=getChannel(channelName);
      return channel.getContent().toString();
   }

}
