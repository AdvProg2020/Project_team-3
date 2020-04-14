public class ItemEdit extends Request {
    private String itemID;
    private String changedField;
    private String newFieldValue;
    ///overloaded constructor
    public ItemEdit(String requestId, String itemID , String changedField , String newFieldValue) {
        super(requestId);
        this.itemID = itemID;
        this.changedField=changedField;
        this.newFieldValue=newFieldValue;
    }

    public String getItemID() {
        return itemID;
    }
    public String getChangedField(){
        return this.changedField;
    }
    public String getNewFieldValue() {
        return newFieldValue;
    }

}
