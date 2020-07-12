package Project.Model.Users;

import Project.Controller.Database;
import Project.Controller.UserController;

import java.util.HashMap;

public class Admin extends User {

    public Admin(String username, String password, String name, String lastName, String email, String number) {
        super(username, password, name, lastName, email, number, "Admin");
        Database.getInstance().saveUser(this);
    }

    public Admin(String username, String password, String name, String lastName, String email, String number, HashMap<String,String> req) {
        super(username, password, name, lastName, email, number, "Admin");
        this.setAllRequests(req);
        Database.getInstance().saveUser(this);
    }

    @Override
    public String getPersonalInfo() {
        String response = "";
        response += "Admin.\n";
        response += "Name: " + getName() + "\n";
        response += "Surname: " + getLastName() + "\n";
        response += "Email: " + getEmail() + "\n";
        response += "Number: " + getNumber() + "\n";
        return response;
    }

    public static String addAdminAccount(String username, String password, String name, String lastName, String email, String number) {
        if (UserController.getInstance().isThereUserWithUsername(username)) {
            return "Error: user exists with this username";
        }
            Database.getInstance().saveUser(new Admin(username, password, name, lastName, email, number));
            return "Successful: User registered";
    }

    public void deleteUser(String username) {
        UserController.getInstance().deleteUser(username);
    }


}
