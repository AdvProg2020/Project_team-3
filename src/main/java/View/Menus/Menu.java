package View.Menus;

import Control.Controller;
import Control.UserController;

public abstract class Menu {

    public int optionCount;

    public int getOptionCount() {
        return optionCount;
    }

    public abstract void help();

    public abstract void show();

    public abstract void execute(String command);


    public void login() {
        System.out.print("Enter your username:");
        String username = View.read.nextLine();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();

        System.out.println(UserController.getInstance().login(username, password));
    }


    public void register() {
        System.out.println("Register a buyer or a seller?  [B/S] ");
        String key = View.read.nextLine();
        if (!key.equals("B") && !key.equals("S")) {
            System.out.println("No account type exists with the name " + key + ". Try again.");
            register();
            return;
        }

        String username = enterUsername();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();
        System.out.print("Enter your first name:");
        String firstName = View.read.nextLine();
        System.out.print("Enter your surname:");
        String lastName = View.read.nextLine();
        String email = enterEmail();
        double money = enterMoney();
        String number = enterNumber();


        if (key.equals("B")) {
            System.out.println(UserController.getInstance().registerBuyer(money, username, password, firstName, lastName, email, number));
        } else {
            System.out.print("Enter your company name:");
            String companyName = View.read.nextLine();
            System.out.println(UserController.getInstance().registerSeller(money, username, password, firstName, lastName, email, number, companyName));
        }


    }

    private String enterUsername() {
        System.out.print("Enter your username:");
        String username = View.read.nextLine();
        if (UserController.getInstance().isThereUserWithUsername(username)) {
            System.out.println("A user exists with this username. Try another.");
            return enterUsername();
        }
        return username;
    }

    private String enterEmail() {
        System.out.print("Enter your email:");
        String email = View.read.nextLine();
        if (!UserController.getInstance().isValidEmail(email)) {
            System.out.println("Invalid email. Try again.");
            return enterEmail();
        }
        return email;
    }

    private Double enterMoney() {
        System.out.print("Enter your starting money:");
        String money = View.read.nextLine();
        if (UserController.getInstance().validateMoney(money) == -1) {
            System.out.println("Invalid money. Try again.");
            return enterMoney();
        }
        return UserController.getInstance().validateMoney(money);
    }

    private String enterNumber() {
        System.out.print("Enter your phone number:");
        String number = View.read.nextLine();
        if (!UserController.getInstance().isValidPhoneNumber(number)) {
            System.out.println("Invalid phone number. Try again.");
            return enterNumber();
        }
        return number;
    }

    public void logout() {
        UserController.getInstance().logout();
    }

    private void exit() {
        Controller.getInstance().saveGson();
    }

    private void back() {

    }


}

