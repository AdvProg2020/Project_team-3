package Model.Logs;

import java.util.Date;

public class SaleLog {
    private String id;
    private Date time;
    private double price;
    private String itemId;
    private String buyerName;
    private int count;
    private String deliveryState;


    public SaleLog(String id, Date time, double price, String itemId, String buyerName, int count) {
        this.id = id;
        this.time = time;
        this.price = price;
        this.itemId = itemId;
        this.buyerName = buyerName;
        this.count = count;
    }

    public double getPrice() {
        return price * count;
    }

    @Override
    public String toString() {
        return "item id: " + id + " buyer name: " + buyerName + " count: " + count + " price: " + price;
    }

    public String toSimpleString(){
        return id + "     " + buyerName + "     " + count + "     " + price;
    }
}

