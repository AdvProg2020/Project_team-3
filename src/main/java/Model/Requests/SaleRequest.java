package Model.Requests;

import Model.Sale;

public class SaleRequest extends Request {

    private Sale newSale;


    public SaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale=newSale;
        String news="new Model.Sale with Id"+this.newSale.getId()+"is requested for being add to your System!";
        this.setMessage(news);
    }

    public Sale getNewSale() {
        return newSale;
    }
}