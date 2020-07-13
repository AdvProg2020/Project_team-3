package Project.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientConnector {
    private static ClientConnector clientConnector;
    private ClientConnector(){}
    public static ClientConnector getInstance(){
        if(clientConnector==null) clientConnector=new ClientConnector();
        return clientConnector;
    }


    public String postRequest(URL url,String userToken,String dataToPost){
        try {
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if(!userToken.equals("")){
                connection.setRequestProperty("selection",userToken);
            }
            else if(userToken.equals("")){} /// register request to server!!!!!!
            sendMessageToServer(connection,dataToPost);
            connection.disconnect();
            return getMessageFromServer(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRequest(URL url,String userToken){
        try {
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(!userToken.equals("")){
                connection.setRequestProperty("selection",userToken);
            }
            else if(userToken.equals("")){}
            connection.disconnect();
            return getMessageFromServer(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteRequest(URL url,String userToken){
        HttpURLConnection connection= null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            if(!userToken.equals("")){
                connection.setRequestProperty("selection",userToken);
            }
            else if(userToken.equals("")){}
            connection.disconnect();
            return getMessageFromServer(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMessageFromServer(HttpURLConnection connection){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void sendMessageToServer(HttpURLConnection connection,String dataToPost){
        byte [] input=dataToPost.getBytes();
        try {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input,0,input.length);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }











}
