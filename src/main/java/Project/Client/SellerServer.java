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
          thread=new Run();
          thread.start();
           } catch (IOException e) {
           e.printStackTrace();
           }
    }

    public void closeServer(){
       try {
          Socket socket = new Socket("localhost", 9000);
          DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
          DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
          JsonObject json=new JsonObject();
          json.addProperty("type",3);
          json.addProperty("content","seller remove port");
          json.addProperty("port",server.getLocalPort());
          json.addProperty("token",Client.getInstance().getToken());
          outputStream.writeUTF(json.toString());
          outputStream.flush();
          System.out.println(inputStream.readUTF());
          outputStream.close();
          inputStream.close();
          socket.close();
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
         try {
            String desPath = dataInputStream.readUTF();
            File file=new File(desPath);
            System.out.println("the des path is  : "+desPath);
            byte[]imageData=new byte[(int)file.length()];
            dataoutStream.writeUTF(String.valueOf((int)file.length()));
            dataoutStream.flush();
            FileInputStream fis=new FileInputStream(file);
            fis.read(imageData);
            fis.close();
            dataoutStream.write(imageData);
            dataoutStream.flush();
            System.out.println("finish");
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
    }

   private class Run extends Thread {
      @Override
      public void run() {
         try {
            while (server.isClosed()==false) {
               Socket request = server.accept();
                  new RequestHandler(request).run();
            }
         } catch (Exception e) {
         }
         System.out.println("seller server closed");
      }
   }
}
