package Model.Requests;

public class SaleEdit extends Request {
    private String saleID;
    private String changedFiled;
    private String newFieldValue;
    /////overloaded Constructor
    public SaleEdit(String requestId, String saleID , String changedFiled , String newFieldValue) {
        super(requestId);
        this.saleID = saleID;
        this.changedFiled=changedFiled;
        this.newFieldValue=newFieldValue;
        setType("SaleEdit");
    }

    public String getSaleID() {
        return saleID;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    public String getChangedFieled() {
        return changedFiled;
    }

    @Override
    public String toString(){
        return "id :"+getRequestId()+"\n"+
                "type: "+getType()+"\n"+
                "changed Field: "+getChangedFieled()+"\n"+
                "new Field value:"+getNewFieldValue();
    }
}
