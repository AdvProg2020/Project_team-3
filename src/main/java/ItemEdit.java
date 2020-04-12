public class ItemEdit extends Request {
    private Item Item;
    ///overloaded constructor
    public ItemEdit(String requestId, Item Item) {
        super(requestId);
        this.Item = Item;
    }

    public Item getNewItem() {
        return Item;
    }
}
