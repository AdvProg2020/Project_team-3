package Server.Controller;

import Server.Server;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class AuthTokenHandler {

   private static AuthTokenHandler authTokenHandler;
   private  final SecureRandom secureRandom = new SecureRandom();
   private  final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
   private HashMap<String,String> onlineUsersTokens;
   private HashMap<String,Long> tokenMillis;
   private HashMap<String,Integer> tokensIP;
   private HashMap<Integer,Integer> IPsFailedLogIns;
   private HashMap<Integer,Long> disabledIPsTime;
   private ArrayList<String> onlineUsername=new ArrayList<>();
   private Clock clock;
   private int userIP;
   private AuthTokenHandler(){
      onlineUsersTokens=new HashMap<>();
      tokenMillis = new HashMap<>();
      tokensIP = new HashMap<>();
      clock = Clock.systemDefaultZone();
      IPsFailedLogIns = new HashMap<>();
      disabledIPsTime = new HashMap<>();
   }

   public void setUserIP(int userIP) {
      this.userIP = userIP;
   }

   public static AuthTokenHandler getInstance(){
      if(authTokenHandler==null) authTokenHandler=new AuthTokenHandler();
      return authTokenHandler;
   }

   public String generateTokenForUser(String username){
      while(true) {
         byte[] randomBytes = new byte[24];
         secureRandom.nextBytes(randomBytes);
         String token=base64Encoder.encodeToString(randomBytes);
         if(onlineUsersTokens.containsKey(token)==false) {
            onlineUsersTokens.put(token, username);
            tokenMillis.put(token,clock.millis());
            onlineUsername.add(username);
            tokensIP.put(token,userIP);
            return token;
         }
      }
   }

   public void updateTime(){
      ArrayList<String> deleteTheseTokens = new ArrayList<>();
      for(String token:tokenMillis.keySet()){
         if(clock.millis() - tokenMillis.get(token) > 3600000){
            deleteTheseTokens.add(token);
         }
      }
      for(String token:deleteTheseTokens){
         tokenMillis.remove(token);
         String username = onlineUsersTokens.get(token);
         onlineUsersTokens.remove(token);
         onlineUsername.remove(username);
      }
   }

   public String deleteToken(String token){
      if(onlineUsersTokens.containsKey(token)){
         onlineUsername.remove(onlineUsersTokens.get(token));
         onlineUsersTokens.remove(token);
         return "Successful: ";
      }
      return "Error: ";
   }

   public String logout(String token){
      return deleteToken(token);
   }

   public String getUserWithToken(String token){
      if(onlineUsersTokens.containsKey(token)){
         if(tokensIP.get(token)!=userIP){
            return null;
         }
         return onlineUsersTokens.get(token);
      }
      return null;
   }

   public Boolean isUserOnline(String username){
      return onlineUsername.contains(username);
   }

   protected void addIPToLoginDetention(){
      if(IPsFailedLogIns.containsKey(userIP)){
         IPsFailedLogIns.replace(userIP,IPsFailedLogIns.get(userIP)+1);
      }
      else {
         IPsFailedLogIns.put(userIP,1);
      }
      if(IPsFailedLogIns.get(userIP) >= 3){
         disabledIPsTime.put(userIP,clock.millis());
         Server.addIPBlocked(userIP);
      }
   }

   public void updateLoginDetention(){
      for(int ip:disabledIPsTime.keySet()){
         if(clock.millis() - disabledIPsTime.get(ip) > 60000){
            Server.removeIPBlocked(ip);
         }
      }
   }
}