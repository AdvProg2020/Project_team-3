package Model.Logs;

import Controller.Controller;
import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Item;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog {

    private String id;
    private Date time;
    private ArrayList<Double> price;
    private ArrayList<String> allItemsID;
    private ArrayList<String> sellerName;
    private ArrayList<Integer> count;
    private String deliveryState;
    private String buyerName;

    public BuyLog(String buyerName) {
        price = new ArrayList<>();
        allItemsID = new ArrayList<>();
        sellerName = new ArrayList<>();
        count = new ArrayList<>();
        this.buyerName = buyerName;
        id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "");
    }

    public void addItem(double price, int count, String itemid, String sellerName) {
        this.price.add(price);
        this.count.add(count);
        this.allItemsID.add(itemid);
        this.sellerName.add(sellerName);

        SaleLog saleLog = new SaleLog(id, time, price, itemid, buyerName, count);
        UserController.getInstance().assignSaleLog(sellerName, saleLog);
    }

    @Override
    public String toString() {
        String ans = "";
        int index = 0;
        for (String id : allItemsID) {
            ans += "item id: " + id + " seller name: " + sellerName.get(index) + " count: " + count.get(index) + "\n";
            index++;
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
