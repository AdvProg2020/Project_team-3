package Server.Model;

import Server.Controller.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Auction {
    private String endTime;

    private String itemID;

    private String highestBidderUsername;
    private double highestBid;
    private String id;
    private LinkedHashMap<String,String> chat;

    public Auction(LocalDateTime endTime,String itemID,double startingPrice){
        chat = new LinkedHashMap<>();
        this.endTime = endTime.toString();
        this.itemID = itemID;
        this.highestBidderUsername="*none*";
        this.highestBid = startingPrice;
        this.id = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Items");
    }

    public Auction(LocalDateTime endTime,String itemID,String highestBidderUsername,double highestBid,String id, LinkedHashMap<String,String> chat){
        this.endTime = endTime.toString();
        this.itemID = itemID;
        this.highestBid = highestBid;
        this.highestBidderUsername = highestBidderUsername;
        this.id = id;
        this.chat = chat;
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
        return LocalDateTime.parse(endTime);
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
        while(chat.containsKey(username)){
            username += "*";
        }
        chat.put(username,message);
    }
}
