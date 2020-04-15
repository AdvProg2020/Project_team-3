import java.util.ArrayList;

public class BuyLog {
    private String id;
    private int time;
    private double saleAmount;
    private ArrayList<String> allItemsName;
    private String discountCodeId;
    private String sellerName;
    private String BuyerName;
    private String deliveryState;
    private static String idCount="00000000";
    public BuyLog(double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName
    ,String discountCodeId) {
        this.saleAmount = saleAmount;
        this.allItemsName = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        id=idCount;
        idCount=Controller.getInstance().addId(idCount);
        this.discountCodeId=discountCodeId;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public ArrayList<String> getAllItemsName() {
        return allItemsName;
    }

    public int getTime() {
        return time;
    }

    public String getDiscountCodeId() {
        return discountCodeId;
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
