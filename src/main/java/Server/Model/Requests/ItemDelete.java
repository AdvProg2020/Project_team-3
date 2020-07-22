package Server.Model.Requests;

import Server.Controller.ItemAndCategoryController;
import Server.Controller.UserController;
import Server.Model.Item;

public class ItemDelete extends Request {
    private String itemId;
    private Item item;
    public ItemDelete(String requestId , String itemId) {
        super(requestId);
        this.itemId=itemId;
        item=ItemAndCategoryController.getInstance().getItemById(itemId);
        String news="new Request for deleting Item : " + itemId+" !" ;
        setMessage(news);
        if(item.getState().equals("file")==false) {
            setType("ItemDelete");
        }else {
            setType("FileDelete");
        }
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getPendingMessage());
    }

    public String getItemId(){return this.itemId;}
    @Override
    public String toString() {
        return "id :" + getRequestId() + "   " + "type: " + getType();
    }

    @Override
    public String getAcceptedMessage() {
        if(item.getState().equals("file")==false)
        return "id: "+getRequestId()+" state:accepted "+" info:the item has been deleted";
        return "id: "+getRequestId()+" state:accepted "+" info:the file has been deleted";
    }

    @Override
    public String getDeclineMessage() {
        if(item.getState().equals("file")==false)
        return "id: "+getRequestId()+" state:declined "+" info:your request to delete the item has been declined";
        return "id: "+getRequestId()+" state:declined "+" info:your request to delete the file has been declined";
    }

    @Override
    public void accept() {
        if(ItemAndCategoryController.getInstance().getItemById(itemId)==null) return;
     UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getDeclineMessage());
    }
}
