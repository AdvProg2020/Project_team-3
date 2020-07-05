package Project.Model.Requests;

import Project.Controller.ItemAndCategoryController;
import Project.Controller.UserController;

public class ItemEdit extends Request {
    private String itemID;
    private String changedField;
    private String newFieldValue;

    ///overloaded constructor
    public ItemEdit(String requestId, String itemID, String changedField, String newFieldValue) {
        super(requestId);
        this.itemID = itemID;
        String news="Request to edit Item "+itemID+" field \""+changedField+"\" to "+newFieldValue;
        this.setMessage(news);
        this.changedField = changedField;
        this.newFieldValue = newFieldValue;
        setType("ItemEdit");
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemID).getSellerName()).addRequest(getRequestId(),getPendingMessage());
    }

    public String getItemID() {
        return itemID;
    }

    public String getChangedField() {
        return this.changedField;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    @Override
    public String toString() {
        return "id :" + getRequestId() + "   " + "type: " + getType();
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
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemID).getSellerName()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemID).getSellerName()).addRequest(getRequestId(),getDeclineMessage());
    }
}
