package Model.Logs;

import Control.Controller;
import Control.ItemAndCategoryController;
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
        id= Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize()," ");
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

    public int getTime() {
        return time;
    }

    public ArrayList<String> getDiscountCodeId() {
        return discountCodeId;
    }

    public void addDiscountCode(String id){
        discountCodeId.add(id);
    }

    public boolean hasDiscountWithID(String id)
    {
        if(discountCodeId.contains(id)) return true;
        return false;
    }

    public String removeDiscountCode(String id){
        if(hasDiscountWithID(id)==true){
            discountCodeId.remove(id);
            return "removed successfully!";
        }
        return "we do not have that discount code!";
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
