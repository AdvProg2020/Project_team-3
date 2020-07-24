package Project.Client;

import com.google.gson.JsonObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;


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
          Socket socket = new Socket("0.tcp.ngrok.io", 19092);
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
          Socket socket = new Socket("0.tcp.ngrok.io", 19092);
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
            //dataoutStream.write(encrypt(imageData,"thisisafuckingsecretcodedonotgiveittoanyone"));
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

   public static byte[] encrypt(byte[] data, String secret)
   {
      try
      {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         return Base64.getEncoder().encode(cipher.doFinal(data));
      }
      catch (Exception e)
      {
         System.out.println("Error while encrypting: " + e.toString());
      }
      return null;
   }

   private static SecretKeySpec secretKey;
   private static byte[] key;

   public static void setKey(String myKey)
   {
      MessageDigest sha = null;
      try {
         key = myKey.getBytes("UTF-8");
         sha = MessageDigest.getInstance("SHA-1");
         key = sha.digest(key);
         key = Arrays.copyOf(key, 16);
         secretKey = new SecretKeySpec(key, "AES");
      }
      catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      catch (UnsupportedEncodingException e) {
         e.printStackTrace();
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
