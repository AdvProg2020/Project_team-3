package Model.Requests;

public class ItemDelete extends Request {
    private String itemId;

    public ItemDelete(String requestId , String itemId) {
        super(requestId);
        this.itemId=itemId;
        String news="new Request for deleting Item : " + itemId+" !" ;
        setMessage(news);
        setType("ItemDelete");
    }

    public String getItemId(){return this.itemId;}
    @Override
    public String toString() {
        return "id :" + getRequestId() + "   " + "type: " + getType();
    }
}
