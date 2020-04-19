package Control;

import Model.Logs.BuyLog;
import Model.Logs.SaleLog;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;

import java.util.ArrayList;

public class UserController {
    Controller controller = Controller.getInstance();
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
        addUser(new Buyer(money,username,password,name,lastName,email,number));
            return "Successful: user registered";
    }

    public String registerSeller(double money ,String username, String password, String name, String lastName, String email, String number,String companyName) {
        if(isThereUserWithUsername(username)){
            return "Error :user exist with this username";
        }
        addUser(new Seller(money,username,password,name,lastName,email,number,companyName));
        return "Successful: user registered";
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

    public void editPersonalInfo(String username,String field,String newValue){

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
