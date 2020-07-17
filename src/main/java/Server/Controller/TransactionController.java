package Server.Controller;

import Server.Model.Users.Admin;

import java.io.*;
import java.net.Socket;

public class TransactionController {
    private static TransactionController transactionController;
    private TransactionController(){
        try {
            socket=new Socket("localHost",8000);
            dataInputStream= new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            setMainBankAccountId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static TransactionController getInstance(){
        if(transactionController==null) transactionController=new TransactionController();
        return transactionController;
    }
    private String mainBankAccountId="";
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public String getMainBankAccountId() {
        return mainBankAccountId;
    }

    public void setMainBankAccountId() {
        Admin admin=(Admin) UserController.getInstance().getUserByUsername("admin");
        if(!admin.getBankAccountId().equals("")){
            mainBankAccountId=admin.getBankAccountId();
        }
        else if(admin.getBankAccountId().equals("")) {
            String string=addAccountToBank(admin.getName(),admin.getLastName(),"reza",admin.getPassword(),admin.getPassword());
            mainBankAccountId=string;
            admin.setBankAccountId(mainBankAccountId);
        }
    }

    public String addAccountToBank(String firstName,String lastName,String username,String password , String repeatPassword){
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
