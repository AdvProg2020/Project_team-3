public class SaleEdit extends  Request{
    private Sale sale;
    /////overloaded Constructor
    public SaleEdit(String requestId, Sale sale) {
        super(requestId);
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }

}
