
public class SaleRequest extends Request {

    private Sale newSale;


    public SaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale = newSale;
        String news="new Sale with Id"+newSale.getId()+"is requested for being add to your System!";
        this.setMessage(news);
        addRequest(this);
    }

    public Sale getNewSale() {
        return newSale;
    }

}
