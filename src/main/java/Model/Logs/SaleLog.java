package Model.Logs;

import java.util.Date;

public class SaleLog {

    private Date time;
    private double price;
    private String itemId;
    private String buyerName;
    private int count;
    private String deliveryState;


    public SaleLog( Date time, double price, String itemId, String buyerName, int count) {
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
        return  " buyer name: " + buyerName + " count: " + count + " price: " + price;
    }

    public String toSimpleString(){
        return  buyerName + "     " + count + "     " + price;
    }
}

