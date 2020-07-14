package Project.Client.Model.Users;

import Server.Controller.Database;
import Server.Controller.UserController;

import java.util.HashMap;

public class Admin extends User {

    public Admin(String username, String password, String name, String lastName, String email, String number) {
        super(username, password, name, lastName, email, number, "Admin");
    }

    public Admin(String username, String password, String name, String lastName, String email, String number, HashMap<String,String> req) {
        super(username, password, name, lastName, email, number, "Admin");
        this.setAllRequests(req);
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

    public void deleteUser(String username) {
        UserController.getInstance().deleteUser(username);
    }


}
