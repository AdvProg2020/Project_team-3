
import java.util.*;

public abstract class Request {

    private String requestId;
    private String message;
    private boolean isAccepted;

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

    public boolean isIs_accepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }




}
