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

    public abstract void execute(String command);

    public boolean login(String command) {
        /*System.out.print("Enter your username:");
        String username = View.read.nextLine();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();

        String ans = UserController.getInstance().login(username, password);
        System.out.println(ans);
        return ans.startsWith("Success");*/

        if(command.split(" ").length != 2){
            System.out.println(View.ANSI_RED + "Invalid username." + View.ANSI_RESET);
            return false;
        }
        System.out.print("Enter your password:");
        String password = View.read.nextLine();
        String username = command.split(" ")[1];

        String ans = UserController.getInstance().login(username, password);
        System.out.println(ans);
        if(ans.startsWith("Success")){
            if(UserController.getInstance().returnUserType(username).equals("Admin")){
                View.setCurrentMenu(AdminMenu.getInstance());
            }
            if(UserController.getInstance().returnUserType(username).equals("Buyer")){
                View.setCurrentMenu(BuyerMenu.getInstance());
            }
            if(UserController.getInstance().returnUserType(username).equals("Seller")){
                View.setCurrentMenu(SellerMenu.getInstance());
            }
        }
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

    public boolean register(String command) {
        if(command.split(" ").length != 4){
            System.out.println(View.ANSI_RED + "Invalid username/account type." + View.ANSI_RESET);
            return false;
        }
        String key = command.split(" ")[2];
        if(!key.equals("buyer") && !key.equals("seller") && !key.equals("admin")){
            System.out.println(View.ANSI_RED + "Invalid account type." + View.ANSI_RESET);
            return false;
        }

        String username = command.split(" ")[3];



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

        if (key.equals("buyer")) {
            ans = UserController.getInstance().registerBuyer(money, username, password, firstName, lastName, email, number);
            System.out.println(ans);
            return ans.startsWith("Success");
        } else if(key.equals("seller")) {
            System.out.print("Enter your company name:");
            String companyName = View.read.nextLine();
            ans = UserController.getInstance().registerSeller(money, username, password, firstName, lastName, email, number, companyName);
            System.out.println(ans);
            return ans.startsWith("Success");
        }else{
            /*
            ans = UserController.getInstance().registerAdmin(username,password,firstName,lastName,email,number);
            System.out.println(ans);
            return ans.startsWith("Succ");*/
            System.out.println("You cant make a admin account.");
            return false;
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

     String enterEmail() {
        System.out.print("Enter a valid email:");
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

     String enterNumber() {
        System.out.print("Enter a valid phone number:");
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
        View.setCurrentMenu(MainMenu.getInstance());
    }

    public void printList(ArrayList<String> stringList){
        int count=1;
        for (String s : stringList) {
            System.out.println(count+"-"+s);
            count++;
        }
        if(count==1){
            System.out.println("empty");
        }
    }

    public void viewProduct(String command){
        if(command.split(" ").length!=2){
            System.out.println(View.ANSI_RED+"Invalid product ID."+View.ANSI_RESET);
            return;
        }
        String id = command.split(" ")[1];
        ItemMenu.getInstance().setItemID(id);
        View.setCurrentMenu(ItemMenu.getInstance());
    }


    private void back() {

    }


}

