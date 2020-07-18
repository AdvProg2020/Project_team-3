package Project.Client.Model.Logs;



import Project.Client.MakeRequest;
import Project.Client.Model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog {


    private String time;
    private ArrayList<String> allItemsID;
    private HashMap<String,String> itemsSeller;
    private HashMap<String,Double> itemsCount;
    private HashMap<String,Double> itemsPrice;
    private String address;
    private Double discountGrandTotal;


  public BuyLog(ArrayList<String> allItemsID,HashMap<String,Double> itemsCount,HashMap<String,Double> itemsPrice,HashMap<String,String> itemsSeller,String address,String time){
      this.allItemsID=allItemsID;
      this.itemsSeller=itemsSeller;
      this.itemsCount=itemsCount;
      this.itemsPrice=itemsPrice;
      this.time=time;
      this.address=address;
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
            if (itemID.equals(id)) return MakeRequest.getItem(id);
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
    public HashMap<String, Double> getItemsCount() {
        return itemsCount;
    }



    public String getAddress() {
        return address;
    }

    public Double getDiscountGrandTotal() {
        return discountGrandTotal;
    }
}
