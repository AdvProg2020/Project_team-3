package Project.Client.Model.Users;

import Server.Controller.Database;
import Server.Controller.UserController;

import java.util.HashMap;

public class Admin extends User {




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


}
