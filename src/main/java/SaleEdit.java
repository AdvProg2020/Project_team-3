public class SaleEdit extends  Request{
    private String saleID;
    private String changedFiled;
    private String newFieldValue;
    /////overloaded Constructor
    public SaleEdit(String requestId, String saleID , String changedFiled , String newFieldValue) {
        super(requestId);
        this.saleID = saleID;
        this.changedFiled=changedFiled;
        this.newFieldValue=newFieldValue;
    }

    public String getSaleID() {
        return saleID;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    public String getChangedFiled() {
        return changedFiled;
    }
}
