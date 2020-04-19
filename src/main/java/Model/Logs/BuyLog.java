package Model.Logs;

import Control.Controller;
import Model.Item;

import java.util.ArrayList;

public class BuyLog {
    private String id;
    private int time;
    private double saleAmount;
    private ArrayList<String> allItemsID;
    private ArrayList<String> discountCodeId;
    private String sellerName;
    private String BuyerName;
    private String deliveryState;
    private static String idCount="00000000";
    public BuyLog(double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName
    ) {
        this.saleAmount = saleAmount;
        this.allItemsID = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        id=idCount;
        idCount= Controller.getInstance().addId(idCount);
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }

    public boolean hasItemID(String id) {return false;}

    public Item getItemById(String id){return null;}

    public int getTime() {
        return time;
    }

    public ArrayList<String> getDiscountCodeId() {
        return discountCodeId;
    }

    public void addDiscountCode(){

    }

    public boolean hasDiscountWithID(){
        return false;
    }

    public void removeDiscountCode(){

    }

    public String getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String newState){
        this.deliveryState=newState;
    }

}
