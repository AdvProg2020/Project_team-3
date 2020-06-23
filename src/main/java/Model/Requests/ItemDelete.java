package Model.Requests;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Users.User;

public class ItemDelete extends Request {
    private String itemId;

    public ItemDelete(String requestId , String itemId) {
        super(requestId);
        this.itemId=itemId;
        String news="new Request for deleting Item : " + itemId+" !" ;
        setMessage(news);
        setType("ItemDelete");
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getPendingMessage());
    }

    public String getItemId(){return this.itemId;}
    @Override
    public String toString() {
        return "id :" + getRequestId() + "   " + "type: " + getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:the item has been deleted";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your request to delete the item has been declined";
    }

    @Override
    public void accept() {
     UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(itemId).getSellerName()).addRequest(getRequestId(),getDeclineMessage());
    }
}
