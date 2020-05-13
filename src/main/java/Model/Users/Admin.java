package Model.Users;

import Controller.Database;
import Controller.UserController;

import java.io.IOException;

public class Admin extends User {

    public Admin(String username, String password, String name, String lastName, String email, String number) {
        super(username, password, name, lastName, email, number, "Admin");
        try {
            Database.getInstance().saveUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPersonalInfo() {
        String response = "";
        response += "You are an admin.\n";
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
        try {
            Database.getInstance().saveUser(new Admin(username, password, name, lastName, email, number));
            return "Successful: User registered";
        } catch (IOException e) {
            return "Error";
        }
    }

    public void deleteUser(String username) {
        UserController.getInstance().deleteUser(username);
    }

    public String showCategory(String categoryName) {
        return "salam";
    }

    public void addCategory(String name) {

    }

    public void changeIsAccepted() {
        //dar inja modir request ha ra accept ya decline mikonad! ArrayList <>allRequest
    }


}
