package Model.Logs;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyLog {


    private Date time;
    private ArrayList<Double> price;
    private ArrayList<String> allItemsID;
    private HashMap<String,String> itemsSeller;
    private HashMap<String,Integer> itemsCount;
    private String deliveryState;
    private String buyerName;
    private String address;

    public BuyLog(String buyerName,String address) {
        price = new ArrayList<>();
        allItemsID = new ArrayList<>();
        itemsSeller = new HashMap<>();
        itemsCount = new HashMap<>();
        this.buyerName = buyerName;
        this.address = address;
    }

    public void addItem(double price, int count, String itemID, String sellerName) {
        this.price.add(price);
        this.allItemsID.add(itemID);
        this.itemsSeller.put(itemID,sellerName);
        this.itemsCount.put(itemID,count);
        SaleLog saleLog = new SaleLog(time, price, itemID, buyerName, count);
        UserController.getInstance().assignSaleLog(sellerName, saleLog);
    }

    @Override
    public String toString() {
        String ans = "";

        for (String id : allItemsID) {
            ans += "item ID:" + id + " seller name: " + itemsSeller.get(id) + " count: " + itemsCount.get(id) + "\n";
        }
        return ans;
    }

    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }

    public boolean hasItemID(String id) {
        if (allItemsID.contains(id)) return true;
        return false;
    }

    public Item getItemById(String id) {
        for (String itemID : allItemsID) {
            if (itemID.equals(id)) return ItemAndCategoryController.getInstance().getItemById(id);
        }
        return null;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String newState) {
        this.deliveryState = newState;
    }

}
