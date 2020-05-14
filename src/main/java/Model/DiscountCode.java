package Model;

import Controller.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {

    //private HashMap<String,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private HashMap<String, Integer> usageCount;
    private int usageCountInt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //constructor
    public DiscountCode(int discountPercentage, LocalDateTime startTime, LocalDateTime endTime, ArrayList<String> validUsers, int usageCountInt) {
        this.discountId = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Discount Codes");
        this.discountPercentage = discountPercentage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.usageCount = new HashMap<>();
        for (String username : validUsers) {
            usageCount.put(username, usageCountInt);
        }
        this.usageCountInt = usageCountInt;
        //this.allUsers=new HashMap<>();
    }

    @Override
    public String toString() {
        String ans = "DiscountCode ID:" + discountId + "   ";
        ans += discountPercentage + "%   ";
        ans += "ends in:" + endTime.toString();
        return ans;
    }

    public String getDiscountId() {
        return discountId;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean hasUser(String userID) {
        return usageCount.containsKey(userID);
    }

    public int getUsageCountForUser(String username) {
        return usageCount.get(username);
    }

    public void changeUsageCount(int newCount) {
        usageCountInt = newCount;
        int userCount;
        for (String username : usageCount.keySet()) {
            userCount = usageCount.get(username);
            userCount += newCount - usageCountInt;

            usageCount.replace(username, userCount);

        }
    }
}