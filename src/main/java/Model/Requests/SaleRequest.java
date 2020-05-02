package Model.Requests;

import Model.Sale;

public class SaleRequest extends Request {

    private Sale newSale;


    public SaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale=newSale;
        String news="new Model.Sale with Id"+this.newSale.getId()+"is requested for being add to your System!";
        this.setMessage(news);
        setType("SaleRequest");
    }

    public Sale getNewSale() {
        return newSale;
    }

    @Override
    public String toString(){
        return "id: "+getRequestId()+"\n"+"type: "+getType()+"\n"+"Sale time:"+newSale.getStartTime()+"\n";
    }




}
