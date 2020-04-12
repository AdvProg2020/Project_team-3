import java.util.ArrayList;

public class Sale {
    private String id;
    private ArrayList<String> itemId = new ArrayList<>();
    private int startTime;
    private int endTime;
    private int offPercentage;
    private String status;

    public Sale (String id, int startTime, int endTime, int offPercentage){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.offPercentage = offPercentage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public String getId() {
        return id;
    }

    public void addItemToSale(String itemId){

    }

    public boolean saleHasItemWithID(String id){
      return false;
    }
}