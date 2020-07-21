package Server.Model.Requests;

import Server.Controller.UserController;
import Server.Model.FileItem;
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
        if(newItem.getState().equals("file")==false) {
            setType("ItemRequest");
        }else {
            setType("FileRequest");
        }
        UserController.getInstance().getUserByUsername(sellerName).addRequest(getRequestId(),getPendingMessage());
    }

    public Item getNewItem() {
        return new Item(newItem);
    }

    @Override
    public String toString(){
        if(newItem.getState().equals("file")==false)
        return "id: " + getRequestId()+"   item Name: "+newItem.getName()+"   "+"type: "+getType();
        return "id: " + getRequestId()+"   file Name: "+newItem.getName()+"   "+"type: "+getType();
    }

    @Override
    public String getAcceptedMessage() {
        if(newItem.getState().equals("file")==false)
        return "id: "+getRequestId()+" state:accepted "+" info:your request to add item with name "+newItem.getName()+" has been accepted";
        return "id: "+getRequestId()+" state:accepted "+" info:your request to add file with name "+newItem.getName()+" has been accepted";
    }

    @Override
    public String getDeclineMessage() {
        if(newItem.getState().equals("file")==false)
        return "id: "+getRequestId()+" state:declined "+" info:your request to add item with name "+newItem.getName()+" has been declined";
        return "id: "+getRequestId()+" state:declined "+" info:your request to add file with name "+newItem.getName()+" has been declined";
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
