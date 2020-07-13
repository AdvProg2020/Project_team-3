package Project.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {
   ////inja bayad vasl beshe v payam begire badesh befreste bara proccess ke on payam ro anjam bede v javab ro befreste bara server v srrvera befreste client

    private static Server server;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private Server(){}

    public static Server getInstance(){
        if(server==null) server=new Server();
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
    public String getMessageFromClient(){
        BufferedReader bufferedReader= null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder responseString = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                responseString.append(responseLine.trim());
            }
            return  responseString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
