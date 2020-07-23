package Project.Client;

import Project.Client.Model.Item;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
   private String bankAccountToken="";
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
         message.addProperty("timestamp", LocalDateTime.now().toString());
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

   public String sendImageToServer(String srcPath,String desPath){

      try {
         Socket clientSocket = new Socket("localhost", port);
         DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
         DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
         File file=new File(srcPath);
         dataOutputStream.writeUTF("image get "+desPath + " "+(int)file.length());
         dataOutputStream.flush();
         String received=dataInputStream.readUTF();
         byte[] imageData=new byte[(int)file.length()];
         FileInputStream fis=new FileInputStream(file);
         fis.read(imageData);
         fis.close();
         dataOutputStream.write(imageData);
         dataOutputStream.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public Image getImageFromServer(String imageName , String imageType){
      try {
         Socket clientSocket=new Socket("localhost",port);
         DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
         DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
         dataOutputStream.writeUTF("image send "+ imageName +" "+imageType);
         dataOutputStream.flush();
         String received=dataInputStream.readUTF();
         int size=Integer.parseInt(received);
         byte[]imageData=new byte[size];
         dataInputStream.readFully(imageData);
         Image image=new Image(new ByteArrayInputStream(imageData));
         return image;
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public Image getImageFromServer(String imageName , String imageType , int width, int height){
      try {
         Socket clientSocket=new Socket("localhost",port);
         DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
         DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
         dataOutputStream.writeUTF("image send "+ imageName +" "+imageType);
         dataOutputStream.flush();
         String received=dataInputStream.readUTF();
         int size=Integer.parseInt(received);
         byte[]imageData=new byte[size];
         dataInputStream.readFully(imageData);
         Image image=new Image(new ByteArrayInputStream(imageData),width,height,false,false);
         return image;
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public String getFileFromServer(Item item,String path){
      try {
         int port=MakeRequest.getSellerPort(item.getSellerName());
         Socket clientSocket=new Socket("localhost",port);
         DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
         DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
         dataOutputStream.writeUTF(item.getBrand());
         dataOutputStream.flush();
         String received=dataInputStream.readUTF();
         int size=Integer.parseInt(received);
         byte[]fileData=new byte[size];
         dataInputStream.readFully(fileData);
         File file=new File(path+File.separator+item.getName());
         FileOutputStream fileOutputStream=new FileOutputStream(file);
         fileOutputStream.write(fileData);
         fileOutputStream.close();
         System.out.println("finish");
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   /*

   public String getFileFromServer(String path,String fileName){
      try {
         Socket clientSocket=new Socket("localhost",port);
         DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
         DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
         dataOutputStream.writeUTF("file get "+ fileName);
         dataOutputStream.flush();
         String received=dataInputStream.readUTF();
         int size=Integer.parseInt(received);
         byte[]fileData=new byte[size];
         dataInputStream.readFully(fileData);
         File file=new File(path+File.separator+fileName);
         FileOutputStream fileOutputStream=new FileOutputStream(file);
         fileOutputStream.write(fileData);
         fileOutputStream.close();
         System.out.println("finish");
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }
    */

   public void setToken(String token) {
      Token = token;
   }

   public String getToken() {
      return Token;
   }

   public String getBankAccountToken() {
      return bankAccountToken;
   }

   public void setBankAccountToken(String bankAccountToken) {
      this.bankAccountToken = bankAccountToken;
   }

   public void initiateSellerServer(){

   }
}