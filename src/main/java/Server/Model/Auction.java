package Server.Model;

import Server.Controller.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Auction {
    private LocalDateTime endTime;

    private String itemID;

    private String highestBidderUsername;
    private double highestBid;
    private String id;
    private HashMap<String,String> chat;

    public Auction(LocalDateTime endTime,String itemID,double startingPrice){
        chat = new HashMap<>();
        this.endTime = endTime;
        this.itemID = itemID;
        this.highestBidderUsername="*none*";
        this.highestBid = startingPrice;
        this.id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Items");
    }

    public void rebid(String username,double value){
        highestBidderUsername = username;
        highestBid = value;
    }

    public String getId() {
        return id;
    }

    public String getItemID() {
        return itemID;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public HashMap<String, String> getChat() {
        return chat;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public String getHighestBidderUsername() {
        return highestBidderUsername;
    }

    public void addChatMessage(String username,String message){
        chat.put(username,message);
    }
}
