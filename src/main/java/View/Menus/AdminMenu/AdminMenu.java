package View.Menus.AdminMenu;

import ControllerTest.Database;
import ControllerTest.ItemAndCategoryController;
import ControllerTest.SaleAndDiscountCodeController;
import Model.Sale;
import View.Menus.*;
import View.Menus.ShopMenu.ShopMenu;

import java.util.ArrayList;
import java.util.Date;
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
            logout();
        }
        if(command.equals("offs")){
            View.setPreviousMenu(AdminMenu.getInstance());
            View.setCurrentMenu(DiscountsMenu.getInstance());
        }
        else if (command.equals("help")) {
            help();
        }
        else if (command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
        }
        else if (command.equals("manage users")) {
            printUsers();
            View.setCurrentMenu(AdminManageUsersMenu.getInstance());
        }
        else if (command.equals("view personal info")) {
            viewPersonalInfo();
        }
        else if (command.equals("manage all products")) {
            showAllProducts();
        }
        else if(command.equals("manage categories")){
            showAllCategories();
            View.setCurrentMenu(AdminManageCategoriesMenu.getInstance());
        }
        else if(command.equals("products")){
            View.setPreviousMenu(AdminMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        else if (command.equals("manage requests")) {
            showAllRequests();
            View.setCurrentMenu(AdminManageRequestsMenu.getInstance());
        }else if(command.equals("create discount code")){
            createDiscountCode();
        }else if(command.equals("view discount codes")){
            viewAllDiscountCodes();
            View.setCurrentMenu(AdminManageDiscountCodesMenu.getInstance());
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
        }
        matcher = View.getMatcher("remove (\\S+)", command);
        if (matcher.matches()) {
            deleteItem(matcher.group(1));
        }
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_BLUE+"You are in the Admin menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info"); //done
        System.out.println("edit [field]");
        System.out.println("manage users");  //done
        //System.out.println("delete user [username]"); //done
        //System.out.println("view [username]");    //done
        System.out.println("change type [username] [role]");  //done but need test
        //System.out.println("create manager profile"); //done
        System.out.println("manage all products");    //done  but need test
        System.out.println("remove [product id]");    //done but need test
        System.out.println("create discount code");     //done
        System.out.println("view discount codes");         //done but need test
        //System.out.println("view discount code [id]");   //done
        //System.out.println("edit discount code [id]");
        //System.out.println("remove discount code [id]"); //done but need test
        System.out.println("manage requests");           //done but need test
        //System.out.println("detail request [request id]");  //done but request info need to be added
        //System.out.println("accept [request id]");    //done but need test
        //System.out.println("decline [request id]");   //done but need test
        System.out.println("manage categories");
        //System.out.println("edit category [category name]");
        //System.out.println("add [category name]");
        //System.out.println("remove [category name]");
        System.out.println("logout");                //done
    }

    private void AddAdminAccount() {
        registerAdmin();
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
        ArrayList<String> allDiscountCodes = Database.getInstance().printFolderContent("Discount Codes");
        printList(allDiscountCodes);
    }

    private void showAllCategories() {
        //set current menu to category menu
    }

    private void removeProducts() {

    }

    private void createDiscountCode() {
    int percentage=readNumber(101,"please enter discount percentage:");
    int day=readNumber(32,"please enter expiration date:");
    int month=readNumber(13,"please enter expiration month:");
    int year=readNumber(2025,"please enter expiration year:");
    Date date=new Date(year-1900,month-1,day);
    System.out.println(SaleAndDiscountCodeController.getInstance().addDiscountCode(percentage,date));
    }




    private void manageAllProducts() {
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(allItems);
    }

    private void manageRequests() {
        ArrayList<String> allRequests = Database.getInstance().printFolderContent("Requests");
        printList(allRequests);
    }

    private void requestDetails() {

    }

    private void manageCategories() {

    }


    private void removeProduct() {

    }




}
