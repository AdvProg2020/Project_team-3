package Model.Requests;

public abstract class Request {

    private static String idCount="00000000";
    private String requestId;
    private String message;
    private boolean isAccepted;
    private String type;
    public Request(String requestId) {
        this.requestId = requestId;
        this.isAccepted = false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static String getIdCount(){return idCount;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
