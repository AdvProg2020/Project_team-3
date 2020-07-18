package Project.Client.Model;



import Project.Client.MakeRequest;
import Server.Controller.ItemAndCategoryController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
   static Cart cart;
   HashMap<String, Integer> allItemCount;
   ArrayList<String> allItemId;

   public static Cart getInstance(){
      if(cart==null) cart=new Cart();
      return cart;
   }
   private Cart() {
      allItemCount = new HashMap<>();
      allItemId = new ArrayList<>();
   }

   public ArrayList<String> getAllItemId() {
      return allItemId;
   }



   public String add(String itemId) {
      Item item= MakeRequest.getItem(itemId);
      if (0 == item.getInStock()) {
         return "Error: there isn't enough in stock";
      }
      if (allItemCount.get(itemId) == null) {
         allItemCount.put(itemId, 1);
         allItemId.add(itemId);
         return "Successful";
      } else {
         return "Error: item is already in the cart";
      }
   }

   public void remove(String itemName) {
      allItemCount.remove(itemName);
      allItemId.remove(itemName);
   }

   public boolean isEmpty() {
      if (allItemId.size() == 0) return true;
      return false;
   }

   public String changeCountBy(String itemId, int count) {
      Item item= MakeRequest.getItem(itemId);
      if (count >item.getInStock()) {
         return "Error: there isn't enough in stock";
      }
      allItemCount.replace(itemId, count);
      if (count <= 0) {
         allItemCount.remove(itemId);
         allItemId.remove(itemId);
      }
      return "Successful";
   }


   public boolean includesItem(String itemId) {
      if (allItemCount.get(itemId) == null) {
         return false;
      }
      return true;
   }

   public int getItemCount(String itemID) {
      if (allItemCount.containsKey(itemID)) {
         return allItemCount.get(itemID);
      }
      return 0;
   }



   @Override
   public String toString() {
      String cart = "";
      int count = 0;
      for (String id : allItemId) {
         count = allItemCount.get(id);
         Item item= MakeRequest.getItem(id);
         cart += ("item name:" + item.getName() + " item id:" + id + " count:" + count + "\n");
      }
      return cart;
   }

   public HashMap<String, Integer> getAllItemCount() {
      return allItemCount;
   }

   public void empty() {
      allItemId.clear();
      allItemCount.clear();
   }

   public String cartIncreaseDecrease(String itemId, int count) { //for decrease count needs to be negative
      System.out.println(itemId);
      if (MakeRequest.isThereProductWithId(itemId)==false) {
         return "Error: invalid id";
      }
      if(includesItem(itemId)==false){
         return "Error: you must first add this item to your cart";
      }
      count += getItemCount(itemId);
      return changeCountBy(itemId, count);
   }
}
