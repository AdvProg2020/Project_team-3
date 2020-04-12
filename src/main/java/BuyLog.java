import java.util.ArrayList;

public class BuyLog {
    String id;
    int time;
    double saleAmount;
    ArrayList<String> allItemsName;
    String sellerName;
    String BuyerName;
    String deliveryState;
    static int TotalBuyLogs=1000;

    public BuyLog(double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName) {
        this.id=sellerName+"/"+buyerName+TotalBuyLogs;
        this.saleAmount = saleAmount;
        this.allItemsName = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        //time=??;
        //deliveryState="?";
        TotalBuyLogs++;
    }

}
