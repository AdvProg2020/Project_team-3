public class AddItemRequest  extends Request {
    Item newItem;

    public AddItemRequest(String requestId, Item newItem) {
        super(requestId);
        this.newItem = newItem;
        String news="new Item with Id:"+newItem.getId()+"is requested for being added in your System";
        this.setMassage(news);
        addRequest(this);
    }

    public Item getNewItem() {
        return newItem;
    }
}
