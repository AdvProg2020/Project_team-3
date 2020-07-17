package Project.Client.Model.Users;

import Server.Controller.Database;
import Server.Model.Users.User;

public class Assistant extends User {
    public Assistant(String username, String password, String name, String lastName, String email, String number) {
        super(username, password, name, lastName, email, number, "Admin");
        Database.getInstance().saveUser(this);
    }

    @Override
    public String getPersonalInfo() {
        String response = "";
        response += "Assistant.\n";
        response += "Name: " + getName() + "\n";
        response += "Surname: " + getLastName() + "\n";
        response += "Email: " + getEmail() + "\n";
        response += "Number: " + getNumber() + "\n";
        return response;
    }
}
