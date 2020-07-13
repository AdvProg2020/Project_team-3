package Project.Client.CLI;

import Server.Controller.ItemAndCategoryController;
import Server.Controller.UserController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Menu {
    private Menu previousMenu;

    protected abstract void help();

    protected abstract void run();

    protected abstract void execute(String command);

    public void setPreviousMenu(Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    protected Menu getPreviousMenu() {
        if (previousMenu == null)
            return MainMenu.getInstance();
        return previousMenu;
    }

    protected String enterUsername() {
        System.out.print("Enter your username:");
        String username = View.read.nextLine();
        if (UserController.getInstance().isThereUserWithUsername(username)) {
            System.out.println("A user exists with this username. Try another.");
            return enterUsername();
        }
        return username;
    }

    protected String enterEmail() {
        System.out.print("Enter a valid email:");
        String email = View.read.nextLine();
        if (!UserController.getInstance().isValidEmail(email)) {
            System.out.println("Invalid email. Try again.");
            return enterEmail();
        }
        return email;
    }

    protected Double enterMoney() {
        System.out.print("Enter your starting money:");
        String money = View.read.nextLine();
        if (UserController.getInstance().validateMoney(money) == -1) {
            System.out.println("Invalid money. Try again.");
            return enterMoney();
        }
        return UserController.getInstance().validateMoney(money);
    }

    protected String readName(String message) {
        System.out.println(message);
        return View.read.nextLine();
    }

    protected String enterNumber() {
        System.out.print("Enter a valid phone number:");
        String number = View.read.nextLine();
        if (!UserController.getInstance().isValidPhoneNumber(number)) {
            System.out.println("Invalid phone number. Try again.");
            return enterNumber();
        }
        return number;
    }

    protected int readNumber(int limit, String message) {   //if limit is -1 there is no limit for int number
        System.out.println(message);
        String number = View.read.nextLine();
        try {
            int num = Integer.parseInt(number);
            if (((num > limit) || (num < 0)) && (limit != -1)) {
                System.out.println("Invalid please try again");
                return readNumber(limit, "");
            }
            return num;
        } catch (Exception expection) {
            System.out.println("invalid number");
            return readNumber(limit, message);
        }
    }

    protected double readNumber(double limit, String message) { //balaii amma double
        System.out.println(message);
        String number = View.read.nextLine();
        try {
            double num = Double.parseDouble(number);
            if (((num > limit) || (num < 0)) && (limit != -1)) {
                System.out.println("Invalid please try again");
                return readNumber(limit, "");
            }
            return num;
        } catch (Exception e) {
            System.out.println("invalid number");
            return readNumber(limit, message);
        }
    }

    protected double readDoubleNumber(String message) {
        System.out.println(message);
        return View.read.nextDouble();
    }

    protected void printList(ArrayList<String> stringList) {
        int count = 1;
        if(stringList==null){
            System.out.println("empty");
        }
        for (String s : stringList) {
            System.out.println(count + "-" + s);
            count++;
        }
        if (count == 1) {
            System.out.println("empty");
        }
    }

    protected void viewItem(String id) {
        String ans = ItemAndCategoryController.getInstance().viewItem(id);
        if (ans.contains("Error")) {
            System.out.println(ans);
            return;
        }
        ItemAndCategoryController.getInstance().addView(id);
        ItemMenu.getInstance().setPreviousMenu(View.getCurrentMenu());
        View.setCurrentMenu(ItemMenu.getInstance());
        ItemMenu.getInstance().setItemID(id);
    }

    protected LocalDateTime getDate(String message){
        LocalDateTime date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println(message);
        String dateString = View.getRead().nextLine();
        try{
            date = LocalDateTime.parse(dateString,dateTimeFormatter);
            return date;
        }catch (Exception e){
            System.out.println(View.ANSI_RED+"Invalid date. Try again."+View.ANSI_RESET);
            date = getDate(message);
        }
        return date;
    }

}

