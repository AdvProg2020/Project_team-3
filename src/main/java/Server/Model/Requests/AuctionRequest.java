package Server.Model.Requests;

import Server.Controller.ItemAndCategoryController;
import Server.Controller.UserController;
import Server.Model.Auction;
import Server.Model.Item;

public class AuctionRequest  extends Request {
    Auction auction;
    String sellerName;

    public AuctionRequest(String requestId, Auction newAuction) {
        super(requestId);
        this.sellerName= ItemAndCategoryController.getInstance().getItemById(newAuction.getItemID()).getSellerName();
        this.auction = newAuction;
        String news="Request to add auction \""+newAuction.getItemID()+"\" by "+sellerName;
        this.setMessage(news);
        setType("AuctionRequest");
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getPendingMessage());
    }

    public Auction getAuction() {
        return auction;
    }

    @Override
    public String toString(){
        return "id: " + getRequestId()+"   item id: "+auction.getItemID()+"   "+"type: "+getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your request to start auction for "+auction.getItemID()+" has been accepted";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your request to start auction for \"+auction.getItemID()+\" has been declined";
    }

    @Override
    public void accept() {
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getDeclineMessage());
    }
}
