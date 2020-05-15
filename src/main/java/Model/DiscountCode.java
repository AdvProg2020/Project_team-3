package Model;

import Controller.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {

    //private HashMap<String,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private double maxDiscount;
    private HashMap<String, Integer> usageCount;
    private int usageCountInt;
    private String startTime;
    private String endTime;

    //constructor
    public DiscountCode(int discountPercentage, LocalDateTime startTime, LocalDateTime endTime, ArrayList<String> validUsers, int usageCountInt,double maxDiscount) {
        this.discountId = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "DiscountCodes");
        this.discountPercentage = discountPercentage;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.maxDiscount = maxDiscount;
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
        ans += "ends in:" + endTime;
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
        this.endTime = endTime.toString();
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.parse(endTime);
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.parse(startTime);
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

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void useDiscountCode(String username){
        int currentCount = usageCount.get(username);
        currentCount--;
        usageCount.replace(username,currentCount);
    }

    public boolean userCanUse(String username){
        return (usageCount.get(username) > 0);
    }
}