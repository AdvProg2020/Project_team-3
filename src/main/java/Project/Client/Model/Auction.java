package Project.Client.Model;

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

    public Auction(LocalDateTime endTime, String itemID, double startingPrice){
        chat = new LinkedHashMap<>();
        this.endTime = endTime.toString();
        this.itemID = itemID;
        this.highestBidderUsername="*none*";
        this.highestBid = startingPrice;
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

}
