import java.util.*;

public class DiscountCode {

    private HashMap<User,Integer>allUsers;
    private String discountId;
    private int discountPercentage;
    private int startTime;
    private int endTime;
    //constructor
    public DiscountCode(String discountId , int discountPercentage
            , int startTime , int endTime , int allUsages){
        this.discountId=discountId;
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

    public HashMap<User, Integer> getAllUsers() {
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
    public void addUser(User user){
        this.allUsers.put(user , 0);
    }

    public void addUsage(User user){
        if(this.allUsers.containsKey(user)) {
            this.allUsers.put(user, this.allUsers.get(user) + 1);
        }
    }
}