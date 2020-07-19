package Server.Model.Chat;

import java.util.ArrayList;

public class Channel {
    ArrayList<Message> allMessage;
    String name;

    public Channel(String name){
       allMessage=new ArrayList<>();
       this.name=name;
    }



    public void addMessage(String username,String message){
       if((message!=null)&&(message.isEmpty()==false)){
          allMessage.add(new Message(username,message));
       }
    }

    public ArrayList<String> getContent(){
       ArrayList<String> content=new ArrayList<>();
       for (Message message : allMessage) {
          content.add(message.toString());
       }
       return content;
    }

    public String getName() {
       return name;
    }


}

