
import java.util.*;

public abstract class Request {

    private static ArrayList<Request> allRequest;
    private String requestId;
    private String message;
    private boolean isAccepted;

    public Request(String requestId) {
        this.requestId = requestId;
        this.isAccepted = false;
    }

    public static ArrayList<Request> getAllRequest() {
        return allRequest;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMassage() {
        return message;
    }

    public void setMassage(String massage) {
        this.message = massage;
    }

    public boolean isIs_accepted() {
        return isAccepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.isAccepted = is_accepted;
    }

    public static void addRequest(Request newRequest){
        allRequest.add(newRequest);
    }

    public static void removeRequest(Request removed){
        allRequest.remove(removed);
    }




}
