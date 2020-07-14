package Server.Controller;

import Server.Model.Users.User;
import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {
   private static RequestProcessor requestProcessor;
   private Matcher matcher;

   private RequestProcessor() {

   }

   public static RequestProcessor getInstance() {
      if (requestProcessor == null) requestProcessor = new RequestProcessor();
      return requestProcessor;
   }

   public String process(String command) {
      JsonParser parser = new JsonParser();
      JsonObject commandJson = (JsonObject) parser.parse(command);
      if (commandJson.get("type").getAsInt() == 0) {  //genral command doesnt need token
         return generalProcessor(commandJson);
      } else if (commandJson.get("type").getAsInt() == 1) {  //login and register (doesnt contain token)
         return loginMenuProcessor(commandJson);
      } else if (commandJson.get("type").getAsInt() == 2) {  //buyer menu
         return buyerMenuProcessor(commandJson);
      } else if (commandJson.get("type").getAsInt() == 3) {  //seller menu
         return sellerMenuProcessor(commandJson);
      } else if (commandJson.get("type").getAsInt() == 4) {  //admin menu
         return adminMenuProcessor(commandJson);
      } else if (commandJson.get("type").getAsInt() == 5) { //user general
         return userGeneralProcessor(commandJson);
      }
      return "Error: invalid command";
   }

   public String loginMenuProcessor(JsonObject command) {
      if (getJsonStringField(command,"content").equals("login")) {
         return UserController.getInstance().login(getJsonStringField(command, "username"), getJsonStringField(command, "password"));
      }

      if (getJsonStringField(command,"content").equals("logout")) {
         return AuthTokenHandler.getInstance().logout(getJsonStringField(command,"token"));
      }

      if (getJsonStringField(command,"content").equals("create account")) {
         String name = getJsonStringField(command, "name");
         String lastName = getJsonStringField(command, "lastName");
         String password = getJsonStringField(command, "password");
         String username = getJsonStringField(command, "username");
         String email = getJsonStringField(command, "email");
         String number = getJsonStringField(command, "number");
         System.out.println(username);
         if ((getJsonStringField(command,"account type").equals("buyer"))) {
            double money = command.get("money").getAsDouble();
            return UserController.getInstance().registerBuyer(money, username, password, name, lastName, email, number);
         }

         if ((getJsonStringField(command,"account type").equals("seller"))) {
            double money = command.get("money").getAsDouble();
            String companyName = getJsonStringField(command, "company");
            return UserController.getInstance().registerSeller(money, username, password, name, lastName, email, number, companyName);
         }

         if((getJsonStringField(command,"account type").equals("admin"))) {
            String user = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
            Controller.getInstance().setCurrentOnlineUser(username);
            if (user == null) return "Error: incorrect Token";
            if(UserController.getInstance().getUserType(user).equals("Admin")==false) return "Error: you cant register admin.";
            return UserController.getInstance().registerAdmin(username,password,name,lastName,email,number);
         }

         return "Error: invalid account type";
      }

      return "Error: invalid command";
   }

   public String adminMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      if (command.get("content").toString().equals("\"delete user\"")) {
         return UserController.getInstance().deleteUser(getJsonStringField(command,"username"));
      }

      if (command.get("content").toString().equals("\"user list\"")) {
         String response="";
         if(getJsonStringField(command,"userType").equals("Admin")) {
            for (String user : Database.getInstance().getAllUsername("Admin")) {
               response+=user+"\n";
            }
         }
         if(getJsonStringField(command,"userType").equals("Seller")){
            for (String user : Database.getInstance().getAllUsername("Seller")) {
               response+=user+"\n";
            }
         }
         if(getJsonStringField(command,"userType").equals("Buyer")){
            for (String user : Database.getInstance().getAllUsername("Buyer")) {
               response+=user+"\n";
            }
         }
         if(getJsonStringField(command,"userType").equals("All")){
            for (String user : Database.getInstance().printFolderContent("Users")) {
               response+=user+"\n";
            }
         }
        return response;
      }

      if (command.get("content").toString().equals("\"view user\"")) {
         return UserController.getInstance().viewPersonalInfo(getJsonStringField(command,"username"));
      }

      if (command.get("content").toString().equals("\"accept request\"")) {
         return RequestController.getInstance().acceptRequest(getJsonStringField(command,"requestId"));
      }

      if (command.get("content").toString().equals("\"decline request\"")) {
         return RequestController.getInstance().declineRequest(getJsonStringField(command,"requestId"));
      }

      if (command.get("content").toString().equals("\"view request\"")) {
         return RequestController.getInstance().getRequestDetail(getJsonStringField(command,"requestId"));
      }

      if (command.get("content").toString().equals("\"request list\"")) {
         String response="";
         for (String requests : Database.getInstance().printFolderContent("Requests")) {
            response+=requests+"\n";
         }
         return response;
      }
      if (command.get("content").toString().equals("\"is there request with id\"")) {
        if(RequestController.getInstance().isThereRequestWithId(getJsonStringField(command,"requestId")))
           return "true";
           return "false";
      }
      if (command.get("content").toString().equals("\"delete category\"")) {
         return ItemAndCategoryController.getInstance().removeCategory(command.get("category name").toString());
      }

      if (command.get("content").toString().equals("\"delete product\"")) {
         return ItemAndCategoryController.getInstance().deleteItem(command.get("productId").toString());
      }

      if(getJsonStringField(command,"content").equals("add category")){
         String categoryName=getJsonStringField(command,"category name");
         String fatherCategoryName=getJsonStringField(command,"father category name");
         ArrayList<String> attributes=new ArrayList<>();
         for (JsonElement attribute : command.getAsJsonArray("attribute")) {
            attributes.add(attribute.getAsString());
         }
         return ItemAndCategoryController.getInstance().addCategory(categoryName,attributes,fatherCategoryName);
      }

      return "Error: invalid command";
   }

   public String buyerMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";

      if(command.get("content").toString().equals("\"getAllLogs\"")){
         return UserController.getInstance().getBuyLogs(username);
      }

      if(command.get("content").toString().equals("\"personalInfo\"")){
         return UserController.getInstance().viewPersonalInfo(username);
      }

      if(command.get("content").toString().equals("\"EditPersonalInfo\"")){
         String filed=getJsonStringField(command,"field");
         String filedValue=getJsonStringField(command,"fieldValue");
         UserController.getInstance().editPersonalInfo(username,filed,filedValue);
         return "successful";
      }

      return "Error: invalid command";
   }

   public String sellerMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      return "Error: invalid command";
   }

   public String userGeneralProcessor(JsonObject command) {
      System.out.println("salam");
      String username=AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      if (command.get("content").equals("\"view personal info\"")) {
         return UserController.getInstance().viewPersonalInfo(username);
      }
      if (getJsonStringField(command, "content").equals("get online user")) {
         String name = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command, "token"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(UserController.getInstance().getUserByUsername(name)).toString();
      }
      return "Error: invalid command";
   }

   public String generalProcessor(JsonObject command) {
      if (getJsonStringField(command,"content").equals("update date and time")) {
         Controller.getInstance().updateDateAndTime();
         return "Successful: ";
      }
      if (getJsonStringField(command,"content").equals("is there user with username")) {
        if(UserController.getInstance().isThereUserWithUsername(getJsonStringField(command,"username")))
           return "true";
        return "false";
      }
      if (getJsonStringField(command,"content").equals("category list")) {
         String response="";
         for (String category : Database.getInstance().printFolderContent("Categories")) {
            response+=category+"\n";
         }
         return response;
      }
      return "Error: invalid command";
   }

   public Matcher getMatcher(String regex, String input) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(input);
   }

   public String getJsonStringField(JsonObject json, String field) {
      return json.get(field).toString().replace("\"", "");
   }

}
