package Model.Requests;

import Model.Sale;

public class SaleRequest extends Request {

    private Sale newSale;


    public SaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale=newSale;
        String news="Request to create a new sale of "+newSale.getOffPercentage()+"% by "+newSale.getSellerUsername();
        this.setMessage(news);
        setType("SaleRequest");
    }

    public Sale getNewSale() {
        return newSale;
    }

    @Override
    public String toString(){
        return "id: "+getRequestId()+"   "+"type: "+getType();
    }

}
