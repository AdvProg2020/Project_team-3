package Project.Client;

import Project.Client.Model.Users.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
      json.addProperty("account type","admin");
      json.remove("type");
      json.addProperty("type",4);
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

   public static String makeDeleteCategoryRequest(String categoryName){
      JsonObject json=new JsonObject();
      json.addProperty("token",Client.getInstance().getToken());
      json.addProperty("type",4);
      json.addProperty("content","delete category");
      json.addProperty("category name",categoryName);
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


   //type 0
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



}
