package Project.Client.Model.Users;

import java.util.HashMap;

public class Assistant extends User {
    public Assistant(String username, String password, String name, String lastName, String email, String number) {
        super(username, password, name, lastName, email, number, "Assistant");
        //Database.getInstance().saveUser(this);
    }

    public Assistant(String username, String password, String name, String lastName, String email, String number, HashMap<String,String> req) {
        super(username, password, name, lastName, email, number, "Assistant");
        this.setAllRequests(req);
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
