import java.util.ArrayList;

public class BuyLog {
    private String id;
    private int time;
    private double saleAmount;
    private ArrayList<String> allItemsName;
    private String sellerName;
    private String BuyerName;
    private String deliveryState;
    private static String idCount="00000000";
    public BuyLog(double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName) {
        this.saleAmount = saleAmount;
        this.allItemsName = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        //time=??;
        //deliveryState="?";
        id=idCount;
        idCount=Controller.getInstance().addId(idCount);
    }

}
