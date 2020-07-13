package Server.Model.Requests;

import Server.Controller.UserController;
import Server.Model.Item;

public class ItemRequest  extends Request {
    Item newItem;
    String sellerName;
    public ItemRequest(String requestId, Item newItem) {
        super(requestId);
        this.sellerName=newItem.getSellerName();
        this.newItem = newItem;
        String news="Request to add Item \""+newItem.getName()+"\" at a price of "+newItem.getPrice();
        this.setMessage(news);
        setType("ItemRequest");
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getPendingMessage());
    }

    public Item getNewItem() {
        return new Item(newItem);
    }

    @Override
    public String toString(){
        return "id: " + getRequestId()+"   item Name: "+newItem.getName()+"   "+"type: "+getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your request to add item with name "+newItem.getName()+" has been accepted";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your request to add item with name "+newItem.getName()+" has been declined";
    }

    @Override
    public void accept() {
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getDeclineMessage());
    }
}
