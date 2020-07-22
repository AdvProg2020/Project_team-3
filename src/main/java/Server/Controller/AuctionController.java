package Server.Controller;

import Server.Model.Auction;
import Server.Model.DiscountCode;
import Server.Model.Item;
import Server.Model.Users.Buyer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AuctionController {
    private static AuctionController auctionController;
    private AuctionController(){

    }
    public static AuctionController getInstance(){
        if (auctionController == null){
            auctionController = new AuctionController();
        }
        return auctionController;
    }

    public String addAuction(int duration,double startPrice,String itemID){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        if(item.getInStock()<=0){
            return "Error: item not in stock";
        }
        LocalDateTime endTime = LocalDateTime.now().plusHours(duration);
        Auction auction = new Auction(endTime,itemID,startPrice);
        String requestID = Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(), "Requests");
        RequestController.getInstance().addAuctionRequest(requestID,auction);
        return "Successful: Your request to start this auction has been sent to the admins.";
    }

    public Auction getAuctionByID(String auctionID){
        ArrayList<Auction> viableOptions = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Auctions WHERE id='"+auctionID+"'");
            while(rs.next())
            {
                String id = rs.getString(1);
                LinkedHashMap<String,String> chat = gson.fromJson(rs.getString(6),new TypeToken<LinkedHashMap<String,String>>(){}.getType());
                String itemID = rs.getString(2);
                LocalDateTime end = LocalDateTime.parse(rs.getString(3));
                String username = rs.getString(4);
                double bid = Double.parseDouble(rs.getString(5));
                viableOptions.add(new Auction(end,itemID,username,bid,id,chat));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(viableOptions.isEmpty()) return null;
        return viableOptions.get(0);
    }

    public void addChatToAuction(String id,String username,String message){
        Auction auction = getAuctionByID(id);
        auction.addChatMessage(username,message);
        Database.getInstance().saveAuction(auction);
    }

    public ArrayList<String> getAllAuctionIDs(){
        ArrayList<String> allAucs = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Auctions");
            while(rs.next())
            {
                allAucs.add(rs.getString(1));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return allAucs;
    }

    public ArrayList<Auction> getAllAuctions(){
        ArrayList<Auction> ans = new ArrayList<>();
        for(String id:getAllAuctionIDs()){
            ans.add(getAuctionByID(id));
        }
        return ans;
    }

    public String bidOnAuction(String auctionID,double bid,String newBidder){
        Auction auction = getAuctionByID(auctionID);
        if(bid <= auction.getHighestBid()){
            return "Your bid is too low.";
        }
        Buyer newGuy = (Buyer)UserController.getInstance().getUserByUsername(newBidder);
        if(newGuy.getMoney() - bid < TransactionController.getInstance().getMinimumMoney()){
            return "insufficient money!";
        }
        if(!auction.getHighestBidderUsername().equals("*none*")){
            Buyer buyer = (Buyer)UserController.getInstance().getUserByUsername(auction.getHighestBidderUsername());
            buyer.setMoney(buyer.getMoney()+auction.getHighestBid());
            Database.getInstance().saveUser(buyer);
        }
        auction.rebid(newBidder,bid);
        Database.getInstance().saveAuction(auction);
        newGuy.setMoney(newGuy.getMoney() - bid);
        return "Bid successful";
    }

    public void updateAuctionsTime(){
        for(Auction auction:getAllAuctions()){
            if(auction.getEndTime().isBefore(LocalDateTime.now())){
                endAuction(auction);
            }
        }
    }

    private void endAuction(Auction auction){
        // in miad pool ro az highest bidder kam mikone mirize hesabe seller

        //...

        Database.getInstance().deleteAuction(auction);
    }
}
