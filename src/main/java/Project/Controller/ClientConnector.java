package Project.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            byte [] input=dataToPost.getBytes();
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(input,0,input.length);
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            connection.disconnect();
            return response.toString();//// server reaction!!!
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
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            connection.disconnect();
            return response.toString(); /// data that is posted from server!!!
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
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            connection.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }











}
