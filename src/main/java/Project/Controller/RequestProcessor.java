package Project.Controller;

import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {
   private static RequestProcessor requestProcessor;
   private Matcher matcher;
   private RequestProcessor(){

   }

   public static RequestProcessor getInstance(){
       if(requestProcessor==null) requestProcessor=new RequestProcessor();
       return requestProcessor;
   }

   public String process(JsonObject command){
   if(command.get("type").getAsInt()==1){  //login and register (doesnt contain token)
      return loginMenuProcessor(command);
   }else if(command.get("type").getAsInt()==2){  //buyer menu
      return buyerMenuProcessor(command);
   }else if(command.get("type").getAsInt()==3){  //seller menu
      return sellerMenuProcessor(command);
   }else if(command.get("type").getAsInt()==4){  //admin menu
      return adminMenuProcessor(command);
   }else if(command.get("type").getAsInt()==5){ //user general
      return userGeneralProcessor(command);
   }
   return "Error: invalid command";
   }

   public String loginMenuProcessor(JsonObject command){
      if(command.get("content").equals("login")){
         return UserController.getInstance().login(command.get("username").toString(),command.get("password").toString());
      }

      if(command.get("content").equals("logout")){
         //harvaght barname be current online user ehtiaj nadasht az comment dar miad
        // return AuthTokenHandler.getInstance().deleteToken(command.get("token").toString());
         return UserController.getInstance().logout();
      }

      if(command.get("content").equals("create account")) {
         String name = command.get("name").toString();
         String lastName = command.get("lastName").toString();
         String password = command.get("password").toString();
         String username = command.get("username").toString();
         String email = command.get("email").toString();
         String number = command.get("number").toString();
         double money = command.get("money").getAsDouble();

         if (command.get("account type").equals("buyer")) {
            return UserController.getInstance().registerBuyer(money, username, password, name, lastName, email, number);
         }

         if (command.get("account type").equals("seller")) {
            String companyName= command.get("company").toString();
            return UserController.getInstance().registerSeller(money,username,password,name,lastName,email,number,companyName);
         }

         return "Error: invalid account type";
      }

      return "Error: invalid command";
   }

   public String adminMenuProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      if(username==null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String buyerMenuProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      if(username==null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String sellerMenuProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      if(username==null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String userGeneralProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      if(username==null) return "Error: incorrect Token";
      if(command.get("content").equals("view personal info")){
         return UserController.getInstance().viewPersonalInfo(username);
      }
      return "Error: invalid command";
   }

   public  Matcher getMatcher(String regex, String input) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(input);
   }

}
