package Server.Controller;

import Server.Model.Auction;


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
        return null;
    }

    public ArrayList<Auction> getAllAuctions(){
        return null;
    }
}
