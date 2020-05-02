package View.Menus;

import Control.UserController;

import java.util.ArrayList;

public abstract class Menu {

    public int optionCount;

    public int getOptionCount() {
        return optionCount;
    }

    public abstract void help();

    public abstract void run();

    public boolean login() {
        System.out.print("Enter your username:");
        String username = View.read.nextLine();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();

        String ans = UserController.getInstance().login(username, password);
        System.out.println(ans);
        return ans.startsWith("Success");
    }

    public boolean registerAdmin(){
        String username = enterUsername();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();
        System.out.print("Enter your first name:");
        String firstName = View.read.nextLine();
        System.out.print("Enter your surname:");
        String lastName = View.read.nextLine();
        String email = enterEmail();
        String number = enterNumber();
        String ans=UserController.getInstance().registerAdmin(username,password,number,lastName,email,number);
        System.out.println(ans);
        return ans.startsWith("Success");
    }

    public boolean register() {
        System.out.println("Register a buyer or a seller?  [B/S] ");
        String key = View.read.nextLine();
        if (!key.equals("B") && !key.equals("S")) {
            System.out.println("No account type exists with the name " + key + ". Try again.");

            return register();
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

        String ans;

        if (key.equals("B")) {
            ans = UserController.getInstance().registerBuyer(money, username, password, firstName, lastName, email, number);
            System.out.println(ans);
            return ans.startsWith("Success");
        } else {
            System.out.print("Enter your company name:");
            String companyName = View.read.nextLine();
            ans = UserController.getInstance().registerSeller(money, username, password, firstName, lastName, email, number, companyName);
            System.out.println(ans);
            return ans.startsWith("Success");
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

    public String readName(String message){
        System.out.println(message);
        return View.read.nextLine();
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

    public int readNumber(int limit,String message){   //if limit is -1 there is no limit for int number
    if(!message.isEmpty())
      System.out.println(message);
    String number=View.read.nextLine();
        int num=Integer.parseInt(number);
        if(((num>limit)||(num<0))&&(limit!=-1)){
            System.out.println("Invalid please try again");
            return readNumber(limit,"");
        }
        return num;
    }

    public double readDoubleNumber(String message){
        System.out.println(message);
        return View.read.nextDouble();
    }

    public void logout() {
        System.out.println(UserController.getInstance().logout());
    }

    public void printList(ArrayList<String> stringList){
        int count=1;
        for (String s : stringList) {
            System.out.println(count+"-"+s);
        }
    }

    private void back() {

    }


}

