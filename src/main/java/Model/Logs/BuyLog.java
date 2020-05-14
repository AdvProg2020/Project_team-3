package Model.Logs;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog {


    private LocalDateTime time;
    private ArrayList<String> allItemsID;
    private HashMap<String,String> itemsSeller;
    private HashMap<String,Integer> itemsCount;
    private HashMap<String,Double> itemsPrice;
    private String deliveryState;
    private String buyerName;
    private String address;
    private Double discountGrandTotal;

    public BuyLog(String buyerName,String address) {
        allItemsID = new ArrayList<>();
        itemsSeller = new HashMap<>();
        itemsCount = new HashMap<>();
        itemsPrice = new HashMap<>();
        this.buyerName = buyerName;
        this.address = address;
    }

    public void addItem(double price, int count, String itemID, String sellerName) {
        this.itemsPrice.put(itemID,price);
        this.allItemsID.add(itemID);
        this.itemsSeller.put(itemID,sellerName);
        this.itemsCount.put(itemID,count);
        SaleLog saleLog = new SaleLog(time, price, itemID, buyerName, count);
        UserController.getInstance().assignSaleLog(sellerName, saleLog);
    }

    @Override
    public String toString() {
        String ans ="Total sum:"+totalPrice()+"   "+time.toString();
        ans += "\nDiscount:" + discountGrandTotal;
        ans += "\nAddress:" + address;
        ans += "\nitem ID        price       seller          count\n";

        for (String id : allItemsID) {
            ans +=  id + "     " +itemsPrice.get(id)+"       "+itemsSeller.get(id) + "    " + itemsCount.get(id) + "\n";
        }
        return ans;
    }

    public String toSimpleString(){
        return time+"   "+totalPrice();
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

    public double totalPrice(){
        double ans = 0;
        for(String id:allItemsID){
            ans += itemsPrice.get(id);
        }
        return ans;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setDiscountGrandTotal(Double discountGrandTotal) {
        this.discountGrandTotal = discountGrandTotal;
    }
}
