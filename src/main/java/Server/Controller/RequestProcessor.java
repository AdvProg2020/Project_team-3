package Server.Controller;

import Project.Client.CLI.View;
import Project.Client.Model.SortAndFilter;
import Project.Client.Model.Users.User;
import Server.Model.Item;
import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

      if (getJsonStringField(command,"content").equals("is token valid")) {
         if(AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"))==null) return "false";
         return "true";
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
         return ItemAndCategoryController.getInstance().removeCategory(getJsonStringField(command,"category name"));
      }

      if (command.get("content").toString().equals("\"delete product\"")) {
         return ItemAndCategoryController.getInstance().deleteItem(getJsonStringField(command,"productId"));
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

      if(getJsonStringField(command,"content").equals("rename category")){
         return ItemAndCategoryController.getInstance().renameCategory(getJsonStringField(command,"category name"),getJsonStringField(command,"new name"));
      }

      if(getJsonStringField(command,"content").equals("add attribute")){
         return ItemAndCategoryController.getInstance().addAttributeToCategory(getJsonStringField(command,"category name"),getJsonStringField(command,"attribute"));
      }

      if(getJsonStringField(command,"content").equals("add discount code")){
         int percent=command.get("percent").getAsInt();
         int usage=command.get("usage").getAsInt();
         int maxDiscount=command.get("max discount").getAsInt();
         LocalDateTime start=getDate(getJsonStringField(command,"start date"));
         LocalDateTime end=getDate(getJsonStringField(command,"end date"));
         ArrayList<String> allUsers=new ArrayList<>();
         for (JsonElement attribute : command.getAsJsonArray("discount users")) {
            allUsers.add(attribute.getAsString());
         }
         return SaleAndDiscountCodeController.getInstance().addDiscountCode(percent,end,start,allUsers,usage,maxDiscount);
      }

      if(getJsonStringField(command,"content").equals("get discount code list")){
         String response="";
         for (String category : Database.getInstance().printFolderContent("DiscountCodes")) {
            response+=category+"\n";
         }
         return response;
      }

      if(getJsonStringField(command,"content").equals("get discount info")){
         return SaleAndDiscountCodeController.getInstance().printDiscount(getJsonStringField(command,"discountId"));
      }

      if(getJsonStringField(command,"content").equals("edit discount int field")){
         String field=getJsonStringField(command,"field");
         String discountId=getJsonStringField(command,"discountId");
         int value=command.get("value").getAsInt();
         if(field.equals("percent"))
            return SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountId,value);
         if(field.equals("usage"))
            return SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountId,value);
         if(field.equals("maxDiscount"))
            return SaleAndDiscountCodeController.getInstance().editDiscountCodeMaxDiscount(discountId,value);
         return "Error: invalid discount field";
      }

      if(getJsonStringField(command,"content").equals("edit discount code end date")){
         String date=getJsonStringField(command,"endDate");
         String discountId=getJsonStringField(command,"discountId");
         return SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountId,getDate(date));
      }

      if(getJsonStringField(command,"content").equals("delete discount code")){
         String discountId=getJsonStringField(command,"discountId");
         return SaleAndDiscountCodeController.getInstance().deleteDiscountCode(discountId);
      }

      return "Error: invalid command";
   }

   public String buyerMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      if(getJsonStringField(command,"content").equals("getAllLogs")){
         return UserController.getInstance().getBuyLogs(username);
      }
      if(getJsonStringField(command,"content").equals("getAllDiscountCodes")){
         return UserController.getInstance().getBuyerDiscountCode();
      }
      if(getJsonStringField(command,"content").equals("comment")){
         String fatherComment=getJsonStringField(command,"fatherCommentId");
         String itemId=getJsonStringField(command,"itemId");
         String comment=getJsonStringField(command,"comment");
         return ItemAndCategoryController.getInstance().comment(comment,itemId,fatherComment);
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
      String username=AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      if (getJsonStringField(command,"content").equals("view personal info")) {
         return UserController.getInstance().viewPersonalInfo(username);
      }
      if (getJsonStringField(command, "content").equals("get online user")) {
         String name = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command, "token"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(UserController.getInstance().getUserByUsername(name)).toString();
      }

      if(getJsonStringField(command,"content").equals("edit personal info")){
        UserController.getInstance().editPersonalInfo(username,getJsonStringField(command,"field"),getJsonStringField(command,"new value"));
        return "Successful: ";
      }
      if(getJsonStringField(command,"content").equals("user image path")){
         return UserController.getInstance().userImagePath(username);
      }
      if(getJsonStringField(command,"content").equals("view user all request")){
         return UserController.getInstance().getUserByUsername(username).getAllRequests();
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
      if (getJsonStringField(command,"content").equals("is there product with id")) {
         if(ItemAndCategoryController.getInstance().isThereItemWithId(getJsonStringField(command,"product id")))
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
      if(getJsonStringField(command,"content").equals("get category info")){
         return ItemAndCategoryController.getInstance().getCategoryInfo(getJsonStringField(command,"category name"));
      }
      if(getJsonStringField(command,"content").equals("cartWithoutDiscountCode")){
         return String.valueOf(CartController.getInstance().getCartPriceWithoutDiscountCode());
      }
      if(getJsonStringField(command,"content").equals("increaseDecrease")){
         String itemId=getJsonStringField(command,"itemId");
         int count=Integer.parseInt(getJsonStringField(command,"count"));
         return CartController.getInstance().cartIncreaseDecrease(itemId,count);
      }
      if(getJsonStringField(command,"content").equals("empty")){
         CartController.getInstance().getCurrentShoppingCart().empty();
         return "successful";
      }
      if(getJsonStringField(command,"content").equals("getCart")){
         Gson gson=new Gson();
         String response=gson.toJson(CartController.getInstance().getCurrentShoppingCart());
         return response;
      }
      if(getJsonStringField(command,"content").equals("getItem")){
         Gson gson=new Gson();
         String itemId=getJsonStringField(command,"itemId");
         Item item=ItemAndCategoryController.getInstance().getItemById(itemId);
         String response=gson.toJson(item);
         return response;
      }
      if(getJsonStringField(command,"content").equals("addView")){
         String itemId=getJsonStringField(command,"itemId");
         ItemAndCategoryController.getInstance().addView(itemId);
         return "successful!";
      }
      if(getJsonStringField(command,"content").equals("show products")){
         SortAndFilterController.getInstance().reset();
         if(command.has("filter attribute"))
            SortAndFilterController.getInstance().activateFilterAttribute(getJsonStringField(command,"attribute key"),getJsonStringField(command,"attribute value"));
         if(command.has("filter brand"))
            SortAndFilterController.getInstance().activateFilterBrandName(getJsonStringField(command,"brand"));
         if(command.has("filter availability"))
            SortAndFilterController.getInstance().activateFilterAvailability();
         if(command.has("filter category"))
            SortAndFilterController.getInstance().activateFilterCategoryName(getJsonStringField(command,"category name"));
         if(command.has("filter name"))
            SortAndFilterController.getInstance().activateFilterName(getJsonStringField(command,"name"));
         if(command.has("seller name"))
            SortAndFilterController.getInstance().activateFilterSellerName(getJsonStringField(command,"seller"));
         if(command.has("filter price range"))
            SortAndFilterController.getInstance().activateFilterPriceRange(command.get("min").getAsDouble(),command.get("max").getAsDouble());
         SortAndFilterController.getInstance().activateSort(getJsonStringField(command,"sort"));
         String response="";
         for (String productId : SortAndFilterController.getInstance().show("Project.Main")) {
            response+=productId+"\n";
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

   private LocalDateTime getDate(String dateString){
      LocalDateTime date;
      dateString=dateString.substring(8,10)+"/"+dateString.substring(5,7)+"/"+dateString.substring(0,4)+" 12:12";
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      try{
         date = LocalDateTime.parse(dateString,dateTimeFormatter);
         return date;
      }catch (Exception e){
         System.out.println(View.ANSI_RED+"Invalid date. Try again."+View.ANSI_RESET);
         return null;
      }
   }

}
