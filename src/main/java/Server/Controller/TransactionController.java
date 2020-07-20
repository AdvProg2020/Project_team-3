package Server.Controller;

import Server.Model.Users.Admin;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class TransactionController {
    private static TransactionController transactionController;
    private final String mainBankAccountId="10001";
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private int wagePercent;
    private int minimumMoney;

    public static TransactionController getInstance(){
        if(transactionController==null) transactionController=new TransactionController();
        return transactionController;
    }
    private TransactionController(){
        try {
            socket=new Socket("localHost",8080);
            dataInputStream= new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getMainBankAccountId(){
        return mainBankAccountId;
    }


    public int getWagePercent(){return wagePercent;}
    public int getMinimumMoney(){return minimumMoney;}
    public void setNumbers(int percent , int minimum){
        System.out.println(percent+" "+minimum+" "+"new new");
        wagePercent=percent;
        minimumMoney=minimum;
        String value=String.valueOf(wagePercent)+" "+String.valueOf(minimumMoney);
        Gson gson=new Gson();
        String content=gson.toJson(value);
        String path="Resource"+File.separator+"WagePercent.gson";
        File file=new File(path);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMainBankAccountId() {
        String path="Resource"+File.separator+"WagePercent.gson";
        File file=new File(path);
        Gson gson=new Gson();
        if(file.exists()==false){
            try {
                file.createNewFile();
                Admin admin=(Admin) UserController.getInstance().getUserByUsername("admin");
                String s=addAccountToBank(admin.getName(),admin.getLastName(),admin.getUsername(),admin.getPassword(),admin.getPassword());
                System.out.println("the main account id is : "+ s);
                String string="5 1000";
                String gsonToSave=gson.toJson(string);
                FileWriter writer = new FileWriter(file);
                writer.write(gson.toJson(gsonToSave));
                writer.close();
                wagePercent=5;
                minimumMoney=1000;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(file.exists()==true){
            try {
                String content=new String(Files.readAllBytes(file.toPath()));
                String string=gson.fromJson(content,String.class);
                wagePercent=Integer.parseInt(string.substring(1,string.indexOf(" ")));
                minimumMoney=Integer.parseInt(string.substring(string.indexOf(" ")+1,string.length()-1));
                System.out.println(minimumMoney);
                System.out.println(wagePercent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String addAccountToBank(String firstName, String lastName, String username, String password , String repeatPassword){
        String toBeSend="create_account "+firstName+" "+lastName+" "+username+" "+password+" "+repeatPassword;
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getBankToken(String username,String password){
        String toBeSend="get_token "+username+" "+password;
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getReceiptID(String token, String type, String money, String sourceId, String desId, String description){
        StringBuilder sb=new StringBuilder("create_receipt "+token+" "+type+" "+money+" "+sourceId+" "+desId);
        if(!description.equals("")) sb.append(description);
        String toBeSend=sb.toString();
        String received="";
        try {
            System.out.println("to be send "+toBeSend+"*");
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getTransaction(String type,String token){
        String toBeSend="get_transactions "+token+" "+type;
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String payReceipt(String receiptId){
        String toBeSend="pay "+receiptId;
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getBalance(String token){
        String toBeSend="get_balance "+token;
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String exitBank(){
        String toBeSend="exit";
        String received="";
        try {
            dataOutputStream.writeUTF(toBeSend);
            dataOutputStream.flush();
            received=dataInputStream.readUTF();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


















}
