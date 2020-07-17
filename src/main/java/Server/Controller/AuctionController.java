package Server.Controller;

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
}
