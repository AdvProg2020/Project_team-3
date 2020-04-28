package Control;

import Model.Logs.BuyLog;
import Model.Logs.SaleLog;
import Model.Requests.Request;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;

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
        for (User user : controller.allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

   /*public void setAdmin(Admin admin) {
        controller.admin = admin;
        niazi be in method nist admin haye mokhtalef ba ham farghi nadaran
    } */

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
            return -1;
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
        for (User user : controller.allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user){
        controller.allUsers.add(user);
    }



    public String registerBuyer(double money, String username, String password, String name, String lastName, String email, String number) {
        if(isThereUserWithUsername(username)){
            return "Error : User exist with this username!";
        }
        Buyer user=new Buyer(money,username,password,name,lastName,email,number);
        addUser(user);
        return "Successful: User registered.";
    }

    public String registerSeller(double money ,String username, String password, String name, String lastName, String email, String number,String companyName) {
        if(isThereUserWithUsername(username)){
            return "Error : User exist with this username!";
        }
        Seller user=new Seller(money,username,password,name,lastName,email,number ,companyName);
        addUser(user);
        String requestID=controller.addId(Request.getIdCount());
        RequestController.getInstance().addUserRequest(requestID ,user);
        return "Success: Your request has been sent to the admin.";
    }

    public String registerAdmin(String username, String password, String name, String lastName, String email, String number){
        Admin.addAdminAccount(username,password,name,lastName,email,number);
        return "Success: Your request has been sent to the admin.";
    }

    public void setAdmin(User user){
        //age khastim ye user beshe admin, ino seda mizanim
        //1- miad etelaate user ro migire va ye admin new mikone va zakhire mikone
        //2- miad user ro hazf mikone az allUsers, chonke be shekle ye admin savesh kardim
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

    public String logout(){
        if(controller.currentOnlineUser==null){
            return "Error: Not logged in!";
        }
        controller.currentOnlineUser=null;
        return "Success: Logged out.";
    }

    public void deleteUser(String username) {
        User user=getUserByUsername(username);
        controller.allUsers.remove(user);
       //dge chi bayad remove she?
    }

    public void editPersonalInfo(String username,String field,String newValue) {

        User user=getUserByUsername(username);
        if(field.equals("name")){
            user.setName(newValue);
        }else if(field.equals("lastName")){
            user.setLastName(newValue);
        }else if(field.equals("Number")){
            user.setNumber(newValue);
        }else if(field.equals("Email")){
            user.setEmail(newValue);
        }else if(field.equals("PassWord")){
            user.setPassword(newValue);
        }

    }

}
