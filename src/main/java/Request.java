
import java.util.*;

public abstract class Request {

    private static ArrayList<Request> allRequests;
    private String requestId;
    private String message;
    private boolean isAccepted;

    public Request(String requestId) {
        this.requestId = requestId;
        this.isAccepted = false;
    }

    public static ArrayList<Request> getAllRequest() {
        return allRequests;
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

    public static void addRequest(Request newRequest){
        allRequests.add(newRequest);
    }

    public static boolean removeRequest(Request removed){
        if(allRequests.contains(removed)) {
            allRequests.remove(removed);
            return true;
        }
        return false;
    }




}
