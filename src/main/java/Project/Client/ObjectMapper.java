package Project.Client;

import Project.Client.Model.Item;
import Project.Client.Model.Users.Admin;
import Project.Client.Model.Users.Buyer;
import Project.Client.Model.Users.Seller;
import Project.Client.Model.Users.User;
import Project.Client.Model.Category;
import Project.Client.Model.Logs.BuyLog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectMapper {

   public static User jsonToUser(JsonObject json){
      if(getJsonStringField(json,"type").equals("Admin")){
         return jsonToAdmin(json);
      }else if(getJsonStringField(json,"type").equals("Buyer")){
        return jsonToBuyer(json);
      }else if(getJsonStringField(json,"type").equals("Seller")){
       return jsonToSeller(json);
      }
      return null;
   }

   public static Admin jsonToAdmin(JsonObject json){
      String name = getJsonStringField(json,"name");
      String lastName = getJsonStringField(json,"lastName");
      String password = getJsonStringField(json,"password");
      String username = getJsonStringField(json,"username");
      String email = getJsonStringField(json,"email");
      String number = getJsonStringField(json,"number");
      return new Admin(username,password,name,lastName,email,number);
   }

   public static Seller jsonToSeller(JsonObject json){
      String name = getJsonStringField(json,"name");
      String lastName = getJsonStringField(json,"lastName");
      String password = getJsonStringField(json,"password");
      String username = getJsonStringField(json,"username");
      String email = getJsonStringField(json,"email");
      String number = getJsonStringField(json,"number");
      double money= json.get("money").getAsDouble();
      String company=getJsonStringField(json,"companyName");
      return new Seller(money,username,password,name,lastName,email,number,company);
   }

   public static Buyer jsonToBuyer(JsonObject json){
      String name = getJsonStringField(json,"name");
      String lastName = getJsonStringField(json,"lastName");
      String password = getJsonStringField(json,"password");
      String username = getJsonStringField(json,"username");
      String email = getJsonStringField(json,"email");
      String number = getJsonStringField(json,"number");
      double money= json.get("money").getAsDouble();
      return new Buyer(money,username,password,name,lastName,email,number);
   }

   public static Item jsonToItem(JsonObject json){
      System.out.println(json.toString());
      String name=getJsonStringField(json,"name");
      String brand=getJsonStringField(json,"brand");
      double price=json.get("price").getAsDouble();
      String description=getJsonStringField(json,"description");
      String productId=getJsonStringField(json,"id");
      int inStock=json.get("inStock").getAsInt();
      int viewCount=json.get("viewCount").getAsInt();
      int timesBought=json.get("timesBought").getAsInt();
      Gson gson = new Gson();
      HashMap attributes=gson.fromJson(json.get("attributes"), HashMap.class);
      ArrayList<String> allBuyers=gson.fromJson(json.get("buyerUserName"), ArrayList.class);
      return new Item(productId,description,name,brand,timesBought,price,inStock,viewCount,attributes,allBuyers);
   }

   public static String getJsonStringField(JsonObject json,String field){
      return json.get(field).toString().replace("\"","");
   }
}
