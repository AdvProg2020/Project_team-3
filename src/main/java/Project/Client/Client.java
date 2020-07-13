package Project.Client;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class Client {
   private String Token;
   private int port=9000;
   private static Client client;

   private Client(){}

   public static Client getInstance(){
      if(client==null) client=new Client();
      return client;
   }

   public String sendMessage(JsonObject message){
      try {
      Socket clientSocket = new Socket("localhost", port);
      DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
      DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
      dataOutputStream.writeUTF(message.toString());
      dataOutputStream.flush();
      String response=dataInputStream.readUTF();
      dataOutputStream.flush();
      dataOutputStream.close();
      dataInputStream.close();
      clientSocket.close();
      return response;
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "error in server and client connection.";
   }

   public static void setClient(Client client) {
      Client.client = client;
   }

   public static Client getClient() {
      return client;
   }
}
