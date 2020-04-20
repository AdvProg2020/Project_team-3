package Model.Users;

import Control.UserController;

public class Admin extends User {

    public Admin(String username, String password, String name, String lastName, String email, String type, String number) {
        super(username, password, name, lastName, email, type, number);
        UserController.getInstance().addUser(this);
    }

    public static String addAdminAccount(String username, String password, String name, String lastName, String email, String type, String number) {
        if(UserController.getInstance().isThereUserWithUsername(username)==true){
            return "Error: user exists with this username";
        }
        UserController.getInstance().addUser(new Admin(username,password,name,lastName,email,type,number));
            return "Successful: User registered";
    }

    public void deleteUser(String username) {
      UserController.getInstance().deleteUser(username);
    }

    public String showCategory(String categoryName) {
           return "salam";
    }

     public void addCategory(String name) {

    }

    public void changeIsAccepted(){
        //dar inja modir request ha ra accept ya decline mikonad! ArrayList <>allRequest
    }


}
