package Model;

import Controller.Controller;
import Controller.ItemAndCategoryController;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale {
    private String id;
    private ArrayList<String> itemId = new ArrayList<>();
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int offPercentage;

    private enum Status {accepted, addingProcess, editingProcess}

    ;
    Status status;

    public Sale(LocalDateTime startTime, LocalDateTime endTime, int offPercentage) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.offPercentage = offPercentage;
        this.id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Sales");
    }

    public void acceptStatus() {
        status = Status.accepted;
    }

    public void editStatus() {
        status = Status.editingProcess;
    }

    public void addStatus() {
        status = Status.addingProcess;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getOffPercentage() {
        return offPercentage;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }

    public String getId() {
        return id;
    }

    public void addItemToSale(String Id) {
        itemId.add(id);
    }

    public boolean saleHasItemWithID(String id) {
        return false;
    }

    public String itemsInfo(){
    String string="";
    Item item;
        for (String id : itemId) {
            item= ItemAndCategoryController.getInstance().getItemById(id);
            string+="item id: "+id+"price before sale: "+item.getPrice()+"  price after sale: "+item.getPriceWithSale()+"\n";
        }
        return string;
    }

    @Override
    public String toString() {
        return "id: " + getId() + "\n" +
                "Off Percentage: " + getOffPercentage() + "\n" +
                "Status: " + status + "\n" +
                "Start Time: " + getStartTime() + "\n" +
                "End Time: " + getEndTime() + "\n";
    }


}