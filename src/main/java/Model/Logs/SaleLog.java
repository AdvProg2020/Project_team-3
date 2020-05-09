package Model.Logs;

import Control.Controller;
import Control.ItemAndCategoryController;
import Model.Item;

import java.util.ArrayList;

public class SaleLog {
    private String id;
    private int time;
    private double decreasedPrice;
    private double saleAmount;
    private ArrayList<String> allItemsID;
    private String sellerName;
    private String buyerName;
    private String deliveryState;
    private static String idCount="00000000";
    public SaleLog(int time,double decreasedPrice ,double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName) {
        this.time = time;
        this.saleAmount = saleAmount;
        this.allItemsID = allItemsName;
        this.sellerName = sellerName;
        buyerName = buyerName;
        id= Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize()," ");
        this.decreasedPrice=decreasedPrice;
    }

    public String getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public double getDecreasedPrice() {
        return decreasedPrice;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }

    public boolean hasItemID(String id) {
        if(allItemsID.contains(id)) return true;
        return false;
    }

    public Item getItemById(String id){
        for(String itemID:allItemsID){
            if(itemID.equals(id)) return ItemAndCategoryController.getInstance().getItemById(id);
        }
        return null;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public static String getIdCount() {
        return idCount;
    }

    @Override
    public String toString(){
        String ans = "Sale ID:" + id +"   ";
        ans += "Total price:" + saleAmount + "   ";
        ans += "\nCustomer's name:"+ buyerName + "\nItems:\n";
        for(String id:allItemsID){
            ans+="ID:" + id + ItemAndCategoryController.getInstance().getItemById(id).getPrice();
            ans += "\n";
        }
        return ans;
    }
}

