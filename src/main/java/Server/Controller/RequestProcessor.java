package Server.Controller;

import Project.Client.Model.SortAndFilter;
import Server.Model.*;
import Server.Model.Logs.BuyLog;
import Server.Model.Logs.SaleLog;
import Server.Model.Users.Buyer;
import Server.Model.Users.Seller;
import Server.Model.Users.User;
import Server.Server;
import com.google.gson.*;


import java.io.*;
import java.net.Inet4Address;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
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
      /*if(commandJson.has("timestamp")) {
         try {
            LocalDateTime timestamp = LocalDateTime.parse(commandJson.get("timestamp").getAsString());
            if (LocalDateTime.now().isBefore(timestamp) || LocalDateTime.now().isAfter(timestamp.plusMinutes(2))) {
               return "Error: invalid command";
            }
         } catch (Exception e) {
            return "Error: invalid command";
         }
      }*/
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
      }else if (commandJson.get("type").getAsInt() == 6) { //chat menu
         return chatProcessor(commandJson);
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

         if((getJsonStringField(command,"account type").equals("assistant"))) {
            String user = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
            Controller.getInstance().setCurrentOnlineUser(username);
            if (user == null) return "Error: incorrect Token";
            if(UserController.getInstance().getUserType(user).equals("Admin")==false) return "Error: you cant register assistant.";
            return UserController.getInstance().registerAssistant(username,password,name,lastName,email,number);
         }

         return "Error: invalid account type";
      }

      return "Error: invalid command";
   }

   public String adminMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";
      if (getJsonStringField(command,"content").equals("delete user")) {
         return UserController.getInstance().deleteUser(getJsonStringField(command,"username"));
      }

      if (getJsonStringField(command,"content").equals("user list")) {
         ArrayList<String> response=new ArrayList<>();
         boolean online=command.has("online");
         if(getJsonStringField(command,"userType").equals("Admin")) {
            for (String user : Database.getInstance().getAllUsername("Admin")) {
               if((online)&&(AuthTokenHandler.getInstance().isUserOnline(user)==false)) continue;
               response.add(user);
            }
         }
         if(getJsonStringField(command,"userType").equals("Assistant")){
            for (String user : Database.getInstance().getAllUsername("Assistant")) {
               if((online)&&(AuthTokenHandler.getInstance().isUserOnline(user)==false)) continue;
               response.add(user);
            }
         }
         if(getJsonStringField(command,"userType").equals("Seller")){
            for (String user : Database.getInstance().getAllUsername("Seller")) {
               if((online)&&(AuthTokenHandler.getInstance().isUserOnline(user)==false)) continue;
               response.add(user);
            }
         }
         if(getJsonStringField(command,"userType").equals("Buyer")){
            for (String user : Database.getInstance().getAllUsername("Buyer")) {
               if((online)&&(AuthTokenHandler.getInstance().isUserOnline(user)==false)) continue;
               response.add(user);
            }
         }
         if(getJsonStringField(command,"userType").equals("All")){
            for (String user : Database.getInstance().printFolderContent("Users")) {
               if((online)&&(AuthTokenHandler.getInstance().isUserOnline(user)==false)) continue;
               response.add(user);
            }
         }
         Gson gson=new Gson();
        return gson.toJson(response);
      }

      if (getJsonStringField(command,"content").equals("view user")) {
         return UserController.getInstance().viewPersonalInfo(getJsonStringField(command,"username"));
      }

      if (getJsonStringField(command,"content").equals("get user by username")) {
         String enteredUN = getJsonStringField(command,"username");
         Gson gson = new Gson();
         return gson.toJson(UserController.getInstance().getUserByUsername(enteredUN));
      }

      if(getJsonStringField(command,"content").equals("deliver log")){
         String enteredUN = getJsonStringField(command,"username");
         int index = Integer.parseInt(getJsonStringField(command,"index"));
         UserController.getInstance().deliverLog(enteredUN,index);
         return "delivered log";
      }

      if (getJsonStringField(command,"content").equals("accept request")) {
         return RequestController.getInstance().acceptRequest(getJsonStringField(command,"requestId"));
      }

      if (getJsonStringField(command,"content").equals("decline request")) {
         return RequestController.getInstance().declineRequest(getJsonStringField(command,"requestId"));
      }

      if (getJsonStringField(command,"content").equals("view request")) {
         return RequestController.getInstance().getRequestDetail(getJsonStringField(command,"requestId"));
      }

      if (getJsonStringField(command,"content").equals("request list")) {
         Gson gson=new Gson();
         return gson.toJson(Database.getInstance().printFolderContent("Requests"));
      }

      if (getJsonStringField(command,"content").equals("is there request with id")) {
        if(RequestController.getInstance().isThereRequestWithId(getJsonStringField(command,"requestId")))
           return "true";
           return "false";
      }
      if (getJsonStringField(command,"content").equals("delete category")) {
         return ItemAndCategoryController.getInstance().removeCategory(getJsonStringField(command,"category name"));
      }

      if (getJsonStringField(command,"content").equals("delete product")) {
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
         Gson gson=new Gson();
         return gson.toJson( Database.getInstance().printFolderContent("DiscountCodes"));
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
      if ((username == null)&&(getJsonStringField(command,"content").equals("rate")==false)){
         return "Error: incorrect Token";
      } else  Controller.getInstance().setCurrentOnlineUser(username);


      if(getJsonStringField(command,"content").equals("get cart price with discount")){
         Cart cart=CartController.getInstance().getCurrentShoppingCart();
         Gson gson = new Gson();
         ArrayList<String> allItems=gson.fromJson(command.get("all item id"), ArrayList.class);
         ArrayList<String> allItemsCount=gson.fromJson(command.get("item count"), ArrayList.class);
         HashMap<String,Integer> itemIdWithCount=new HashMap<>();
         for(int i=0;i<allItems.size();i++) itemIdWithCount.put(allItems.get(i),Integer.parseInt(allItemsCount.get(i)));
         cart.setAllItemId(allItems);
         cart.setAllItemCount(itemIdWithCount);
         return String.valueOf(CartController.getInstance().getCartPriceWithDiscountCode(getJsonStringField(command,"discount id")));
      }

      if(getJsonStringField(command,"content").equals("buy cart")){
         String address=getJsonStringField(command,"address");
         String bankAccountId=getJsonStringField(command,"bankAccountId");
         String bankToken=getJsonStringField(command,"bankToken");
         Cart cart=CartController.getInstance().getCurrentShoppingCart();
         Gson gson = new Gson();
         ArrayList<String> allItems=gson.fromJson(command.get("all item id"), ArrayList.class);
         ArrayList<String> allItemsCount=gson.fromJson(command.get("item count"), ArrayList.class);
         HashMap<String,Integer> itemIdWithCount=new HashMap<>();
         for(int i=0;i<allItems.size();i++) itemIdWithCount.put(allItems.get(i),Integer.parseInt(allItemsCount.get(i)));
         cart.setAllItemId(allItems);
         cart.setAllItemCount(itemIdWithCount);
         if(command.has("discount")){
            String discountId=getJsonStringField(command,"discount");
            return CartController.getInstance().buy(address,discountId,bankAccountId,bankToken);
         }
         return CartController.getInstance().buy(address,bankAccountId,bankToken);
      }

      if(getJsonStringField(command,"content").equals("getAllDiscountCodes")){
         return UserController.getInstance().getBuyerDiscountCode();
      }

      if(getJsonStringField(command,"content").equals("get buyer buy log")){
         ArrayList<BuyLog> buyLogs=((Buyer)UserController.getInstance().getUserByUsername(username)).getBuyLogs();
         Gson gson=new Gson();
         return gson.toJson(buyLogs);
      }

      if(getJsonStringField(command,"content").equals("rate")){
         String id=getJsonStringField(command,"id");
         int score=command.get("score").getAsInt();
         return ItemAndCategoryController.getInstance().rate(score,id);
      }

      if(getJsonStringField(command,"content").equals("comment")){
         String itemId=getJsonStringField(command,"item id");
         String comment=getJsonStringField(command,"comment");
         return ItemAndCategoryController.getInstance().comment(comment,itemId);
      }

      if(getJsonStringField(command,"content").equals("bid")){
         String auctionId=getJsonStringField(command,"auction id");
         String bid =getJsonStringField(command,"bid");
         return AuctionController.getInstance().bidOnAuction(auctionId,Double.parseDouble(bid),username);
      }

      if(getJsonStringField(command,"content").equals("is seller server online")){
        String sellerName=getJsonStringField(command,"seller name");
        if(Server.isSellerServerOnline(sellerName)) return "true";
        return "false";
      }

      if(getJsonStringField(command,"content").equals("get seller port")){
         String sellerName=getJsonStringField(command,"seller name");
         return String.valueOf(Server.getSellerPort(sellerName));
      }



      return "Error: invalid command";
   }

   public String sellerMenuProcessor(JsonObject command) {
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";

      if(getJsonStringField(command,"content").equals("seller server port")){
         System.out.println("salam");
       int port=command.get("port").getAsInt();
         Server.addSellerServerPort(username,port);
         return "seller server saved";
      }

      if(getJsonStringField(command,"content").equals("seller remove port")){
         Server.removeSellerServerPort(username);
         return "Successful: seller port removed";
      }

      if(getJsonStringField(command,"content").equals("add product")){
         String name=getJsonStringField(command,"name");
         String brand=getJsonStringField(command,"brand");
         String description=getJsonStringField(command,"description");
         double price=command.get("price").getAsDouble();
         int inStock=command.get("inStock").getAsInt();
         String categoryName=getJsonStringField(command,"category");
         String image=getJsonStringField(command,"image");
         String video=getJsonStringField(command,"video");
         ArrayList<String> attributeValue=new ArrayList<>();
         for (JsonElement attribute : command.getAsJsonArray("attribute value")) {
            attributeValue.add(attribute.getAsString());
         }
         ArrayList<String> attributeKey=new ArrayList<>();
         for (JsonElement attribute : command.getAsJsonArray("attribute key")) {
            attributeKey.add(attribute.getAsString());
         }
         HashMap<String,String> attribute=new HashMap<>();
         for(int i=0;i<attributeKey.size();i++){
            attribute.put(attributeKey.get(i),attributeValue.get(i));
         }
         return ItemAndCategoryController.getInstance().addItem(name,brand,description,price,inStock,categoryName,attribute,image,video);
      }

      if(getJsonStringField(command,"content").equals("add file")){
         String name=getJsonStringField(command,"name");
         String description=getJsonStringField(command,"description");
         double price=command.get("price").getAsDouble();
         String image=getJsonStringField(command,"image");
         String path=getJsonStringField(command,"path");
         return ItemAndCategoryController.getInstance().addFile(name,description,price,image,path);
      }

      if(getJsonStringField(command,"content").equals("get sale log")){
         ArrayList<SaleLog> saleLogs=UserController.getInstance().getSaleLogs(username);
        /* JsonObject json=new JsonObject();
         json.addProperty("size",saleLogs.size());
           for(int i=0;i<saleLogs.size();i++) {
            SaleLog log = saleLogs.get(i);
            json.addProperty("buyer"+i , log.getBuyerName());
            json.addProperty("seller"+i , log.getSellerUsername());
            json.addProperty("itemId"+i , log.getItemId());
            json.addProperty("date"+i , log.getTime());
            json.addProperty("price"+i , log.getPrice());
            json.addProperty("count"+i , log.getCount());
         }
         return json.toString(); */
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(saleLogs);
      }

      if(getJsonStringField(command,"content").equals("remove product")){
         String id=getJsonStringField(command,"id");
         return ItemAndCategoryController.getInstance().deleteItem(id);
      }

      if(getJsonStringField(command,"content").equals("get seller sale")){
         Gson gson=new Gson();
         ArrayList<Sale> allSales=SaleAndDiscountCodeController.getInstance().getSellerSales(username);
         ArrayList<String> saleString=new ArrayList<>();
         for (Sale sale : allSales) {
            saleString.add(sale.toSimpleString());
         }
         return gson.toJson(saleString);
      }


      if(getJsonStringField(command,"content").equals("show seller items")){
         Gson gson=new Gson();
         Seller seller=(Seller) UserController.getInstance().getCurrentOnlineUser();
         ArrayList<String> result=new ArrayList<>();
         for (String id : seller.getAllItemsId()) {
            Item item=ItemAndCategoryController.getInstance().getItemById(id);
            if(item==null) continue;
            if(item.getState().equals("file")==false) result.add(item.showIdWithName());
         }
         return gson.toJson(result);
      }

      if(getJsonStringField(command,"content").equals("show seller files")){
         Gson gson=new Gson();
         Seller seller=(Seller) UserController.getInstance().getCurrentOnlineUser();
         ArrayList<String> result=new ArrayList<>();
         for (String id : seller.getAllItemsId()) {
            Item item=ItemAndCategoryController.getInstance().getItemById(id);
            if(item==null) continue;
            if(item.getState().equals("file")) result.add(item.showIdWithName());
         }
         return gson.toJson(result);
      }

      if(getJsonStringField(command,"content").equals("edit sale")){
         String id=getJsonStringField(command,"id");
         String field=getJsonStringField(command,"field");
         String value=getJsonStringField(command,"value");
         return SaleAndDiscountCodeController.getInstance().editSale(id,field,value);
      }



      if(getJsonStringField(command,"content").equals("add sale")){
         LocalDateTime start=getDate(getJsonStringField(command,"start"));
         LocalDateTime end=getDate(getJsonStringField(command,"end"));
         int percent=command.get("percent").getAsInt();
         Gson gson = new Gson();
         ArrayList<String> allItems=gson.fromJson(command.get("items"), ArrayList.class);
         return SaleAndDiscountCodeController.getInstance().addSale(start,end,percent,allItems);
      }

      if(getJsonStringField(command,"content").equals("edit product")){
         String id=getJsonStringField(command,"id");
         String field=getJsonStringField(command,"field");
         String value=getJsonStringField(command,"value");
         ItemAndCategoryController.getInstance().editItem(field,value,id);
         return "Successful:";
      }

      if(getJsonStringField(command,"content").equals("start auction")){
         int duration= Integer.parseInt(getJsonStringField(command,"duration"));
         double startPrice=Double.parseDouble(getJsonStringField(command,"price"));
         String itemID=getJsonStringField(command,"item");
         return AuctionController.getInstance().addAuction(duration,startPrice,itemID);
      }

      return "Error: invalid command";
   }

   public String userGeneralProcessor(JsonObject command) {
      String username=AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";

      if(getJsonStringField(command,"content").equals("get Transaction")){
         String bankToken=getJsonStringField(command,"bank Token");
         String type=getJsonStringField(command,"transaction");
         return TransactionController.getInstance().getTransaction(type,bankToken);
      }
      if(getJsonStringField(command,"content").equals("set Money")){
         double money=Double.parseDouble(getJsonStringField(command,"money"));
         User user=UserController.getInstance().getUserByUsername(username);
         String type=user.getType();
         if(type.equalsIgnoreCase("buyer")){
            Buyer buyer=(Buyer) user;
            buyer.setMoney(money);
            Database.getInstance().saveUser(buyer);
         }else if(type.equalsIgnoreCase("seller")){
            Seller seller=(Seller) user;
            seller.setMoney(money);
            Database.getInstance().saveUser(seller);
         }
         return "done!";
      }

      if(getJsonStringField(command,"content").equals("getBankBalance")){
         String bankAccountToken=getJsonStringField(command,"bankToken");
         return TransactionController.getInstance().getBalance(bankAccountToken);
      }

      if(getJsonStringField(command,"content").equals("wallet limit")){
         return String.valueOf(TransactionController.getInstance().getMinimumMoney());
      }
      if(getJsonStringField(command,"content").equals("set bank")){
         TransactionController.getInstance().setMainBankAccountId();
         return "done!";
      }
      if(getJsonStringField(command,"content").equals("wage percent")){
         int wage=TransactionController.getInstance().getWagePercent();
         return String.valueOf(wage);
      }

      if(getJsonStringField(command,"content").equals("bank receipt")){
         String bankToken=getJsonStringField(command,"bank token");
         String type=getJsonStringField(command,"receipt Type");
         String money=getJsonStringField(command,"money");
         String srcId=getJsonStringField(command,"srcId");
         String desId=getJsonStringField(command,"desId");
         String description=getJsonStringField(command,"description");
         return TransactionController.getInstance().getReceiptID(bankToken,type,money,srcId,desId,description);
      }

      if(getJsonStringField(command,"content").equals("createBankAccount")){
         String bankAccountUsername=getJsonStringField(command,"username");
         String bankAccountPassword=getJsonStringField(command,"password");
         String firstName=getJsonStringField(command,"firstName");
         String lastName=getJsonStringField(command,"lastName");
         String repeatPassword=getJsonStringField(command,"repeatPassword");
         return TransactionController.getInstance().addAccountToBank(firstName,lastName,bankAccountUsername,bankAccountPassword,repeatPassword);
      }
      if(getJsonStringField(command,"content").equals("getBankToken")){
         String bankAccountUsername=getJsonStringField(command,"username");
         String bankAccountPassword=getJsonStringField(command,"password");
         return TransactionController.getInstance().getBankToken(bankAccountUsername,bankAccountPassword);
      }
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

      if(getJsonStringField(command,"content").equals("add chat to auction")){
         String id=getJsonStringField(command,"auction id");
         String message = getJsonStringField(command,"message");
         AuctionController.getInstance().addChatToAuction(id,username,message);
         return "Successful:";
      }
      return "Error: invalid command";
   }

   public String generalProcessor(JsonObject command) {
      if(getJsonStringField(command,"content").equals("delete Image")){
         String path=getJsonStringField(command,"path");
         File file=new File(path);
         if(file.exists()) file.delete();
         return "done!";
      }
      if(getJsonStringField(command,"content").equals("set Numbers")){
         int wage=Integer.parseInt(getJsonStringField(command,"wage"));
         int min=Integer.parseInt(getJsonStringField(command,"min"));
         TransactionController.getInstance().setNumbers(wage,min);
         return "done!";
      }

      if(getJsonStringField(command,"content").equals("payReceipt")){
         String receiptId=getJsonStringField(command,"id");
         return TransactionController.getInstance().payReceipt(receiptId);
      }
      if(getJsonStringField(command,"content").equals("SendImage")){
         String desPath=getJsonStringField(command,"desPath");
         String imageDataString=getJsonStringField(command,"image");
         byte[] imageByte= Base64.getDecoder().decode(imageDataString);
         File file=new File(desPath);
         try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(imageByte);
            fos.close();
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         return "done!";
      }
      if(getJsonStringField(command,"content").equalsIgnoreCase("getImage")){
         String imageName=getJsonStringField(command,"imageName");
         String imageType=getJsonStringField(command,"imageType");
         String desPath="";
         if(imageType.equalsIgnoreCase("user")) desPath=UserController.getInstance().userImagePath(imageName);
         else if(imageType.equalsIgnoreCase("item")) desPath="src/main/resources/Images/ItemImages/"+imageName;
         File file=new File(desPath);
         String imageDataString="";
         try {
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[]imageData=new byte[(int)file.length()];
            fileInputStream.read(imageData);
            imageDataString=Base64.getEncoder().encodeToString(imageData);
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
         return imageDataString;
      }



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

      if (getJsonStringField(command,"content").equals("reset filter")) {
         SortAndFilterController.getInstance().reset();
            return "Successful:v";
      }

      if (getJsonStringField(command,"content").equals("is there sale with id")) {
         if(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(getJsonStringField(command,"id")))
            return "true";
         return "false";
      }

      if (getJsonStringField(command,"content").equals("is there category with name")) {
         if(ItemAndCategoryController.getInstance().isThereCategoryWithName(getJsonStringField(command,"name")))
            return "true";
         return "false";
      }

      if (getJsonStringField(command,"content").equals("category list")) {
         Gson gson=new Gson();
         return gson.toJson(Database.getInstance().printFolderContent("Categories"));
      }

      if (getJsonStringField(command,"content").equals("get category attribute")) {
         Category category=ItemAndCategoryController.getInstance().getCategoryByName(getJsonStringField(command,"name"));
         Gson gson=new Gson();
         if(category.getAttributes()==null) return gson.toJson(new ArrayList<String>());
         return gson.toJson(category.getAttributes());
      }

      if(getJsonStringField(command,"content").equals("get category info")){
         return ItemAndCategoryController.getInstance().getCategoryInfo(getJsonStringField(command,"category name"));
      }


      if(getJsonStringField(command,"content").equals("add view")){
         String itemId=getJsonStringField(command,"item id");
         ItemAndCategoryController.getInstance().addView(itemId);
         return "successful:";
      }

      if(getJsonStringField(command,"content").equals("itemCount")){
         String itemId=getJsonStringField(command,"itemId");
         int count=CartController.getInstance().getCurrentShoppingCart().getItemCount(itemId);
         return String.valueOf(count);
      }

      if(getJsonStringField(command,"content").equals("get item")){
         String productId=getJsonStringField(command,"product id");
         Item item=ItemAndCategoryController.getInstance().getItemById(productId);
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(item);
      }

      if(getJsonStringField(command,"content").equals("get category")){
         String categoryName=getJsonStringField(command,"category name");
         Category category=ItemAndCategoryController.getInstance().getCategoryByName(categoryName);
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         JsonParser parser = new JsonParser();
         return gson.toJson(category);
      }

      if(getJsonStringField(command,"content").equals("item price with sale")){
         String itemId=getJsonStringField(command,"item id");
         Item item=ItemAndCategoryController.getInstance().getItemById(itemId);
         JsonObject json=new JsonObject();
         json.addProperty("price",item.getPriceWithSale());
         return json.toString();
      }

      if(getJsonStringField(command,"content").equals("is in sale")){
         String itemId=getJsonStringField(command,"id");
         Item item=ItemAndCategoryController.getInstance().getItemById(itemId);
         if(item.isInSale()==true) return "true";
         return "false";
      }

      if(getJsonStringField(command,"content").equals("get all item id")){
         ArrayList<String> allItemId=new ArrayList<>();
         for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
            allItemId.add(item.getId());
         }
         return allItemId.toString();
      }

      if(getJsonStringField(command,"content").equals("get sale")){
         Sale sale=SaleAndDiscountCodeController.getInstance().getSaleById(getJsonStringField(command,"id"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(sale);
      }

      if(getJsonStringField(command,"content").equals("get all auctions")){
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(AuctionController.getInstance().getAllAuctions());
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
         if(command.has("filter sale"))
            SortAndFilterController.getInstance().activateFilterSale();
         if(command.has("filter price range"))
            SortAndFilterController.getInstance().activateFilterPriceRange(command.get("min").getAsDouble(),command.get("max").getAsDouble());
         if(command.has("filter seller"))
            SortAndFilterController.getInstance().activateFilterSellerName(getJsonStringField(command,"seller"));
            SortAndFilterController.getInstance().activateSort(getJsonStringField(command,"sort"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(SortAndFilterController.getInstance().show("Main"));
      }

      if(getJsonStringField(command,"content").equals("show files")){
         SortAndFilterController.getInstance().reset();
         if(command.has("filter name"))
            SortAndFilterController.getInstance().activateFilterName(getJsonStringField(command,"name"));
         if(command.has("filter seller"))
            SortAndFilterController.getInstance().activateFilterSellerName(getJsonStringField(command,"seller"));
         if(command.has("filter price range"))
            SortAndFilterController.getInstance().activateFilterPriceRange(command.get("min").getAsDouble(),command.get("max").getAsDouble());
         SortAndFilterController.getInstance().activateSort(getJsonStringField(command,"sort"));
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(SortAndFilterController.getInstance().show("File"));
      }

      if(getJsonStringField(command,"content").equals("get cart price without discount")){
         Cart cart=CartController.getInstance().getCurrentShoppingCart();
         Gson gson = new Gson();
         ArrayList<String> allItems=gson.fromJson(command.get("all item id"), ArrayList.class);
         ArrayList<String> allItemsCount=gson.fromJson(command.get("item count"), ArrayList.class);
         HashMap<String,Integer> itemIdWithCount=new HashMap<>();
         for(int i=0;i<allItems.size();i++) itemIdWithCount.put(allItems.get(i),Integer.parseInt(allItemsCount.get(i)));
         cart.setAllItemId(allItems);
         cart.setAllItemCount(itemIdWithCount);
         System.out.println(allItemsCount.toString());
         return String.valueOf(CartController.getInstance().getCartPriceWithoutDiscountCode());
      }
      return "Error: invalid command";
   }

   public String chatProcessor(JsonObject command){
      String username = AuthTokenHandler.getInstance().getUserWithToken(getJsonStringField(command,"token"));
      Controller.getInstance().setCurrentOnlineUser(username);
      if (username == null) return "Error: incorrect Token";

      if(getJsonStringField(command,"content").equals("add message to channel")){
         String message=getJsonStringField(command,"message");
         String channelName=getJsonStringField(command,"channel name");
         ChatController.getInstance().sendMessageToChannel(channelName,username,message);
         return "Successful";
      }

      if(getJsonStringField(command,"content").equals("get channel")){
         String name=getJsonStringField(command,"name");
         Gson gson=new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(ChatController.getInstance().getChannel(name));
      }

      if(getJsonStringField(command,"content").equals("get assistant channel")){
         ArrayList<String> allChannels=Database.getInstance().printFolderContent("Channels");
         ArrayList<String> assistantChannels=new ArrayList<>();
         for (String channelName : allChannels) {
            if(channelName.contains("#"+username)) assistantChannels.add(channelName.substring(0,channelName.indexOf('#')));
         }
         Gson gson=new GsonBuilder().setPrettyPrinting().create();
         return gson.toJson(assistantChannels);
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
         System.out.println("Invalid date. Try again.");
         return null;
      }
   }

}
