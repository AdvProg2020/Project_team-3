package Model.Requests;

public class ItemEdit extends Request {
    private String itemID;
    private String changedField;
    private String newFieldValue;

    ///overloaded constructor
    public ItemEdit(String requestId, String itemID, String changedField, String newFieldValue) {
        super(requestId);
        this.itemID = itemID;
        String news="Edit Item "+itemID+" field \""+changedField+"\" to "+newFieldValue;
        this.setMessage(news);
        this.changedField = changedField;
        this.newFieldValue = newFieldValue;
        setType("ItemEdit");
    }

    public String getItemID() {
        return itemID;
    }

    public String getChangedField() {
        return this.changedField;
    }

    public String getNewFieldValue() {
        return newFieldValue;
    }

    @Override
    public String toString() {
        return "id :" + getRequestId() + "\n" +
                "type: " + getType() + "\n" +
                "changed Field: " + getChangedField() + "\n" +
                "new Field value:" + getNewFieldValue();
    }


}
