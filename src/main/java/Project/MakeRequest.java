package Project;

import Project.Client.Client;
import com.google.gson.JsonObject;

public class MakeRequest {
   //type1
   public static JsonObject makeLoginRequest(String username,String password){
      JsonObject json=new JsonObject();
      json.addProperty("type",1);
      json.addProperty("content","login");
      json.addProperty("username",username);
      json.addProperty("password",password);
      Client.getInstance().sendMessage(json);
      return json;
  }

   public static JsonObject makeRegisterBuyerRequest(String name,String lastName,String username,String password,String email,String number,double money){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("money",money);
      json.addProperty("account type","buyer");
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeRegisterSellerRequest(String name,String lastName,String username,String password,String email,String number,double money,String companyName){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("money",money);
      json.addProperty("company",companyName);
      json.addProperty("account type","seller");
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeRegisterAdminRequest(String name,String lastName,String username,String password,String email,String number){
      JsonObject json=register(name,lastName,username,password,email,number);
      json.addProperty("account type","admin");
      json.remove("type");
      json.addProperty("type",4);
      Client.getInstance().sendMessage(json);
      return json;
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

   public static JsonObject makeLogoutRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type",1);
      json.addProperty("content","logout");
      Client.getInstance().sendMessage(json);
      return json;
   }
   //type 4
   public static JsonObject makeRequestAcceptRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","accept request");
      json.addProperty("requestId",requestId);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeRequestDeclineRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","decline request");
      json.addProperty("requestId",requestId);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeGetAllRequestsRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","request list");
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeGetRequestInfoRequest(String requestId){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","view request");
      json.addProperty("requestId",requestId);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeDeleteUserRequest(String username){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","delete user");
      json.addProperty("username",username);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeViewUserRequest(String username){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","view user");
      json.addProperty("username",username);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeGetAllUserRequest(){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","user list");
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeDeleteCategoryRequest(String categoryName){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","delete category");
      json.addProperty("category name",categoryName);
      Client.getInstance().sendMessage(json);
      return json;
   }

   public static JsonObject makeDeleteProductAdminRequest(String productId){
      JsonObject json=new JsonObject();
      json.addProperty("type",4);
      json.addProperty("content","delete product");
      json.addProperty("productId",productId);
      Client.getInstance().sendMessage(json);
      return json;
   }
   //type 5
   public static JsonObject makeGetPersonalInfoRequest(String Token){
      JsonObject json=new JsonObject();
      json.addProperty("type","5");
      json.addProperty("content","view personal info");
      Client.getInstance().sendMessage(json);
      return json;
   }

}
