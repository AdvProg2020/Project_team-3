package Server.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerRest {

    private static ServerRest server;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServerRest(){}

    public static ServerRest getInstance(){
        if(server==null) server=new ServerRest();
        return server;
    }

    public void setRequest(HttpServletRequest req){
        request=req;
    }

    public  void setResponse(HttpServletResponse resp){
        response=resp;
    }

    public void sendMessageToClient(String message){
        try {
            response.getOutputStream().println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
