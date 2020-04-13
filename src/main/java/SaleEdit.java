public class SaleEdit extends  Request{
    private Sale sale;
    private String changedFiled;
    private String newFieldValue;
    /////overloaded Constructor
    public SaleEdit(String requestId, Sale sale , String changedFiled , String newFieldValue) {
        super(requestId);
        this.sale = sale;
        this.changedFiled=changedFiled;
        this.newFieldValue=newFieldValue;
    }

    public Sale getSale() {
        return sale;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    public String getChangedFiled() {
        return changedFiled;
    }
}
