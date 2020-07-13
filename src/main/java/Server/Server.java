package Server;

import Server.Controller.RequestController;
import Server.Controller.RequestProcessor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   private ServerSocket server;
   private DataOutputStream dataOutputStream;
   private DataInputStream dataInputStream;

   public Server() {
      try {
         server = new ServerSocket(9000);
      } catch (IOException e) {
         e.printStackTrace();
      }
      new Thread(new Runnable() {
         @Override
         public synchronized void run() {
            try {
               while (true) {
                  Socket request = server.accept();
                  dataInputStream = new DataInputStream(new BufferedInputStream(request.getInputStream()));
                  dataOutputStream = new DataOutputStream(new BufferedOutputStream(request.getOutputStream()));
                  String command=dataInputStream.readUTF();
                  System.out.println("FROM CLIENT: "+command);
                  String response=RequestProcessor.getInstance().process(command);
                  System.out.println("FROM CONTROLLER: "+response);
                  dataOutputStream.writeUTF(response);
                  dataOutputStream.flush();
                  dataOutputStream.close();
                  dataInputStream.close();
                  request.close();
               }
            } catch (Exception e) {
               return;
            }
         }
      }).start();
   }

   public static void main(String[] args) {
      new Server();
      System.out.println("server is running");
      while (true){}
   }

}
