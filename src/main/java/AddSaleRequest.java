
public class AddSaleRequest extends Request {

    private Sale newSale;


    public AddSaleRequest(String requestId, Sale newSale) {
        super(requestId);
        this.newSale = newSale;
        String news="new Sale with Id"+newSale.getId()+"is requested for being add to your System!";
        this.setMassage(news);
        addRequest(this);
    }

    public Sale getNewSale() {
        return newSale;
    }

}
