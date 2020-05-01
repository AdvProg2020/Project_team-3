package Model;

import Control.ItemAndCategoryController;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    String username;
    HashMap<String,Integer> allItemCount;
    ArrayList<String> allItemId;

    public Cart(String username){
        this.username=username;
        allItemCount=new HashMap<>();
        allItemId=new ArrayList<>();
    }

    public void add(String itemId,int count){
    if(allItemCount.get(itemId)==null) {
        allItemCount.put(itemId, count);
        allItemId.add(itemId);
        return;
    }
        changeCountBy(itemId,count);
        return;
    }

    public void remove(String itemName){
       allItemCount.remove(itemName);
       allItemId.remove(itemName);
    }

    public boolean is_Empty(){
     if(allItemId.size()==0)   return true;
     return false;
    }

    public void changeCountBy(String itemId,int count){
       allItemCount.replace(itemId,count);
    }

    public String getUsername() {
        return username;
    }

    public boolean includesItem(String itemId){
        if(allItemCount.get(itemId)==null){
            return false;
        }
            return true;
    }

    public int getItemCount(String itemID){
        return allItemCount.size();
    }

    public double getCartPrice(){
        double price = 0;
        for (String id : allItemId) {
            price+=ItemAndCategoryController.getInstance().getItemById(id).getPrice() * allItemCount.get(id);
        }
        return price;
        }



    void empty(){
        allItemCount.clear();
       allItemId.clear();
    }
}
