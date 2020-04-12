
import java.util.*;

public abstract class Request {

    private String requestId;
    private String massage;
    private boolean is_accepted;

    public Request(String requestId) {
        this.requestId = requestId;
        this.is_accepted = false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public boolean isIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

}
