package Project.Client;

import Project.Client.CLI.View;
import Project.Client.Model.*;
import Project.Client.Model.Chat.Channel;
import Project.Client.Model.Logs.BuyLog;
import Project.Client.Model.Logs.SaleLog;
import Project.Client.Model.Users.User;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class MakeRequest {
   private ArrayList<BuyLog> buyLogs;

   //type1
   public static Boolean isTokenValid() {
      if (Client.getInstance().getToken() == null) return false;
      JsonObject json = new JsonObject();
      json.addProperty("type", 1);
      json.addProperty("content", "is token valid");
      json.addProperty("token", Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static String makeLoginRequest(String username, String password) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 1);
      json.addProperty("content", "login");
      json.addProperty("username", username);
      json.addProperty("password", password);
      String response = Client.getInstance().sendMessage(json);
      if (response.startsWith("Success"))
         Client.getInstance().setToken(response.substring(33, 65));
      return response;
   }

   public static String makeRegisterBuyerRequest(String name, String lastName, String username, String password, String email, String number, double money) {
      JsonObject json = register(name, lastName, username, password, email, number);
      json.addProperty("money", money);
      json.addProperty("account type", "buyer");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRegisterSellerRequest(String name, String lastName, String username, String password, String email, String number, double money, String companyName) {
      JsonObject json = register(name, lastName, username, password, email, number);
      json.addProperty("money", money);
      json.addProperty("company", companyName);
      json.addProperty("account type", "seller");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRegisterAdminRequest(String name, String lastName, String username, String password, String email, String number) {
      JsonObject json = register(name, lastName, username, password, email, number);
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("account type", "admin");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRegisterAssistantRequest(String name, String lastName, String username, String password, String email, String number) {
      JsonObject json = register(name, lastName, username, password, email, number);
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("account type", "assistant");
      return Client.getInstance().sendMessage(json);
   }

   private static JsonObject register(String name, String lastName, String username, String password, String email, String number) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 1);
      json.addProperty("content", "create account");
      json.addProperty("name", name);
      json.addProperty("lastName", lastName);
      json.addProperty("username", username);
      json.addProperty("password", password);
      json.addProperty("email", email);
      json.addProperty("number", number);
      return json;
   }

   public static String makeLogoutRequest() {
      SortAndFilter.getInstance().reset();
      JsonObject json = new JsonObject();
      json.addProperty("type", 1);
      json.addProperty("content", "logout");
      json.addProperty("token", Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   //type 2
   public static String buyCart(String discountId,String address){
      JsonObject json=initializeCart();
      json.addProperty("token",Client.getInstance().getToken());
      json.remove("type");
      json.addProperty("type",2);
      json.addProperty("content", "buy cart");
      json.addProperty("address",address);
      if(discountId!=null)
      json.addProperty("discount",discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static double makeGetCartPriceWithDiscountCode(String discountId){
      JsonObject json=initializeCart();
      json.addProperty("content", "get cart price with discount");
      json.remove("type");
      json.addProperty("type",2);
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("discount id",discountId);
      return Double.parseDouble(Client.getInstance().sendMessage(json));
   }



   public static String makeGetBuyerDiscountCodesRequest() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("type", 2);
      jsonObject.addProperty("content", "getAllDiscountCodes");
      String response= Client.getInstance().sendMessage(jsonObject);
      return response;
   }

   public static ArrayList<BuyLog> makeGetBuyerBuyLogsRequest(){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 2);
      json.addProperty("content", "get buyer buy log");
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(Client.getInstance().sendMessage(json));
      System.out.println(jsonObject.toString());

      int size=jsonObject.get("size").getAsInt();
      ArrayList<BuyLog> buyLogs= new ArrayList<>();
      Gson gson = new Gson();
      for(int i=0;i<size;i++){
         HashMap<String,Double> itemCount=new HashMap<>();
         HashMap<String,Double> itemPrice=new HashMap<>();
         HashMap<String,String> sellerName=new HashMap<>();
         ArrayList<String> itemIdArrayList=gson.fromJson(jsonObject.get("itemId"+i), ArrayList.class);
         ArrayList<Double> itemCountArrayList=gson.fromJson(jsonObject.get("itemCount"+i), ArrayList.class);
         ArrayList<Double> itemPriceArrayList=gson.fromJson(jsonObject.get("itemPrice"+i), ArrayList.class);
         ArrayList<String> itemSellerArrayList=gson.fromJson(jsonObject.get("sellerName"+i), ArrayList.class);
         for(int j=0;j<itemIdArrayList.size();j++){
            itemCount.put(itemIdArrayList.get(j),itemCountArrayList.get(j));
            itemPrice.put(itemIdArrayList.get(j),itemPriceArrayList.get(j));
            sellerName.put(itemIdArrayList.get(j),itemSellerArrayList.get(j));
         }
         String time=getJsonStringField(jsonObject,"time"+i);
         String address=getJsonStringField(jsonObject,"address"+i);
         buyLogs.add(new BuyLog(itemIdArrayList,itemCount,itemPrice,sellerName,address,time));
      }
      return buyLogs;
   }

   public static String makeCommentRequest(String comment,String itemId, String fatherCommentId) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", 2);
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("content", "comment");
      jsonObject.addProperty("comment", comment);
      jsonObject.addProperty("item id", itemId);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String makeRatingRequest(int score,String productId){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", 2);
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("content", "rate");
      jsonObject.addProperty("score", score);
      jsonObject.addProperty("id",productId);
      return Client.getInstance().sendMessage(jsonObject);
   }



   //type 3 seller menu
   public static String addProduct(String name, String brand, String description, double price, int inStock, String categoryName, ArrayList<String> attributesKey,ArrayList<String> attributeValue, String image, String video){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "add product");
      json.addProperty("name", name);
      json.addProperty("brand", brand);
      json.addProperty("description", description);
      json.addProperty("price",price);
      json.addProperty("inStock",inStock);
      json.addProperty("category",categoryName);
      json.addProperty("image",image);
      json.addProperty("video",video);
      JsonArray jsonArray = new Gson().toJsonTree(attributesKey).getAsJsonArray();
      json.add("attribute key", jsonArray);
      jsonArray = new Gson().toJsonTree(attributeValue).getAsJsonArray();
      json.add("attribute value", jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRemoveProductSellerRequest(String productId){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "remove product");
      json.addProperty("id", productId);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetAllSellerItems(){
      showProducts();
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "show seller items");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static String makeAddSaleRequest(String start,String end,int percent,ArrayList<String> selectedItemsID){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "add sale");
      json.addProperty("start",start);
      json.addProperty("end",end);
      json.addProperty("percent",percent);
      JsonArray jsonArray = new Gson().toJsonTree(selectedItemsID).getAsJsonArray();
      json.add("items",jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static Sale makeGetSale(String saleId){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 0);
      json.addProperty("content", "get sale");
      json.addProperty("id",saleId);
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(Client.getInstance().sendMessage(json));
      return ObjectMapper.jsonToSale(jsonObject);
   }

   public static ArrayList<SaleLog> makeGetSaleLogRequest(){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "get sale log");
      Gson gson=new Gson();
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(Client.getInstance().sendMessage(json));

      int size=jsonObject.get("size").getAsInt();
      ArrayList<SaleLog> saleLogs=new ArrayList<>();
      for(int i=0;i<size;i++){
         String seller=getJsonStringField(jsonObject,"seller"+i);
         String buyer=getJsonStringField(jsonObject,"buyer"+i);
         String itemId=getJsonStringField(jsonObject,"itemId"+i);
         String date=getJsonStringField(jsonObject,"date"+i);
         int price=jsonObject.get("price"+i).getAsInt();
         int count=jsonObject.get("count"+i).getAsInt();
         saleLogs.add(new SaleLog(date,price,itemId,buyer,count,seller));
      }
      return saleLogs;
   }

   public static String editSale(String saleId,String field,String value){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "edit sale");
      json.addProperty("id",saleId);
      json.addProperty("field",field);
      json.addProperty("value",value);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetSellerSaleToSimpleString(){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "get seller sale");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static String makeEditProductRequest(String productId,String field,String value){
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 3);
      json.addProperty("content", "edit product");
      json.addProperty("id", productId);
      json.addProperty("field",field);
      json.addProperty("value",value);
      return Client.getInstance().sendMessage(json);
   }

   //type 4 admin menu
   public static String makeRequestAcceptRequest(String requestId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "accept request");
      json.addProperty("requestId", requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRequestDeclineRequest(String requestId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "decline request");
      json.addProperty("requestId", requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetAllRequestsRequest() {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "request list");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static Boolean makeIsThereRequestWithId(String id) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "is there request with id");
      json.addProperty("requestId", id);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static String makeGetRequestInfoRequest(String requestId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "view request");
      json.addProperty("requestId", requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteUserRequest(String username) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "delete user");
      json.addProperty("username", username);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeViewUserRequest(String username) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "view user");
      json.addProperty("username", username);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetAllUserRequest(String userType, boolean online) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("userType", userType);
      json.addProperty("content", "user list");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static String makeDeleteProductAdminRequest(String productId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "delete product");
      json.addProperty("productId", productId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddCategoryRequest(String categoryName, String fatherCategoryName, ArrayList<String> attribute) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "add category");
      json.addProperty("category name", categoryName);
      json.addProperty("father category name", fatherCategoryName);
      JsonArray jsonArray = new Gson().toJsonTree(attribute).getAsJsonArray();
      json.add("attribute", jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddDiscountCodeRequest(int percentInt, String endDate, String startDate, ArrayList<String> allUsername, int usageInt, int maxDiscountInt) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "add discount code");
      json.addProperty("percent", percentInt);
      json.addProperty("usage", usageInt);
      json.addProperty("max discount", maxDiscountInt);
      json.addProperty("start date", startDate);
      json.addProperty("end date", endDate);
      JsonArray jsonArray = new Gson().toJsonTree(allUsername).getAsJsonArray();
      json.add("discount users", jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetAllDiscountCodesRequest() {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "get discount code list");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static String makeGetDiscountInfo(String discountId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "get discount info");
      json.addProperty("discountId", discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeEditDiscountIntField(String discountId, String field, int newInt) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "edit discount int field");
      json.addProperty("field", field);
      json.addProperty("value", newInt);
      json.addProperty("discountId", discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeEditDiscountEndDateField(String discountId, String endDate) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "edit discount code end date");
      json.addProperty("endDate", endDate);
      json.addProperty("discountId", discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteDiscountCodeRequest(String discountId) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "delete discount code");
      json.addProperty("discountId", discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteCategoryRequest(String categoryName) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "delete category");
      json.addProperty("category name", categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddAttributeToCategoryRequest(String attribute, String categoryName) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "add attribute");
      json.addProperty("attribute", attribute);
      json.addProperty("category name", categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRenameCategoryRequest(String categoryName, String newName) {
      JsonObject json = new JsonObject();
      json.addProperty("token", Client.getInstance().getToken());
      json.addProperty("type", 4);
      json.addProperty("content", "rename category");
      json.addProperty("category name", categoryName);
      json.addProperty("new name", newName);
      return Client.getInstance().sendMessage(json);
   }

   //type 5

   public static String getWalletLimit(){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","wallet limit");
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String setMainAccountRequest(){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","set bank");
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String exitFromBankAccount(){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","exit");
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String getBankAccountBalance(){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","getBankBalance");
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      jsonObject.addProperty("bankToken",Client.getInstance().getBankAccountToken());
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String getBankTokenForClient(String username , String password){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("content","getBankToken");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      jsonObject.addProperty("username",username);
      jsonObject.addProperty("password",password);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String makeAccountRequestInBank(String username,String password ,String firstName ,String lastName,String repeatPassword){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("type","5");
      jsonObject.addProperty("content","createBankAccount");
      jsonObject.addProperty("token",Client.getInstance().getToken());
      jsonObject.addProperty("username",username);
      jsonObject.addProperty("password",password);
      jsonObject.addProperty("firstName",firstName);
      jsonObject.addProperty("lastName",lastName);
      jsonObject.addProperty("repeatPassword",repeatPassword);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static User makeGetUserRequest() {
      if(MakeRequest.isTokenValid()==false) return null;
      JsonObject json = new JsonObject();
      json.addProperty("type", "5");
      json.addProperty("content", "get online user");
      json.addProperty("token", Client.getInstance().getToken());
      String response = Client.getInstance().sendMessage(json);
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(response);
      return ObjectMapper.jsonToUser(jsonObject);
   }

   public static String makeEditPersonalInfoRequest(String field, String newValue) {
      JsonObject json = new JsonObject();
      json.addProperty("type", "5");
      json.addProperty("content", "edit personal info");
      json.addProperty("field", field);
      json.addProperty("new value", newValue);
      json.addProperty("token", Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   public static String makeUserImagePathRequest() {
      JsonObject json = new JsonObject();
      json.addProperty("type", "5");
      json.addProperty("content", "user image path");
      json.addProperty("token", Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   public static String makeGetAllUserRequestInfo(){
      JsonObject json = new JsonObject();
      json.addProperty("type", "5");
      json.addProperty("content", "view user all request");
      json.addProperty("token", Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   //type 0

   public static String sendImageToServer(String srcPath, String desPath){
      File file=new File(srcPath);
      String imageDataString="";
      try {
         FileInputStream fis=new FileInputStream(file);
         byte[] imageData=new byte[(int) file.length()];
         fis.read(imageData);
         imageDataString= Base64.getEncoder().encodeToString(imageData);
         fis.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","SendImage");
      jsonObject.addProperty("type",0);
      jsonObject.addProperty("desPath",desPath);
      jsonObject.addProperty("image",imageDataString);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static Image getImageFromServer(String imageName, String type) {
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("content","getImage");
      jsonObject.addProperty("type","0");
      jsonObject.addProperty("imageName",imageName);
      jsonObject.addProperty("imageType",type);
      String response=Client.getInstance().sendMessage(jsonObject);
      byte[] imageData=Base64.getDecoder().decode(response);
      Image image=new Image(new ByteArrayInputStream(imageData));
      return image;
   }

   public static double makeGetItemPriceWithSaleRequest(String itemId){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("type","0");
      jsonObject.addProperty("content","item price with sale");
      jsonObject.addProperty("item id",itemId);
      JsonParser parser = new JsonParser();
      JsonObject response = (JsonObject) parser.parse(Client.getInstance().sendMessage(jsonObject));
      return response.get("price").getAsDouble();
   }

   public static boolean isInSaleItem(String itemId){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("type","0");
      jsonObject.addProperty("content","is in sale");
      jsonObject.addProperty("id",itemId);
      String response=Client.getInstance().sendMessage(jsonObject);
      return response.equals("true");
   }

   public static String makeAddViewToItem(String itemId){
      JsonObject jsonObject=new JsonObject();
      jsonObject.addProperty("type","0");
      jsonObject.addProperty("content","add view");
      jsonObject.addProperty("item id",itemId);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static String makeUpdateDateAndTimeRequest() {
      JsonObject json = new JsonObject();
      json.addProperty("type", "0");
      json.addProperty("content", "update date and time");
      return Client.getInstance().sendMessage(json);
   }

   public static boolean isThereUserWithUsername(String username) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "is there user with username");
      json.addProperty("username", username);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static Boolean isThereSaleWithId(String id){
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "is there sale with id");
      json.addProperty("id", id);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static ArrayList<String> makeGetAllCategoryName() {
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "category list");
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static String makeGetCategoryInfoRequest(String categoryName) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "get category info");
      json.addProperty("category name", categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static boolean isThereProductWithId(String productId) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "is there product with id");
      json.addProperty("product id", productId);
      String response=Client.getInstance().sendMessage(json);
      return response.equals("true");
   }

   public static boolean isThereCategoryWithName(String name) {
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "is there category with name");
      json.addProperty("name", name);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static ArrayList<String> getCategoryAttribute(String categoryName){
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content", "get category attribute");
      json.addProperty("name", categoryName);
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static Item getItem(String productId){
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content","get item");
      json.addProperty("product id",productId);
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(Client.getInstance().sendMessage(json));
      return ObjectMapper.jsonToItem(jsonObject);
   }

   public static Category getCategory(String categoryName){
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content","get category");
      json.addProperty("category name",categoryName);
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(Client.getInstance().sendMessage(json));
      return ObjectMapper.jsonToCategory(jsonObject);
   }

   public static ArrayList<Item> getAllItem(){
      JsonObject json = new JsonObject();
      json.addProperty("type", 0);
      json.addProperty("content","get all item id");
      Gson gson = new Gson();
      ArrayList<String> allItemId=gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      ArrayList<Item> allItems=new ArrayList<>();
      for (String itemId : allItemId) {
         allItems.add(getItem(itemId));
      }
      return allItems;
   }

   public static ArrayList<String> showProducts() {
      JsonObject json = new JsonObject();
      SortAndFilter sortAndFilter = SortAndFilter.getInstance();
      json.addProperty("type", 0);
      json.addProperty("content", "show products");
      if (sortAndFilter.getFilterAttribute()) {
         json.addProperty("filter attribute", "true");
         json.addProperty("attribute key", sortAndFilter.getAttributeKey());
         json.addProperty("attribute value", sortAndFilter.getAttributeValue());
      }
      if (sortAndFilter.getFilterBrand()) {
         json.addProperty("filter brand", "true");
         json.addProperty("brand", sortAndFilter.getBrandName());
      }
      if (sortAndFilter.getFilterAvailability()) {
         json.addProperty("filter availability", "true");
      }
      if (sortAndFilter.getFilterCategoryName()) {
         json.addProperty("filter category", "true");
         json.addProperty("category name", sortAndFilter.getCategoryName());
      }
      if (sortAndFilter.getFilterName()) {
         json.addProperty("filter name", "true");
         json.addProperty("name", sortAndFilter.getName());
      }
      if (sortAndFilter.getFilterSellerName()) {
         json.addProperty("filter seller", "true");
         json.addProperty("seller", sortAndFilter.getSellerName());
      }
      if (sortAndFilter.getFilterPriceRange()) {
         json.addProperty("filter price range", "true");
         json.addProperty("min", sortAndFilter.getMinPrice());
         json.addProperty("max", sortAndFilter.getMaxPrice());
      }
      if(sortAndFilter.getFilterSale()){
         json.addProperty("filter sale", "true");
      }
      json.addProperty("sort", sortAndFilter.showActiveSort());
      Gson gson=new Gson();
      ArrayList<String> result = gson.fromJson(Client.getInstance().sendMessage(json),ArrayList.class);
      return result;
   }

   public static ArrayList<String> show(String categoryName){
      SortAndFilter.getInstance().activateFilterCategoryName(categoryName);
      ArrayList<String> items=showProducts();
      SortAndFilter.getInstance().disableFilterCategoryName();
      return items;
   }

   public static double getCartPriceWithoutDiscount(){
   JsonObject json=initializeCart();
   json.addProperty("content", "get cart price without discount");
   return Double.parseDouble(Client.getInstance().sendMessage(json));
   }


   private static JsonObject initializeCart(){
      JsonObject json = new JsonObject();
      SortAndFilter sortAndFilter = SortAndFilter.getInstance();
      json.addProperty("type", 0);
      JsonArray jsonArray1 = new Gson().toJsonTree(Cart.getInstance().getAllItemId()).getAsJsonArray();
      json.add("all item id",jsonArray1);
      ArrayList<String> itemCount=new ArrayList<>();
      for (String s : Cart.getInstance().getAllItemId()) {
         itemCount.add(String.valueOf(Cart.getInstance().getAllItemCount().get(s)));
      }
      JsonArray jsonArray2 = new Gson().toJsonTree(itemCount).getAsJsonArray();
      json.add("item count",jsonArray2);
      return json;
   }

   public static void resetFilter(){
      JsonObject json = new JsonObject();
      SortAndFilter sortAndFilter = SortAndFilter.getInstance();
      json.addProperty("type", 0);
      json.addProperty("content", "reset filter");
      Client.getInstance().sendMessage(json);
   }

   //type 6
   public static String makeAddMessageToChannel(String channelName,String message){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", 6);
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("content", "add message to channel");
      jsonObject.addProperty("message", message);
      jsonObject.addProperty("channel name",channelName);
      return Client.getInstance().sendMessage(jsonObject);
   }

   public static Channel getChannel(String channelName){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", 6);
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("content", "get channel");
      jsonObject.addProperty("name",channelName);
      Gson gson=new Gson();
      return gson.fromJson(Client.getInstance().sendMessage(jsonObject),Channel.class);
   }

   public static ArrayList<String> getAssistantChannel(){
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", 6);
      jsonObject.addProperty("token", Client.getInstance().getToken());
      jsonObject.addProperty("content", "get assistant channel");
      Gson gson=new Gson();
      return gson.fromJson(Client.getInstance().sendMessage(jsonObject),ArrayList.class);
   }

   public static String getJsonStringField(JsonObject json, String field) {
      return json.get(field).toString().replace("\"", "");
   }

}
