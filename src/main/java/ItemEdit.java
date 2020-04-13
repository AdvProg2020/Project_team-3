public class ItemEdit extends Request {
    private Item Item;
    private String changedField;
    private String newFieldValue;
    ///overloaded constructor
    public ItemEdit(String requestId, Item Item , String changedField , String newFieldValue) {
        super(requestId);
        this.Item = Item;
        this.changedField=changedField;
        this.newFieldValue=newFieldValue;
    }

    public Item getNewItem() {
        return Item;
    }
    public String getChangedField(){
        return this.changedField;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

}
