package Model.Requests;

public class SaleEdit extends Request {
    private String saleID;
    private String changedField;
    private String newFieldValue;
    /////overloaded Constructor
    public SaleEdit(String requestId, String saleID , String changedField , String newFieldValue) {
        super(requestId);
        this.saleID = saleID;
        this.changedField=changedField;
        this.setMessage("Request to change "+changedField+" to "+newFieldValue);
        this.newFieldValue=newFieldValue;
        setType("SaleEdit");
    }

    public String getSaleID() {
        return saleID;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    public String getChangedField() {
        return changedField;
    }

    @Override
    public String toString(){
        return "id :"+getRequestId()+"\n"+
                "type: "+getType()+"\n"+
                "changed Field: "+getChangedField()+"\n"+
                "new Field value:"+getNewFieldValue();
    }
}
