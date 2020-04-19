package Control;

import Model.Logs.BuyLog;
import Model.Logs.SaleLog;
import Model.Users.Admin;
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
        return null;
    }

    public ArrayList<SaleLog> getSellerBuyLogs(String username){
        return null;
    }

    public boolean isThereUserWithUsername(String username) {
        for (User user : controller.allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(User user){
        return false;
    }

    public void registerBuyer(double money, String username, String password, String name, String lastName, String email, String number) {

    }

    public void registerSeller(double money ,String username, String password, String name, String lastName, String email, String number) {

    }

    public void deleteUser(String Username) {

    }

    public void editPersonalInfo(String username,String field,String newValue){

    }

    public void Logout() {

    }
}
