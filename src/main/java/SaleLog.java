import java.util.ArrayList;

public class SaleLog {
    private String id;
    private int time;
    private double saleAmount;
    private ArrayList<String> allItemsName;
    private String sellerName;
    private String BuyerName;
    private String deliveryState;
    private static String idCount="00000000";
    public SaleLog(int time, double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName) {
        this.time = time;
        this.saleAmount = saleAmount;
        this.allItemsName = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        id=idCount;
        idCount=Controller.getInstance().addId(idCount);
    }
}

