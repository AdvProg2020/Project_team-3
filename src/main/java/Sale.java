import java.util.ArrayList;

public class Sale {
    private String id;
    private ArrayList<Item> itemList = new ArrayList<>();
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

    public void addItemToList(Item item){
        if(!saleHasItemWithID(item.getId())) itemList.add(item);
    }

    public boolean saleHasItemWithID(String id){
        if(itemList.isEmpty()) return  false;
        for(Item item: itemList){
            if(item.getId().equals(id)) return true;
        }
        return false;
    }
}
