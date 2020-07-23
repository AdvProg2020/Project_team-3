package Project.Client;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;


public class SellerServer {
    private static SellerServer sellerServer;
    ServerSocket server;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Thread thread;

    public static SellerServer getInstance(){
       if(sellerServer==null) sellerServer=new SellerServer();
       return sellerServer;
    }

    private SellerServer() { }

    public void initiateServer(){
       try {
          server = new ServerSocket(0);
          Socket socket = new Socket("localhost", 9000);
          DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
          DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
          JsonObject json=new JsonObject();
          json.addProperty("type",3);
          json.addProperty("content","seller server port");
          json.addProperty("port",server.getLocalPort());
          json.addProperty("token",Client.getInstance().getToken());
          outputStream.writeUTF(json.toString());
          outputStream.flush();
          System.out.println(inputStream.readUTF());
          outputStream.close();
          inputStream.close();
          socket.close();
          new Thread(new Runnable() {
             @Override
             public void run() {
                try {
                   while (server.isBound()) {
                      Socket request = server.accept();
                      Thread thread = new RequestHandler(request);
                      new Thread(thread).start();
                   }
                } catch (Exception e) {
                }
                System.out.println("seller server closed");
             }
          }).start();
       } catch (IOException e) {
          e.printStackTrace();
       }
    }

    public void closeServer(){
       try {
          server.close();
       } catch (IOException e) {
          e.printStackTrace();
       }
    }

   private class RequestHandler extends Thread {
      private DataInputStream dataInputStream;
      private DataOutputStream dataoutStream;

      private RequestHandler(Socket socket) throws IOException {
         dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
         dataoutStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      }

      @Override
      public void run() {
         //file should be send from here
      }

    }

}
