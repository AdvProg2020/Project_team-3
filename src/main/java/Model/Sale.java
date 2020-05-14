package Model;

import Controller.Controller;
import Controller.ItemAndCategoryController;

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

    ;
    Status status;

    public Sale(LocalDateTime startTime, LocalDateTime endTime, int offPercentage,String sellerUsername,ArrayList<String> saleItems) {
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.offPercentage = offPercentage;
        this.id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Sales");
        this.sellerUsername = sellerUsername;
        itemId = saleItems;
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

    public boolean saleHasItemWithID(String id) {
        return false;
    }

    public String getSellerUsername() {
        return sellerUsername;
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
        String ans= "id: " + getId() + "\n" +
                "Off Percentage: " + getOffPercentage() + "\n" +
                "Status: " + status + "\n" +
                "Start Time: " + getStartTime() + "\n" +
                "End Time: " + getEndTime() + "\n";
        ans += "Items in Sale:\nID             name              price\n";
        for(String itemID:itemId){
            ans+=ItemAndCategoryController.getInstance().getItemById(itemID).toSimpleString();
        }
        return ans;
    }

    public String toSimpleString(){
        return id + "           " + offPercentage + "      " + endTime;
    }


}