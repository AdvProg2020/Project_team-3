package Server.Model.Requests;

import Server.Controller.UserController;
import Server.Model.Sale;

public class SaleRequest extends Request {

    private Sale newSale;


    public SaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale=newSale;
        String news="Request to create a new sale of "+newSale.getOffPercentage()+"% by "+newSale.getSellerUsername();
        this.setMessage(news);
        setType("SaleRequest");
        UserController.getInstance().getUserByUsername(newSale.getSellerUsername()).addRequest(getRequestId(),getPendingMessage());
    }

    public Sale getNewSale() {
        return newSale;
    }

    @Override
    public String toString(){
        return "id: "+getRequestId()+"   "+"type: "+getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your request to add sale with id "+newSale.getId()+" has been accepted";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your request to add sale with id "+newSale.getId()+" has been declined";
    }

    @Override
    public void accept() {
        UserController.getInstance().getUserByUsername(newSale.getSellerUsername()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(newSale.getSellerUsername()).addRequest(getRequestId(),getDeclineMessage());
    }
}
