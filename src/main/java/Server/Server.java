package Server;

import Server.Controller.*;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.util.*;

public class Server {
   private static ServerSocket server;
   private static DataOutputStream dataOutputStream;
   private static DataInputStream dataInputStream;
   private static HashSet<Integer> blockedIp=new HashSet<>();
   private static HashMap<String,Integer> sellerServerPort=new HashMap<>();
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_BLUE = "\u001B[34m";
   private long previousCommandMillis = 0;
   private int previousIP;
   private HashMap<Integer,Integer> suspiciousIPsConnection;
   private HashMap<Integer,Long> DoSBlackListTime;
   private Clock clock;
   public Server() {
      clock = Clock.systemDefaultZone();
      suspiciousIPsConnection = new HashMap<>();
      DoSBlackListTime = new HashMap<>();
      AuthTokenHandler.getInstance().updateTime();
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
                  if(suspiciousIPsConnection.containsKey(request.getLocalPort())) {
                     if (suspiciousIPsConnection.get(request.getLocalPort()) > 1000) {
                        blockedIp.add(request.getLocalPort());
                        System.err.println("connection refused:too many");
                        DoSBlackListTime.put(request.getLocalPort(),clock.millis());
                        request.close();
                        continue;
                     }
                  }
                  if(clock.millis() - previousCommandMillis < 10 && previousIP==request.getLocalPort()){
                     if(suspiciousIPsConnection.containsKey(request.getLocalPort())){
                        int cnt = suspiciousIPsConnection.get(request.getLocalPort());
                        suspiciousIPsConnection.replace(request.getLocalPort(),cnt,cnt+1);
                     }else{
                        suspiciousIPsConnection.put(request.getLocalPort(),1);
                     }
                  }
                  updateDoSList();
                  AuthTokenHandler.getInstance().updateLoginDetention();
                  AuthTokenHandler.getInstance().setUserIP(request.getLocalPort());
                  dataInputStream = new DataInputStream(new BufferedInputStream(request.getInputStream()));
                  dataOutputStream = new DataOutputStream(new BufferedOutputStream(request.getOutputStream()));
                  String command = dataInputStream.readUTF();
                  if((!command.startsWith("image"))&&(!command.startsWith("file"))){
                     System.out.println("FROM CLIENT: " + ANSI_BLUE+command+ANSI_RESET);
                     String response = RequestProcessor.getInstance().process(command);
                     System.out.println("FROM CONTROLLER: " +ANSI_GREEN +response+ANSI_RESET);
                     dataOutputStream.writeUTF(response);
                     dataOutputStream.flush();
                  }
                  else if(command.startsWith("image get")){
                     getImageFromClient(command,dataInputStream,dataOutputStream);
                  }else if(command.startsWith("image send")){
                     sendImageToClient(command,dataInputStream,dataOutputStream);
                  }
                   dataOutputStream.close();
                   dataInputStream.close();
                  previousIP = request.getLocalPort();
                   request.close();
                  previousCommandMillis = clock.millis();

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

   private void updateDoSList(){
      ArrayList<Integer> removeUs = new ArrayList<>();
      for(int ip:DoSBlackListTime.keySet()){
         if(clock.millis() - DoSBlackListTime.get(ip) > 3000){
            removeUs.add(ip);
         }
      }
      for(int ip:removeUs){
         DoSBlackListTime.remove(ip);
      }
   }

   public static void addIPBlocked(int ip){
      blockedIp.add(ip);
   }

   public static void removeIPBlocked(int ip){
      if(blockedIp.contains(ip)){
         blockedIp.remove(ip);
      }
   }

   public static void addSellerServerPort(String username,int port){
      sellerServerPort.put(username,port);
   }

   public static void removeSellerServerPort(String username){
      sellerServerPort.remove(username);
   }

   public static boolean isSellerServerOnline(String username){
      return sellerServerPort.containsKey(username);
   }

   public static int getSellerPort(String sellerName){
      if(sellerServerPort.containsKey(sellerName)) return sellerServerPort.get(sellerName);
      return -1;
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

   public static String encrypt(String strToEncrypt, String secret)
   {
      try
      {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      }
      catch (Exception e)
      {
         System.out.println("Error while encrypting: " + e.toString());
      }
      return null;
   }

   public static String decrypt(String strToDecrypt, String secret)
   {
      try
      {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      }
      catch (Exception e)
      {
         System.out.println("Error while decrypting: " + e.toString());
      }
      return null;
   }
}
