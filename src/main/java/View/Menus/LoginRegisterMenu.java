package View.Menus;

import Controller.Controller;
import Controller.UserController;
import View.Menus.AdminMenu.AdminMenu;
import View.Menus.SellerMenu.SellerMenu;
import View.Menus.ShopAndDiscountMenu.ShopMenu;

public class LoginRegisterMenu extends Menu {
    private static LoginRegisterMenu loginRegisterMenu;
    private String intendedMenu;

    private LoginRegisterMenu() {
    }

    public static LoginRegisterMenu getInstance() {
        if (loginRegisterMenu == null)
            loginRegisterMenu = new LoginRegisterMenu();
        return loginRegisterMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_RED + "Log in or Register." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.startsWith("create account ")) {
            register(command);
        } else if (command.startsWith("login ")) {
            login(command);
        } else if (command.equals("help")) {
            help();
        } else if (command.equals("back")) {
            View.setCurrentMenu(getPreviousMenu());
        } else if (command.equals("logout")) {
            logout();
        } else {
            System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
        }
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_RED + "Log in or Register.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println("create account [type] [username]");
        System.out.println(View.ANSI_WHITE + "where type is buyer/seller/admin and username cannot contain spaces." + View.ANSI_RESET);
        System.out.println("login [username]");
        System.out.println("back");
    }

    public void login(String command) {
        if (Controller.getInstance().isLogin()) {
            System.out.println("Error: logout first");
            return;
        }
        if (command.split(" ").length != 2) {
            System.out.println(View.ANSI_RED + "Invalid username." + View.ANSI_RESET);
            return;
        }
        System.out.print("Enter your password:");
        String password = View.read.nextLine();
        String username = command.split(" ")[1];

        String ans = UserController.getInstance().login(username, password);
        System.out.println(ans);
        /*if (ans.startsWith("Success")) {
            if (UserController.getInstance().returnUserType(username).equals("Admin")) {
                View.setCurrentMenu(AdminMenu.getInstance());
            }
            if (UserController.getInstance().returnUserType(username).equals("Buyer")) {
                View.setCurrentMenu(BuyerMenu.getInstance());
            }
            if (UserController.getInstance().returnUserType(username).equals("Seller")) {
                View.setCurrentMenu(SellerMenu.getInstance());
            }
        }*/

        if (ans.contains("Success")) {
            goToIntendedMenu();
        } else {
            View.setCurrentMenu(loginRegisterMenu);
        }
    }

    public boolean registerAdmin() {
        String username = enterUsername();
        System.out.print("Enter your password:");
        String password = View.read.nextLine();
        System.out.print("Enter your first name:");
        String firstName = View.read.nextLine();
        System.out.print("Enter your surname:");
        String lastName = View.read.nextLine();
        String email = enterEmail();
        String number = enterNumber();
        String ans = UserController.getInstance().registerAdmin(username, password, firstName, lastName, email, number);
        System.out.println(ans);
        return ans.contains("Success");
    }

    public boolean register(String command) {
        if (command.split(" ").length != 4) {
            System.out.println(View.ANSI_RED + "Invalid username/account type." + View.ANSI_RESET);
            return false;
        }
        String key = command.split(" ")[2];
        if (!key.equals("buyer") && !key.equals("seller") && !key.equals("admin")) {
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
            return ans.contains("Success");
        } else if (key.equals("seller")) {
            System.out.print("Enter your company name:");
            String companyName = View.read.nextLine();
            ans = UserController.getInstance().registerSeller(money, username, password, firstName, lastName, email, number, companyName);
            System.out.println(ans);
            return ans.contains("Success");
        } else {
            ans = UserController.getInstance().registerFirstAdmin(username, password, firstName, lastName, email, number);
            System.out.println(ans);
            return ans.contains("Succ");
            /*System.out.println("You cant make an admin account.");
            return false;*/
        }


    }

    public void logout() {
        System.out.println(UserController.getInstance().logout());
        View.setCurrentMenu(this.getPreviousMenu());
    }

    public void setIntendedMenu(String intendedMenu) {
        this.intendedMenu = intendedMenu;
    }

    private void goToIntendedMenu() {
        if (intendedMenu.equals("UserMenu")) {
            if ( UserController.getInstance().getUserType().equals("Admin")) {
                View.setCurrentMenu(AdminMenu.getInstance());
            } else if ( UserController.getInstance().getUserType().equals("Seller")) {
                View.setCurrentMenu(SellerMenu.getInstance());
            } else {
                View.setCurrentMenu(BuyerMenu.getInstance());
            }
        } else if (intendedMenu.equals("PurchaseMenu")) {
            if (!( UserController.getInstance().getUserType().equals("Buyer"))){
                System.out.println("you must be a Buyer to buy items");
                return;
            }
            PurchaseMenu.getInstance().setPreviousMenu(CartMenu.getInstance());
            View.setCurrentMenu(PurchaseMenu.getInstance());
        }
        else if(intendedMenu.equals("MainMenu")){
            MainMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            View.setCurrentMenu(MainMenu.getInstance());
        }
        else if(intendedMenu.equals("ShopMenu")){
            ShopMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        else if(intendedMenu.equals("ItemMenu")){
            View.setCurrentMenu(ItemMenu.getInstance());
        }
        else if(intendedMenu.equals("CartMenu")){
            View.setCurrentMenu(CartMenu.getInstance());
        }

    }
}
