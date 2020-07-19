package Server.Model.Chat;

public class Message {
   String username;
   String message;

   Message(String username,String message){
      this.username=username;
      this.message=message;
   }

   @Override
   public String toString() {
      return username+": "+message;
   }
}
