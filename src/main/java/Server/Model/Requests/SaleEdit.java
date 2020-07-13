package Server.Model.Requests;

import Server.Controller.SaleAndDiscountCodeController;
import Server.Controller.UserController;

public class SaleEdit extends Request {
    private String saleID;
    private String changedField;
    private String newFieldValue;

    public SaleEdit(String requestId, String saleID , String changedField , String newFieldValue) {
        super(requestId);
        this.saleID = saleID;
        this.changedField=changedField;
        this.setMessage("Request to change "+changedField+" to "+newFieldValue);
        this.newFieldValue=newFieldValue;
        setType("SaleEdit");
        if( UserController.getInstance().getUserByUsername(SaleAndDiscountCodeController.getInstance().getSaleById(saleID).getSellerUsername())==null) System.out.println("salamkkkk");
        UserController.getInstance().getUserByUsername(SaleAndDiscountCodeController.getInstance().getSaleById(saleID).getSellerUsername()).addRequest(getRequestId(),getPendingMessage());
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
        return "id :"+getRequestId()+"   "+ "type: "+getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your request to edit field "+changedField+" to "+newFieldValue+" has been accepted";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your request to edit field "+changedField+" to "+newFieldValue+" has been declined";
    }

    @Override
    public void accept() {
        UserController.getInstance().getUserByUsername(SaleAndDiscountCodeController.getInstance().getSaleById(saleID).getSellerUsername()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(SaleAndDiscountCodeController.getInstance().getSaleById(saleID).getSellerUsername()).addRequest(getRequestId(),getDeclineMessage());
    }
}
