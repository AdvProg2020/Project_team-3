package Model.Requests;

import Model.Item;

public class ItemRequest  extends Request {
    Item newItem;
    public ItemRequest(String requestId, Item newItem) {
        super(requestId);
        this.newItem = newItem;
        String news="new Model.Item with Id:"+newItem.getId()+"is requested for being added in your System";
        this.setMessage(news);
        setType("ItemRequest");
    }

    public Item getNewItem() {
        return newItem;
    }

    @Override
    public String toString(){
        return "id: "+getRequestId()+"\n"+"type: "+getType()+"\n"+"Item Brand:"+newItem.getBrand()+"\n";
    }


}
