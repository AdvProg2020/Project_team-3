package Model;

import Control.Controller;

import java.util.ArrayList;

public class Sale {
    private String id;
    private ArrayList<String> itemId = new ArrayList<>();
    private int startTime;
    private int endTime;
    private int offPercentage;
    private String status;
    private static String idCount="00000000";
    public Sale (int startTime, int endTime, int offPercentage){
        this.startTime = startTime;
        this.endTime = endTime;
        this.offPercentage = offPercentage;
        id=idCount;
        idCount= Controller.getInstance().addId(idCount);
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