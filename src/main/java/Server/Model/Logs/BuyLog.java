package Server.Model.Logs;

import Server.Controller.Database;
import Server.Controller.ItemAndCategoryController;
import Server.Controller.UserController;
import Server.Model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog {


    private String time;
    private ArrayList<String> allItemsID;
    private HashMap<String,String> itemsSeller;
    private HashMap<String,Integer> itemsCount;
    private HashMap<String,Double> itemsPrice;
    private String buyerName;
    private String address;
    private Double discountGrandTotal;
    private String state;

    public BuyLog(String buyerName,String address,double discountGrandTotal,LocalDateTime time) {
        allItemsID = new ArrayList<>();
        itemsSeller = new HashMap<>();
        itemsCount = new HashMap<>();
        itemsPrice = new HashMap<>();
        this.buyerName = buyerName;
        this.address = address;
        this.discountGrandTotal = discountGrandTotal;
        this.time = time.toString();
        this.state = "under process";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addItem(double price, int count, String itemID, String sellerName) {
        this.itemsPrice.put(itemID,price);
        this.allItemsID.add(itemID);
        this.itemsSeller.put(itemID,sellerName);
        this.itemsCount.put(itemID,count);
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        item.addTimesBoughtBy(count);
        item.addBuyerUserName(buyerName);
        Database.getInstance().saveItem(item);
        SaleLog saleLog = new SaleLog(LocalDateTime.parse(time), price*count, itemID, buyerName, count , sellerName);
        UserController.getInstance().assignSaleLog(sellerName, saleLog);
    }

    @Override
    public String toString() {
        String ans ="Total sum:"+totalPrice()+"   "+time.toString();
        ans += "\nDiscount:" + discountGrandTotal;
        ans += "\nAddress:" + address;
        ans += "\nitem ID      price     seller      count\n";

        for (String id : allItemsID) {
            ans +=  id + "     " +itemsPrice.get(id)+"       "+itemsSeller.get(id) + "    " + itemsCount.get(id) + "\n";
        }
        return ans;
    }

    public String toSimpleString(){
        return time+"   "+totalPrice();
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

    public double totalPrice(){
        double ans = 0;
        for(String id:allItemsID){
            ans += itemsPrice.get(id)*itemsCount.get(id);
        }
        return ans;
    }

    public void setTime(LocalDateTime time) {
        this.time = time.toString();
    }

    public String getTime(){return time;}

    public ArrayList<String> getAllItemsID() {
        return allItemsID;
    }
    public HashMap<String, Integer> getItemsCount() {
        return itemsCount;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getAddress() {
        return address;
    }

    public Double getDiscountGrandTotal() {
        return discountGrandTotal;
    }

    public HashMap<String, Double> getItemsPrice() {
        return itemsPrice;
    }

    public HashMap<String, String> getItemsSeller() {
        return itemsSeller;
    }
}
