package Project.Client;

import Project.Client.Model.*;
import Project.Client.Model.Logs.SaleLog;
import Project.Client.Model.Users.*;


import com.google.gson.Gson;

import com.google.gson.JsonObject;


public class ObjectMapper {

   public static User jsonToUser(JsonObject json){
      Gson gson = new Gson();
      if(getJsonStringField(json,"type").equals("Admin")){
         return gson.fromJson(json, Admin.class);
      }else if(getJsonStringField(json,"type").equals("Buyer")){
         return gson.fromJson(json, Buyer.class);
      }else if(getJsonStringField(json,"type").equals("Seller")){
         return gson.fromJson(json, Seller.class);
      }else if(getJsonStringField(json,"type").equals("Assistant")){
         return gson.fromJson(json, Assistant.class);
      }
      return null;
   }


   public static Item jsonToItem(JsonObject json){
      Gson gson = new Gson();
      return gson.fromJson(json, Item.class);
   }

   public static Comment jsonToComment(JsonObject json){
      Gson gson = new Gson();
      return gson.fromJson(json, Comment.class);
   }

   public static Category jsonToCategory(JsonObject json){
      Gson gson = new Gson();
      return gson.fromJson(json, Category.class);
   }

   public static Sale jsonToSale(JsonObject json){
      Gson gson = new Gson();
      return gson.fromJson(json, Sale.class);
   }

   public static Auction jsonToAuction(String json){
      Gson gson = new Gson();
      return gson.fromJson(json,Auction.class);
   }

   public static SaleLog jsonToSaleLog(JsonObject json){
      String time=getJsonStringField(json,"time");
      double price=json.get("price").getAsDouble();
      String itemId=getJsonStringField(json,"itemId");
      String buyerName=getJsonStringField(json,"buyerName");
      int count=json.get("count").getAsInt();
      String sellerUsername=getJsonStringField(json,"sellerUsername");
      return new SaleLog(time,price,itemId,buyerName,count,sellerUsername);
   }

   public static String getJsonStringField(JsonObject json,String field){
      return json.get(field).toString().replace("\"","");
   }
}
