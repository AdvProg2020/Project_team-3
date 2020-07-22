package Server;

import Server.Controller.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
   private static ServerSocket server;
   private static DataOutputStream dataOutputStream;
   private static DataInputStream dataInputStream;
   private static HashSet<Integer> blockedIp=new HashSet<>();
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_BLUE = "\u001B[34m";

   public Server() {
      try {
         server = new ServerSocket(9000);
      } catch (IOException e) {
         e.printStackTrace();
      }
      new Thread(new Runnable() {
         @Override
         public synchronized void run() {
            Socket request=null;
            try {
               while (true) {
                  request = server.accept();
                  if(blockedIp.contains(request.getLocalPort())) {  //improper input
                     System.out.println("connection refused.");
                     request.close();
                     continue;
                  }
                  dataInputStream = new DataInputStream(new BufferedInputStream(request.getInputStream()));
                  dataOutputStream = new DataOutputStream(new BufferedOutputStream(request.getOutputStream()));
                  String command = dataInputStream.readUTF();
                  if((command.startsWith("image")==false)&&(command.startsWith("file")==false)){
                     System.out.println("FROM CLIENT: " + ANSI_BLUE+command+ANSI_RESET);
                     String response = RequestProcessor.getInstance().process(command);
                     System.out.println("FROM CONTROLLER: " +ANSI_GREEN +response+ANSI_RESET);
                     dataOutputStream.writeUTF(response);
                     dataOutputStream.flush();
                  }
                  else if(command.startsWith("image get")==true){
                     getImageFromClient(command,dataInputStream,dataOutputStream);
                  }else if(command.startsWith("image send")==true){
                     sendImageToClient(command,dataInputStream,dataOutputStream);
                  }else if(command.startsWith("file send")==true){
                     getFileFromClient(command,dataInputStream,dataOutputStream);
                  }
                   dataOutputStream.close();
                   dataInputStream.close();
                   request.close();
               }
            } catch (IOException e) {
              e.printStackTrace();
            } catch (NullPointerException e){
               try {
                  e.printStackTrace();
                  blockedIp.add(request.getLocalPort());
                  dataOutputStream.writeUTF("Error: connection refused your account has been blacklisted.");
                  dataOutputStream.flush();
                  dataOutputStream.close();
                  dataInputStream.close();
                  server.close();
                  new Server();
               } catch (IOException ex) {
               ex.printStackTrace();
              }
            }
         }
      }).start();
   }

   public String getImageFromClient(String command , DataInputStream dataInputStream, DataOutputStream dataOutputStream){
      String [] token=command.split(" ");
      String desPath=token[2];
      int size=Integer.parseInt(token[3]);
      try {
         dataOutputStream.writeUTF("ok");
         dataOutputStream.flush();
         byte[] imageData=new byte[size];
         System.out.println("reading data from client!");
         dataInputStream.readFully(imageData);
         File file=new File(desPath);
         FileOutputStream fileOutputStream=new FileOutputStream(file);
         fileOutputStream.write(imageData);
         fileOutputStream.close();
         System.out.println("finish");
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "done!";
   }

   public String getFileFromClient(String command , DataInputStream dataInputStream, DataOutputStream dataOutputStream){
      String [] token=command.split(" ");
      String desPath=token[2];
      int size=Integer.parseInt(token[3]);
      try {
         dataOutputStream.writeUTF("ok");
         dataOutputStream.flush();
         byte[] fileData=new byte[size];
         System.out.println("reading data from client!");
         dataInputStream.readFully(fileData);
         File file=new File(desPath);
         FileOutputStream fileOutputStream=new FileOutputStream(file);
         fileOutputStream.write(fileData);
         fileOutputStream.close();
         System.out.println("finish");
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "done!";
   }

   public String sendImageToClient(String command , DataInputStream dataInputStream , DataOutputStream dataOutputStream){
      String [] token=command.split(" ");
      String imageName=token[2];
      String imageType=token[3];
      String desPath="";
      if(imageType.equalsIgnoreCase("user")) desPath= UserController.getInstance().userImagePath(imageName);
      else if(imageType.equalsIgnoreCase("item")) desPath="src/main/resources/Images/ItemImages/"+imageName;
      File file=new File(desPath);
      System.out.println("the des path is: "+desPath);
      byte[]imageData=new byte[(int)file.length()];
      try {
         dataOutputStream.writeUTF(String.valueOf((int)file.length()));
         dataOutputStream.flush();
         FileInputStream fis=new FileInputStream(file);
         fis.read(imageData);
         fis.close();
         dataOutputStream.write(imageData);
         dataOutputStream.flush();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return "done!";
   }

   public static DataInputStream getDataInputStream(){
      return dataInputStream;
   }

   public static DataOutputStream getDataOutputStream() {
      return dataOutputStream;
   }

   public static void main(String[] args) {
      Database.getInstance().initiate();
      new Server();
      try {
         TransactionController.getInstance().initiate();
      }catch (NullPointerException e){
         System.out.println("bank server is not online.");
      }
      System.out.println("server is running");
   }

}
