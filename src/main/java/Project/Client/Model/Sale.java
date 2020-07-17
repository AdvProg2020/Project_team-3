package Project.Client.Model;




import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale {
    private String id;
    private String sellerUsername;
    private ArrayList<String> itemId = new ArrayList<>();
    private String startTime;
    private String endTime;
    private int offPercentage;

    private enum Status {accepted, addingProcess, editingProcess}

    Status status;

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.parse(endTime);
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.parse(startTime);
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime.toString();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime.toString();
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getAllItemId() {
        return itemId;
    }

    public void addItemToSale(String Id) {
        itemId.add(id);
    }

    public void removeItemFromSale(String id) {
        itemId.remove(id);
    }

    public String getSellerUsername() {
        return sellerUsername;
    }


    public String toSimpleString() {
        return id + "                        " + offPercentage + "                            " + endTime;
    }


}