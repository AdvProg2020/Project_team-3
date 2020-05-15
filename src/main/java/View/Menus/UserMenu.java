package View.Menus;

import Controller.Controller;
import Controller.UserController;

public abstract class UserMenu extends Menu {

    @Override
    public void run() {

    }

    @Override
    public void help() {

    }

    public void viewPersonalInfo(String username) {
        System.out.println(UserController.getInstance().viewPersonalInfo(username));
    }

    public void viewPersonalInfo() {
        System.out.print(UserController.getInstance().viewPersonalInfo(Controller.getInstance().getCurrentOnlineUser().getUsername()));
    }

    public void editPersonalInfo(String field) {
        String username = UserController.getInstance().getCurrentOnlineUser().getUsername();
        if (field.equalsIgnoreCase("Name")) {
            editName(username);
        } else if (field.equalsIgnoreCase("Surname")) {
            editLastName(username);
        } else if (field.equalsIgnoreCase("Email")) {
            editEmail(username);
        } else if (field.equalsIgnoreCase("Number")) {
            editNumber(username);
        } else if (field.equalsIgnoreCase("Company") && UserController.getInstance().returnUserType(username).equals("Seller")) {
            editCompanyName(username);
        } else if (field.equalsIgnoreCase("Password")) {
            editPassword(username);
        } else {
            System.out.println(View.ANSI_RED + "You cannot edit that field or it does not exist." + View.ANSI_RESET);
        }
    }

    public void editPassword(String username) {
        System.out.print("Enter your new password:");
        String name = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username, "Password", name);
        System.out.println("Password edited successfully.");
    }

    public void editName(String username) {
        System.out.print("Enter your new name:");
        String name = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username, "Name", name);
        System.out.println("Name edited successfully.");
    }

    public void editLastName(String username) {
        System.out.print("Enter your new surname:");
        String surname = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username, "Surname", surname);
        System.out.println("Surname edited successfully.");
    }

    public void editEmail(String username) {
        String email = enterEmail();
        UserController.getInstance().editPersonalInfo(username, "Email", email);
        System.out.println("Email edited successfully.");
    }

    public void editNumber(String username) {
        String number = enterNumber();
        UserController.getInstance().editPersonalInfo(username, "Number", number);
        System.out.println("Number edited successfully.");
    }

    public void editCompanyName(String username) {
        System.out.print("Enter your new company name:");
        String name = View.read.nextLine();
        UserController.getInstance().editPersonalInfo(username, "CompanyName", name);
        System.out.println("Company name edited successfully.");
    }



}
