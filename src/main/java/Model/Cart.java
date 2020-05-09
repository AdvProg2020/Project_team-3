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

    public String add(String itemId){
        if(0==ItemAndCategoryController.getInstance().getItemById(itemId).getInStock()){
            return "Error: there isn't enough in stock";
        }
    if(allItemCount.get(itemId)==null) {
        allItemCount.put(itemId,1);
        allItemId.add(itemId);
        return "Successful";
    }else{
        return "Error: item is already in the cart"; }
    }

    public void remove(String itemName){
       allItemCount.remove(itemName);
       allItemId.remove(itemName);
    }

    public boolean is_Empty(){
     if(allItemId.size()==0)   return true;
     return false;
    }

    public String changeCountBy(String itemId,int count){
       if(count>ItemAndCategoryController.getInstance().getItemById(itemId).getInStock()){
           return "Error: there isn't enough in stock";
       }
       allItemCount.replace(itemId,count);
       if(count<=0){
           allItemCount.remove(itemId);
           allItemId.remove(itemId);
       }else{
           allItemCount.replace(itemId,count);
       }
           return "Successful";
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
        if(allItemCount.containsKey(itemID)){
            return allItemCount.get(itemID);
        }
        return 0;
    }

    public double getCartPrice(){
        double price = 0;
        for (String id : allItemId) {
            price+=ItemAndCategoryController.getInstance().getItemById(id).getPrice() * allItemCount.get(id);
        }
        return price;
        }

    @Override
    public String toString(){
        String cart="";
        int count=0;
        for (String id : allItemId) {
            count=allItemCount.get(id);
            cart += ("item name:"+ItemAndCategoryController.getInstance().getItemById(id).getName()+" item id:"+id+" count:"+count+"\n");
        }
        return cart;
    }


    void empty(){
        allItemCount.clear();
       allItemId.clear();
    }
}
