package Project.Client;

import Project.Client.Model.SortAndFilter;
import Project.Client.Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MakeRequest {
   //type1
   public static String makeLoginRequest(String username,String password){
      JsonObject json=new JsonObject();
      json.addProperty("type",1);
      json.addProperty("content","login");
      json.addProperty("username",username);
      json.addProperty("password",password);
      String response=Client.getInstance().sendMessage(json);
      if(response.startsWith("Success"))
      Client.getInstance().setToken(response.substring(33,65));
      return response;
  }

   public static String makeRegisterBuyerRequest(String name,String lastName,String username,String password,String email,String number,double money){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("money",money);
      json.addProperty("account type","buyer");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRegisterSellerRequest(String name,String lastName,String username,String password,String email,String number,double money,String companyName){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("money",money);
      json.addProperty("company",companyName);
      json.addProperty("account type","seller");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRegisterAdminRequest(String name,String lastName,String username,String password,String email,String number){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("account type","admin");
      return Client.getInstance().sendMessage(json);
   }

   private static JsonObject register(String name,String lastName,String username,String password,String email,String number){
      JsonObject json=new JsonObject();
      json.addProperty("type",1);
      json.addProperty("content","create account");
      json.addProperty("name",name);
      json.addProperty("lastName",lastName);
      json.addProperty("username",username);
      json.addProperty("password",password);
      json.addProperty("email",email);
      json.addProperty("number",number);
      return json;
   }

   public static String makeLogoutRequest(){
      SortAndFilter.getInstance().reset();
      JsonObject json=new JsonObject();
      json.addProperty("type",1);
      json.addProperty("content","logout");
      json.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   //type 4


   public static String makeRequestAcceptRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","accept request");
      json.addProperty("requestId",requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRequestDeclineRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","decline request");
      json.addProperty("requestId",requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeGetAllRequestsRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","request list");
      return Client.getInstance().sendMessage(json);
   }

   public static Boolean makeIsThereRequestWithId(String id){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","is there request with id");
      json.addProperty("requestId",id);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static String makeGetRequestInfoRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","view request");
      json.addProperty("requestId",requestId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteUserRequest(String username){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","delete user");
      json.addProperty("username",username);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeViewUserRequest(String username){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","view user");
      json.addProperty("username",username);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeGetAllUserRequest(String userType){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("userType",userType);
      json.addProperty("content","user list");
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteProductAdminRequest(String productId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","delete product");
      json.addProperty("productId",productId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddCategoryRequest(String categoryName,String fatherCategoryName,ArrayList<String> attribute){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","add category");
      json.addProperty("category name",categoryName);
      json.addProperty("father category name",fatherCategoryName);
      JsonArray jsonArray = new Gson().toJsonTree(attribute).getAsJsonArray();
      json.add("attribute",jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddDiscountCodeRequest(int percentInt,String endDate,String startDate,ArrayList<String> allUsername,int usageInt,int maxDiscountInt){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","add discount code");
      json.addProperty("percent",percentInt);
      json.addProperty("usage",usageInt);
      json.addProperty("max discount",maxDiscountInt);
      json.addProperty("start date",startDate);
      json.addProperty("end date",endDate);
      JsonArray jsonArray = new Gson().toJsonTree(allUsername).getAsJsonArray();
      json.add("discount users",jsonArray);
      return Client.getInstance().sendMessage(json);
   }

   public static ArrayList<String> makeGetAllDiscountCodesRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","get discount code list");
      ArrayList<String> result=new ArrayList<>();
      for (String s : Client.getInstance().sendMessage(json).split("\n")) {
         if((s!=null)&&(s!="")&&(s!="\n"))
            result.add(s);
      }
      return result;
   }

   public static String makeGetDiscountInfo(String discountId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","get discount info");
      json.addProperty("discountId",discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeEditDiscountIntField(String discountId,String field,int newInt){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","edit discount int field");
      json.addProperty("field",field);
      json.addProperty("value",newInt);
      json.addProperty("discountId",discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeEditDiscountEndDateField(String discountId,String endDate){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","edit discount code end date");
      json.addProperty("endDate",endDate);
      json.addProperty("discountId",discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteDiscountCodeRequest(String discountId){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","delete discount code");
      json.addProperty("discountId",discountId);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeDeleteCategoryRequest(String categoryName){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","delete category");
      json.addProperty("category name",categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeAddAttributeToCategoryRequest(String attribute,String categoryName){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","add attribute");
      json.addProperty("attribute",attribute);
      json.addProperty("category name",categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static String makeRenameCategoryRequest(String categoryName,String newName){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","rename category");
      json.addProperty("category name",categoryName);
      json.addProperty("new name",newName);
      return Client.getInstance().sendMessage(json);
   }

   //type 2
    public static String makeGetBuyerLogsRequest(){
       JsonObject jsonObject=new JsonObject();
       jsonObject.addProperty("token",Client.getInstance().getToken());
       jsonObject.addProperty("type",2);
       jsonObject.addProperty("content","getAllLogs");
       return Client.getInstance().sendMessage(jsonObject);
    }

    public static String makeGetBuyerDiscountCodesRequest(){
       JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("token",Client.getInstance().getToken());
        jsonObject.addProperty("type",2);
        jsonObject.addProperty("content","getAllDiscountCodes");
        return Client.getInstance().sendMessage(jsonObject);
   }



   //type 5
   public static String makeGetPersonalInfoRequest(String Token){
      JsonObject json=new JsonObject();
      json.addProperty("type","5");
      json.addProperty("content","view personal info");
      return Client.getInstance().sendMessage(json);
   }

   public static User makeGetUserRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type","5");
      json.addProperty("content","get online user");
      json.addProperty("token",Client.getInstance().getToken());
      String response=Client.getInstance().sendMessage(json);
      System.out.println(response+" "+Client.getInstance().getToken());
      JsonParser parser = new JsonParser();
      JsonObject jsonObject = (JsonObject) parser.parse(response);
      return ObjectMapper.jsonToUser(jsonObject);
   }

   public static String makeEditPersonalInfoRequest(String field,String newValue){
      JsonObject json=new JsonObject();
      json.addProperty("type","5");
      json.addProperty("content","edit personal info");
      json.addProperty("field",field);
      json.addProperty("new value",newValue);
      json.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   public static String makeUserImagePathRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type","5");
      json.addProperty("content","user image path");
      json.addProperty("token",Client.getInstance().getToken());
      return Client.getInstance().sendMessage(json);
   }

   //type 0
   public static String getCartPriceWithoutDiscountCode() {
       JsonObject jsonObject=new JsonObject();
       jsonObject.addProperty("type","0");
       jsonObject.addProperty("content","cartWithoutDiscountCode");
       return Client.getInstance().sendMessage(jsonObject);
   }
    public static String MakeRequestIncreaseDecreaseCart(String id, int i) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type","0");
        jsonObject.addProperty("content","increaseDecrease");
        jsonObject.addProperty("itemId",id);
        jsonObject.addProperty("count",i);
        return Client.getInstance().sendMessage(jsonObject);
    }
    public static String MakeRequestEmptyCart() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type","0");
        jsonObject.addProperty("content","empty");
        return Client.getInstance().sendMessage(jsonObject);
    }
    public static String makeGetCartRequest() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type","0");
        jsonObject.addProperty("content","getCart");
        return Client.getInstance().sendMessage(jsonObject);
    }
   public static String makeUpdateDateAndTimeRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type","0");
      json.addProperty("content","update date and time");
      return Client.getInstance().sendMessage(json);
   }

   public static boolean isThereUserWithUsername(String username){
      JsonObject json=new JsonObject();
      json.addProperty("type",0);
      json.addProperty("content","is there user with username");
      json.addProperty("username",username);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static ArrayList<String> makeGetAllCategoryName(){
      ArrayList<String> result=new ArrayList<>();
      JsonObject json=new JsonObject();
      json.addProperty("type",0);
      json.addProperty("content","category list");
      for (String s : Client.getInstance().sendMessage(json).split("\n")) {
         if((s!=null)&&(s!="")&&(s!="\n"))
         result.add(s);
      }
      return result;
   }

   public static String makeGetCategoryInfoRequest(String categoryName){
      JsonObject json=new JsonObject();
      json.addProperty("type",0);
      json.addProperty("content","get category info");
      json.addProperty("category name",categoryName);
      return Client.getInstance().sendMessage(json);
   }

   public static boolean isThereProductWithId(String productId){
      JsonObject json=new JsonObject();
      json.addProperty("type",0);
      json.addProperty("content","is there product with id");
      json.addProperty("product id",productId);
      return Client.getInstance().sendMessage(json).equals("true");
   }

   public static ArrayList<String> showProducts(){
      JsonObject json=new JsonObject();
      SortAndFilter sortAndFilter=SortAndFilter.getInstance();
      json.addProperty("type",0);
      json.addProperty("content","show products");
      if(sortAndFilter.getFilterAttribute()){
         json.addProperty("filter attribute","true");
         json.addProperty("attribute key",sortAndFilter.getAttributeKey());
         json.addProperty("attribute value",sortAndFilter.getAttributeValue());
      }
      if(sortAndFilter.getFilterBrand()){
         json.addProperty("filter brand","true");
         json.addProperty("brand",sortAndFilter.getBrandName());
      }
      if(sortAndFilter.getFilterAvailability()){
         json.addProperty("filter availability","true");
      }
      if(sortAndFilter.getFilterCategoryName()){
         json.addProperty("filter category","true");
         json.addProperty("category name",sortAndFilter.getCategoryName());
      }
      if(sortAndFilter.getFilterName()){
         json.addProperty("filter name","true");
         json.addProperty("name",sortAndFilter.getName());
      }
      if(sortAndFilter.getFilterSellerName()){
         json.addProperty("filter seller","true");
         json.addProperty("seller",sortAndFilter.getSellerName());
      }
      if(sortAndFilter.getFilterPriceRange()){
         json.addProperty("filter price range","true");
         json.addProperty("min",sortAndFilter.getMinPrice());
         json.addProperty("max",sortAndFilter.getMaxPrice());
      }
      json.addProperty("sort",sortAndFilter.showActiveSort());
      ArrayList<String> result=new ArrayList<>();
      for (String s : Client.getInstance().sendMessage(json).split("\n")) {
         if((s!=null)&&(s!="")&&(s!="\n"))
            result.add(s);
      }
      return result;
   }



}
