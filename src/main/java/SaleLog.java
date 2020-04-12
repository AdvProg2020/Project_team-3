import java.util.ArrayList;

public class SaleLog {
    String id;
    int time;
    double saleAmount;
    ArrayList<String> allItemsName;
    String sellerName;
    String BuyerName;
    String deliveryState;
    int TotalSellLogs=0;

    public SaleLog(int time, double saleAmount, ArrayList<String> allItemsName, String sellerName, String buyerName) {
        this.id=sellerName+"/"+buyerName+TotalSellLogs;
        this.time = time;
        this.saleAmount = saleAmount;
        this.allItemsName = allItemsName;
        this.sellerName = sellerName;
        BuyerName = buyerName;
        TotalSellLogs++;
    }
}

