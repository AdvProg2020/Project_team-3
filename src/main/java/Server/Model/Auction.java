package Server.Model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Auction {
    private LocalDateTime endTime;

    private String itemID;

    private String highestBidderUsername;
    private double highestBid;

    private HashMap<String,String> chat;

    public Auction(LocalDateTime endTime,String itemID){
        chat = new HashMap<>();
        this.endTime = endTime;
        this.itemID = itemID;
    }

    public void rebid(String username,double value){
        highestBidderUsername = username;
        highestBid = value;
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
}
