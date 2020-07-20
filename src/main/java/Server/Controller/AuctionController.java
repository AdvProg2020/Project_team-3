package Server.Controller;

import Server.Model.Auction;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public Auction getAuctionByID(String auctionID){
        return null;
    }

    public ArrayList<String> getAllAuctionDescription(){
        return null;
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
