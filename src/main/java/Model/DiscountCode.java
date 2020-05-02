package Model;

import Control.Controller;
import Model.Users.User;

import java.util.HashMap;

public class DiscountCode {

    private HashMap<String,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private int startTime;
    private int endTime;
    //constructor
    public DiscountCode( int discountPercentage, int startTime , int endTime , int allUsages){
        this.discountId=Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(),"Discount Codes");
        this.discountPercentage=discountPercentage;
        this.startTime=startTime;
        this.endTime=endTime;
        this.allUsers=new HashMap<>();
    }
    //getters

    public int getUsage(User user){
        if(this.allUsers.containsKey(user)){
            return this.allUsers.get(user);
        }
        return -1;
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

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    //setters
    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    //add user
    public void addUser(String userID){
        this.allUsers.put(userID , 0);
    }

    public void addUsage(String  userID){
        if(this.allUsers.containsKey(userID)) {
            this.allUsers.put(userID, this.allUsers.get(userID) + 1);
        }
    }

    public String toString(){
        return "ID: "+discountId+"\n"+
                "discountPercentage: "+discountPercentage+"\n"+
                "Start Time: "+startTime+"\n"+
                "End Time: "+endTime+"\n";
    }



}