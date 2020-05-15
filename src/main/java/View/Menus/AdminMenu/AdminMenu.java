package View.Menus.AdminMenu;

import Controller.Database;
import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import Controller.UserController;
import Model.Sale;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.ShopAndDiscountMenu.DiscountsMenu;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminMenu extends UserMenu {
    private static AdminMenu adminMenu;

    private AdminMenu() {
    }

    public static AdminMenu getInstance() {
        if (adminMenu == null)
            adminMenu = new AdminMenu();
        return adminMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_BLUE + "You are in the Admin menu." + View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("logout")) {
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        if (command.equals("offs")) {
            DiscountsMenu.getInstance().setPreviousMenu(AdminMenu.getInstance());
            View.setCurrentMenu(DiscountsMenu.getInstance());
            return;
        } else if (command.equals("help")) {
            help();
            return;
        } else if (command.equals("back")) {
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        } else if (command.equals("manage users")) {
            printUsers();
            View.setCurrentMenu(AdminManageUsersMenu.getInstance());
            return;
        } else if (command.equals("view personal info")) {
            viewPersonalInfo();
            return;
        } else if (command.equals("manage all products")) {
            showAllProducts();
            return;
        } else if (command.equals("manage categories")) {
            showAllCategories();
            View.setCurrentMenu(AdminManageCategoriesMenu.getInstance());
            return;
        } else if (command.equals("products")) {
            ShopMenu.getInstance().setPreviousMenu(AdminMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
            return;
        } else if (command.equals("manage requests")) {
            showAllRequests();
            View.setCurrentMenu(AdminManageRequestsMenu.getInstance());
            return;
        } else if (command.equals("create discount code")) {
            createDiscountCode();
            return;
        } else if (command.equals("manage discount codes")) {
            viewAllDiscountCodes();
            View.setCurrentMenu(AdminManageDiscountCodesMenu.getInstance());
            return;
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
            return;
        }
        matcher = View.getMatcher("remove (\\S+)", command);
        if (matcher.matches()) {
            deleteItem(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_BLUE + "You are in the Admin menu.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println("view personal info"); //done
        System.out.println("edit [field]");
        System.out.println("manage users");  //done
        System.out.println("change type [username] [role]");  //done but need test
        System.out.println("manage all products");    //done  but need test
        System.out.println("remove [product id]");    //done but need test
        System.out.println("create discount code");     //done
        System.out.println("manage discount codes");         //done but need test
        System.out.println("manage requests");           //done but need test
        System.out.println("manage categories");
        System.out.println("logout");                //done
    }

    private void AddAdminAccount() {
        LoginRegisterMenu.getInstance().registerAdmin();
    }

    private void showAllSales() {
        ArrayList<Sale> allSales = SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        for (Sale sale : allSales) {
            System.out.println(sale);
        }
    }


    private void deleteItem(String id) {
        System.out.println(ItemAndCategoryController.getInstance().deleteItem(id));
    }


    private void deleteSale(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().deleteSale(id));
    }

    private void printUsers() {
        ArrayList<String> allUserNames = Database.getInstance().printFolderContent("Users");
        printList(allUserNames);
    }

    private void showAllProducts() {
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(allItems);
    }

    private void showAllRequests() {
        ArrayList<String> allRequests = Database.getInstance().printFolderContent("Requests");
        printList(allRequests);
    }

    private void viewAllDiscountCodes() {
        ArrayList<String> allDiscountCodes = Database.getInstance().printFolderContent("DiscountCodes");
        printList(allDiscountCodes);
    }

    private void showAllCategories() {
        //set current menu to category menu
    }

    void createDiscountCode() {
        int percentage = readNumber(101, "Please enter discount percentage:");
        int usageCount = readNumber(1000,"Enter the number of times this code can be used (max:1000):");
        double maxDiscount = readNumber(-1D,"Enter the maximum limit for this discount code:");
        LocalDateTime startTime = getDate("Enter a valid day as the starting time in the following format: dd/MM/yyyy HH:mm");
        LocalDateTime endTime = getDate("Enter a valid day as the end time in the following format: dd/MM/yyyy HH:mm");

        System.out.println("What users do you want to add? Type done to finish.");
        String username;
        ArrayList<String> addedUsers = new ArrayList<>();
        while(true){
            username = View.getRead().nextLine();
            if(username.equals("done")) break;
            if(!UserController.getInstance().isThereUserWithUsername(username) || !UserController.getInstance().getUserType(username).equals("Buyer")){
                System.out.println(View.ANSI_RED+"Error:invalid user!"+View.ANSI_RESET);
                continue;
            }
            addedUsers.add(username);
        }

        System.out.println(SaleAndDiscountCodeController.getInstance().addDiscountCode(percentage,startTime,endTime,addedUsers,usageCount,maxDiscount));
    }

}
