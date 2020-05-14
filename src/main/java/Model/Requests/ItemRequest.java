package Model.Requests;

import Model.Item;

public class ItemRequest  extends Request {
    Item newItem;
    public ItemRequest(String requestId, Item newItem) {
        super(requestId);
        this.newItem = newItem;
        String news="new Item with information "+newItem.toSimpleString()+" is requested for being added in your System";
        this.setMessage(news);
        setType("ItemRequest");
    }

    public Item getNewItem() {
        return new Item(newItem);
    }

    @Override
    public String toString(){
        return "item Name: "+newItem.getName()+"\n"+"type: "+getType()+"\n"+"Item Brand:"+newItem.getBrand()+"\n";
    }


}
