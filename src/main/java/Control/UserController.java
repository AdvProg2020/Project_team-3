package Control;

import Model.Logs.BuyLog;
import Model.Logs.SaleLog;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import View.Menus.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class UserController {
    Controller controller = Controller.getInstance();

    private static UserController userController;

    private UserController(){}

    public static UserController getInstance(){
        if(userController==null)
            userController=new UserController();
        return userController;
    }

    public User getUserByUsername(String username) {
        String path="Resource"+File.separator+"Users";
        String name=username+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return null;
        }
        Gson gson=new Gson();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
            if(content.contains("\"type\": \"Admin\"")){
            return gson.fromJson(content,Admin.class);}
            if(content.contains("\"type\": \"Buyer\"")){
                return gson.fromJson(content,Buyer.class);}
            if(content.contains("\"type\": \"Seller\"")){
                return gson.fromJson(content,Seller.class);}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   public User getCurrentOnlineUser() {
        return controller.currentOnlineUser;
    }

    public double currentOnlineUserBalance(){
        if(getCurrentOnlineUser() instanceof Buyer){
            return ((Buyer) getCurrentOnlineUser()).getMoney();
        }
        if(getCurrentOnlineUser() instanceof Seller) {
            return ((Seller) getCurrentOnlineUser()).getMoney();
        }
            return -111;
    }

    public ArrayList<BuyLog> getBuyerBuyLogs(String username){
        Buyer buyer= (Buyer) getCurrentOnlineUser();
       return buyer.getBuyLogs();
    }

    public ArrayList<SaleLog> getSellerBuyLogs(String username){
        Seller seller= (Seller) getCurrentOnlineUser();
        return seller.getSellLogs();
    }

    public boolean isThereUserWithUsername(String username) {
        String path="Resource"+File.separator+"Users";
        String name=username+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
           return true;
    }

    public String registerBuyer(double money, String username, String password, String name, String lastName, String email, String number)  {
        if(isThereUserWithUsername(username)){
            return "Error : User exist with this username!";
        }
        Buyer user=new Buyer(money,username,password,name,lastName,email,number);
        try{
            Database.getInstance().saveUser(user);} catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful: User registered.";
    }

    public String registerSeller(double money ,String username, String password, String name, String lastName, String email, String number,String companyName) {
        if(isThereUserWithUsername(username)){
            return "Error : User exist with this username!";
        }
        Seller user=new Seller(money,username,password,name,lastName,email,number ,companyName);
        try{
            Database.getInstance().saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
       String requestID=controller.getAlphaNumericString(controller.getIdSize(),"Requests");

        RequestController.getInstance().addUserRequest(requestID ,user);
        return "Success: Your request has been sent to the admin.";
    }

    public String registerAdmin(String username, String password, String name, String lastName, String email, String number){
        return  Admin.addAdminAccount(username,password,name,lastName,email,number);
    }

    public String login(String username,String password){
        if(!isThereUserWithUsername(username)){
            return "Error: No user exists with this username!";
        }
        User user=getUserByUsername(username);
        if(!user.doesPasswordMatch(password)){
            return "Error: Incorrect password!";
        }
        controller.currentOnlineUser=user;
        Controller.getInstance().setCurrentOnlineUser(user);
        return "Success: Login successful.";
    }

    public double validateMoney(String money){
        double moneyDouble = -1;
        try{
            moneyDouble = Double.parseDouble(money);
        }catch (Exception e){
            return -1;
        }
        return moneyDouble;
    }

    public boolean isValidEmail(String email){
        return Controller.getMatcher(email,"^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$").matches();
    }

    public boolean isValidPhoneNumber(String number) {
        return Controller.getMatcher(number,"\\d\\d\\d\\d\\d(\\d+)$").matches();
    }

    public String returnUserType(String username){
       return getUserByUsername(username).getType();
    }
    public String logout(){
        if(controller.currentOnlineUser==null){
            return View.ANSI_RED+ "Error: Not logged in!"+View.ANSI_RESET;
        }
        controller.currentOnlineUser=null;
        return "Success: Logged out.";
    }

    public String deleteUser(String username) {
        if(username.equals(getCurrentOnlineUser().getUsername())){
            return "Error: you cant remove your own account";
        }
        User user=getUserByUsername(username);
        if(user==null){
            return "Error: user doesnt exist";
        }
        Database.getInstance().deleteUser(user);
            return "Successful:";
    }

    public void editPersonalInfo(String username,String field,String newValue) {

        User user=getUserByUsername(username);
        if(field.equals("Name")){
            user.setName(newValue);
        }else if(field.equals("Surname")){
            user.setLastName(newValue);
        }else if(field.equals("Number")){
            user.setNumber(newValue);
        }else if(field.equals("Email")){
            user.setEmail(newValue);
        }else if(field.equals("Password")){
            user.setPassword(newValue);
        }else if(field.equals("CompanyName")){
            ((Seller)user).setCompanyName(newValue);
        }
        try{
            Database.getInstance().saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String viewPersonalInfo(String username){
        User user = getUserByUsername(username);
        if(user==null){
            return "Error: user doesnt exist";
        }
        return user.getPersonalInfo();
    }

    public ArrayList<User> getAllUserFromDataBase(){
        String path="Resource"+File.separator+"Discount Codes";
        File file=new File(path);
        File [] allFiles=file.listFiles();
        String fileContent = null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User>allUser=new ArrayList<>();
            for(File file1:allFiles){
                try {
                    fileContent=new String(Files.readAllBytes(file1.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(fileContent.contains("\"type\": \"Admin\"")){
                    allUser.add(gson.fromJson(fileContent, Admin.class));}
                if(fileContent.contains("\"type\": \"Seller\"")){
                    allUser.add(gson.fromJson(fileContent, Seller.class));}
                if(fileContent.contains("\"type\": \"Buyer\"")){
                    allUser.add(gson.fromJson(fileContent, Buyer.class));}
            }
        return  allUser;
    }

    public String changeTypeTo(String username,String type){
        return getUserByUsername(username).changeTypeTo(type);
    }

}
