package Server.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileWriter;
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

   public String process(String command){
      JsonParser parser = new JsonParser();
      JsonObject commandJson = (JsonObject) parser.parse(command);
   if(commandJson.get("type").getAsInt()==1){  //login and register (doesnt contain token)
      return loginMenuProcessor(commandJson);
   }else if(commandJson.get("type").getAsInt()==2){  //buyer menu
      return buyerMenuProcessor(commandJson);
   }else if(commandJson.get("type").getAsInt()==3){  //seller menu
      return sellerMenuProcessor(commandJson);
   }else if(commandJson.get("type").getAsInt()==4){  //admin menu
      return adminMenuProcessor(commandJson);
   }else if(commandJson.get("type").getAsInt()==5){ //user general
      return userGeneralProcessor(commandJson);
   }
   return "Error: invalid command";
   }

   public String loginMenuProcessor(JsonObject command){
      if(command.get("content").toString().equals("\"login\"")){
         return UserController.getInstance().login(getJsonStringField(command,"username"),getJsonStringField(command,"password"));
      }

      if(command.get("content").toString().equals("\"logout\"")){
         return AuthTokenHandler.getInstance().logout(command.get("token").toString());
      }

      if(command.get("content").toString().equals("\"create account\"")) {
         String name = getJsonStringField(command,"name");
         String lastName = getJsonStringField(command,"lastName");
         String password = getJsonStringField(command,"password");
         String username = getJsonStringField(command,"username");
         String email = getJsonStringField(command,"email");
         String number = getJsonStringField(command,"number");
         double money = command.get("money").getAsDouble();
         System.out.println(username);
         if (command.get("account type").toString().equals("\"buyer\"")) {
            return UserController.getInstance().registerBuyer(money, username, password, name, lastName, email, number);
         }

         if (command.get("account type").toString().equals("\"seller\"")) {
            String companyName= getJsonStringField(command,"company");;
            return UserController.getInstance().registerSeller(money,username,password,name,lastName,email,number,companyName);
         }

         return "Error: invalid account type";
      }

      return "Error: invalid command";
   }

   public String adminMenuProcessor(JsonObject command){
     String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      Controller.getInstance().setCurrentOnlineUser(username);
      if(username==null) return "Error: incorrect Token";

      if(command.get("content").toString().equals("\"delete user\"")){
         return UserController.getInstance().deleteUser(command.get("username").toString());
      }

      if(command.get("content").toString().equals("\"user list\"")){
         return Database.getInstance().printFolderContent("Users").toString();
      }

      if(command.get("content").toString().equals("\"view user\"")){
         return UserController.getInstance().viewPersonalInfo(command.get("username").toString());
      }

      if(command.get("content").toString().equals("\"accept request\"")){
         return RequestController.getInstance().acceptRequest(command.get("requestId").toString());
      }

      if(command.get("content").toString().equals("\"decline request\"")){
         return RequestController.getInstance().declineRequest(command.get("requestId").toString());
      }

      if(command.get("content").toString().equals("\"view request\"")){
         return RequestController.getInstance().getRequestDetail(command.get("requestId").toString());
      }

      if(command.get("content").toString().equals("\"request list\"")){
         return Database.getInstance().printFolderContent("Requests").toString();
      }

      if(command.get("content").toString().toString().equals("\"delete category\"")){
         return ItemAndCategoryController.getInstance().removeCategory(command.get("category name").toString());
      }

      if(command.get("content").toString().equals("\"delete product\"")){
         return ItemAndCategoryController.getInstance().deleteItem(command.get("productId").toString());
      }

      return "Error: invalid command";
   }

   public String buyerMenuProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      Controller.getInstance().setCurrentOnlineUser(username);
      if(username==null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String sellerMenuProcessor(JsonObject command){
      String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      Controller.getInstance().setCurrentOnlineUser(username);
      if(username==null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String userGeneralProcessor(JsonObject command){
    //  String username=AuthTokenHandler.getInstance().getUserWithToken(command.get("token").toString());
      String username="Admin";
      Controller.getInstance().setCurrentOnlineUser(username);
      if(username==null) return "Error: incorrect Token";
      if(command.get("content").equals("\"view personal info\"")){
         return UserController.getInstance().viewPersonalInfo(username);
      }
      if(getJsonStringField(command,"content").equals("get online user")) {
         String name = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command, "token"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(UserController.getInstance().getUserByUsername(name)).toString();
      }
      return "Error: invalid command";
   }

   public  Matcher getMatcher(String regex, String input) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(input);
   }

   public String getJsonStringField(JsonObject json,String field){
      return json.get(field).toString().replace("\"","");
   }

}
