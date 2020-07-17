package Project.Client.Model.Logs;

import java.time.LocalDateTime;

public class SaleLog {

    private String time;
    private double price;
    private String itemId;
    private String buyerName;
    private int count;
    private String sellerUsername;
    //private String deliveryState;


    public SaleLog(String time, double price, String itemId, String buyerName, int count , String sellerUsername) {
        this.time = time;
        this.price = price;
        this.itemId = itemId;
        this.buyerName = buyerName;
        this.count = count;
        this.sellerUsername = sellerUsername;
    }

    public double getPrice() {
        return price * count;
    }

    @Override
    public String toString() {
        return " buyer name: " + buyerName + " count: " + count + " price: " + price+"   time:"+time;
    }

    public String getItemId(){return itemId;}

    public String toSimpleString() {
        return "   "+ buyerName + "            " + count + "     " + price;
    }
}

