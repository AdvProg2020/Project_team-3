package Model;

import Controller.Controller;

import java.time.LocalDateTime;
import java.util.Date;

public class DiscountCode {

    //private HashMap<String,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private int usageCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //constructor
    public DiscountCode(int discountPercentage,Date endTime){
        this.discountId=Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(),"Discount Codes");
        this.discountPercentage=discountPercentage;

        //this.allUsers=new HashMap<>();
    }

    @Override
    public String toString(){
        String ans = "DiscountCode ID:" + discountId + "   ";
        ans += discountPercentage +"%   ";
        ans += "ends in:" + endTime.toString();
        return ans;
    }

    public String getDiscountId() {
        return discountId;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}