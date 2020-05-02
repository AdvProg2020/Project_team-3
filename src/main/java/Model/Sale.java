package Model;

import Control.Controller;

import java.util.ArrayList;

public class Sale {
    private String id;
    private ArrayList<String> itemId = new ArrayList<>();
    private int startTime;
    private int endTime;
    private int offPercentage;
    private enum Status{accepted ,addingProcess ,editingProcess};
    Status status;

    public Sale (int startTime, int endTime, int offPercentage){
        this.startTime = startTime;
        this.endTime = endTime;
        this.offPercentage = offPercentage;
        this.id= Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(),"Sales");
    }

    public void acceptStatus(){
        status=Status.accepted;
    }

    public void editStatus(){
        status=Status.editingProcess;
    }

    public void addStatus(){
        status=Status.addingProcess;
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

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setOffPercentage(int offPercentage) {
        this.offPercentage = offPercentage;
    }

    public String getId() {
        return id;
    }

    public void addItemToSale(String Id){
        itemId.add(id);
    }

    public boolean saleHasItemWithID(String id){
      return false;
    }

    @Override
    public String toString(){
    return "id: "+getId()+"\n"+
    "Off Percentage: "+getOffPercentage()+"\n"+
    "Status: "+status+"\n"+
     "Start Time: "+getStartTime()+"\n"+
     "End Time: "+getEndTime()+"\n";
    }


}