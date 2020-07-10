package Project.Controller;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class AuthTokenHandler {
   private static AuthTokenHandler authTokenHandler;
   private  final SecureRandom secureRandom = new SecureRandom();
   private  final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
   private HashMap<String,String> onlineUsersTokens;

   private AuthTokenHandler(){
      onlineUsersTokens=new HashMap<>();
   }

   public static AuthTokenHandler getInstance(){
      if(authTokenHandler==null) authTokenHandler=new AuthTokenHandler();
      return authTokenHandler;
   }

   public void generateTokenForUser(String username){
      while(true) {
         byte[] randomBytes = new byte[24];
         secureRandom.nextBytes(randomBytes);
         String token=base64Encoder.encodeToString(randomBytes);
         if(onlineUsersTokens.containsKey(token)==false) {
            onlineUsersTokens.put(token, username);
            break;
         }
      }
   }

   public String deleteToken(String token){
      if(onlineUsersTokens.containsKey(token)){
         onlineUsersTokens.remove(token);
         return "Successful: ";
      }
      return "Error: ";
   }

}
