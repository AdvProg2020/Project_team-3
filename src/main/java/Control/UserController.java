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

    public void setAdmin(Admin admin) {
        controller.admin = admin;
    }

    public User getCurrentOnlineUser() {
        return controller.currentOnlineUser;
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
            return "Error :user exist with this username";
        }
        User newUSer=new Buyer(money,username,password,name,lastName,email,number);
        String requestID=controller.addId(Request.getIdCount());
        RequestController.getInstance().addUserRequest(requestID ,newUSer);
            return "your request for signing up was sent to admin!";
    }

    public String registerSeller(double money ,String username, String password, String name, String lastName, String email, String number,String companyName) {
        if(isThereUserWithUsername(username)){
            return "Error :user exist with this username";
        }
        User newUSer=new Buyer(money,username,password,name,lastName,email,number);
        String requestID=controller.addId(Request.getIdCount());
        RequestController.getInstance().addUserRequest(requestID ,newUSer);
        return "your request for signing up was sent to admin!";
    }

    public String login(String username,String password){
        User user=getUserByUsername(username);
        if(user==null){
            return "Error: user doesnt exist";
        }
        if(user.doesPasswordMatch(password)==false){
            return "Error: password doesnt match";
        }
            controller.currentOnlineUser=user;
            return "Successful: login successful";
    }

    public void deleteUser(String username) {
        User user=getUserByUsername(username);
        controller.allUsers.remove(user);
       //dge chi bayad remove she?
    }

    public void editPersonalInfo(String username,String field,String newValue) {
        if (isThereUserWithUsername(username) == false){
            System.out.println("the user is not registered!");
            return;
        }
        User user=getUserByUserName(username);

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

    public void Logout() {
      controller.currentOnlineUser=null;
    }

    public User getUserByUserName(String userName){
        for(User user:controller.allUsers){
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }


}
