package Project.Model;

import Project.Controller.Controller;
import Project.Controller.ItemAndCategoryController;

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

    public Sale(LocalDateTime startTime, LocalDateTime endTime, int offPercentage, String sellerUsername, ArrayList<String> saleItems) {
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.offPercentage = offPercentage;
        this.id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Sales");
        this.sellerUsername = sellerUsername;
        itemId = saleItems;
    }

    public Sale(String id, String sellerUsername, ArrayList<String> items, String startTime, String endTime, int offPercentage, String status) {
        this.id = id;
        this.sellerUsername = sellerUsername;
        this.itemId = items;
        this.startTime = startTime;
        this.endTime = endTime;
        this.offPercentage = offPercentage;
        if(status.startsWith("acc")){
            this.status = Status.accepted;
        }else if(status.startsWith("add")){
            this.status = Status.addingProcess;
        }else if(status.startsWith("edi")){
            this.status = Status.editingProcess;
        }
    }

    public void acceptStatus() {
        status = Status.accepted;
        for (String ID : itemId) {
            ItemAndCategoryController.getInstance().getItemById(ID).setSale(this.id);
        }
    }

    public void editStatus() {
        status = Status.editingProcess;
    }

    public void addStatus() {
        status = Status.addingProcess;
    }

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


    @Override
    public String toString() {
        String ans = "id: " + getId() + "\n" +
                "Off Percentage: " + getOffPercentage() + "\n" +
                "Status: " + status + "\n" +
                "Start Time: " + getStartTime() + "\n" +
                "End Time: " + getEndTime() + "\n";
        ans += "Items in Sale:\nID             name              price\n";
        for (String itemID : itemId) {
            ans += ItemAndCategoryController.getInstance().getItemById(itemID).toSimpleString();
        }
        return ans;
    }

    public String toSimpleString() {
        return id + "                                              " + offPercentage + "                                          " + endTime;
    }


}