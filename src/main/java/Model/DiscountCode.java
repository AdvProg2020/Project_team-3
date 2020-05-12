package Model;

import ControllerTest.Controller;

import java.util.Date;
import java.util.HashMap;

public class DiscountCode {

    private HashMap<String,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private Date startTime;
    private Date endTime;
    //constructor
    public DiscountCode(int discountPercentage,Date endTime){
        this.discountId=Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(),"Discount Codes");
        this.discountPercentage=discountPercentage;
        startTime=new Date();
        this.endTime=endTime;
        this.allUsers=new HashMap<>();
    }
    //getters

    public int getUsage(String userID){
        if(this.allUsers.containsKey(userID)){
            return this.allUsers.get(userID);
        }
        return -1;
    }

    @Override
    public String toString(){
        String ans = "DiscountCode ID:" + discountId + "   ";
        ans += discountPercentage +"%   ";
        ans += "ends in:" + endTime.toString();
        return ans;
    }

    public HashMap<String, Integer> getAllUsers() {
        return allUsers;
    }

    public String getDiscountId() {
        return discountId;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }


    //setters
    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    //add user
    private void addUser(String userID){
        this.allUsers.put(userID , 0);
    }

    public void addUsage(String  userID){
        if(this.allUsers.containsKey(userID)) {
            this.allUsers.put(userID, this.allUsers.get(userID) + 1);
        }else{
            addUser(userID);
        }
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }




}