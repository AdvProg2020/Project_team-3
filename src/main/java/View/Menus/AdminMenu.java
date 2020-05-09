package View.Menus;

import Control.*;
import Model.Sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

public class AdminMenu extends UserMenu {
    private static AdminMenu adminMenu;
    private int optionCount = 9;

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
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("logout")) {
            logout();
        }
        if(command.equals("offs")){
            View.previousMenu = AdminMenu.getInstance();
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
        }
        else if (command.equals("view personal info")) {
            viewPersonalInfo();
        }
        else if (command.equals("create manager profile")) {
            registerAdmin();
        }
        else if (command.equals("manage all products")) {
            showAllproducts();
        }
        else if(command.equals("products")){
            View.previousMenu = AdminMenu.getInstance();
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        else if (command.equals("manage requests")) {
            showAllRequests();
        }else if(command.equals("create discount code")){
            createDiscountCode();
        }else if(command.equals("view discount code")){
            viewAllDiscountCodes();
        }else if(command.equals("back")){
            back();
        }

        matcher = View.getMatcher("edit discount code (\\S+)", command);
        if (matcher.matches()) {
            editDiscountCode(matcher.group(1));
        }

        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
        }
        matcher = View.getMatcher("delete user (\\S+)", command);
        if (matcher.matches()) {
            deleteUser(matcher.group(1));
        }
        matcher = View.getMatcher("detail request (\\S+)", command);
        if (matcher.matches()) {
            detailRequest(matcher.group(1));
        }
        matcher = View.getMatcher("remove (\\S+)", command);
        if (matcher.matches()) {
            deleteItem(matcher.group(1));
        }

        matcher = View.getMatcher("remove discount code (\\S+)", command);
        if (matcher.matches()) {
            deleteDiscountCode(matcher.group(1));
        }

        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewPersonalInfo(matcher.group(1));
        }

        matcher = View.getMatcher("accept (\\S+)", command);
        if (matcher.matches()) {
            acceptRequestId(matcher.group(1));
        }

        matcher = View.getMatcher("decline (\\S+)", command);
        if (matcher.matches()) {
            declineRequestId(matcher.group(1));
        }

        matcher = View.getMatcher("change type (\\S+) (\\S+)", command);
        if (matcher.matches()) {
            changeType(matcher.group(1), matcher.group(2));
        }
        matcher = View.getMatcher("view discount code (\\S+)", command);
        if (matcher.matches()) {
            viewDiscountCode(matcher.group(1));
        }
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_BLUE+"You are in the Admin menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info"); //done
        System.out.println("edit [field]");
        System.out.println("manage users");  //done
        System.out.println("delete user [username]"); //done
        System.out.println("view [username]");    //done
        System.out.println("change type [username] [role]");  //done but need test
        System.out.println("create manager profile"); //done
        System.out.println("manage all products");    //done  but need test
        System.out.println("remove [product id]");    //done but need test
        System.out.println("create discount code");     //done
        System.out.println("view discount code");         //done but need test
        System.out.println("view discount code [id]");   //done
        System.out.println("edit discount code [id]");
        System.out.println("remove discount code [id]"); //done but need test
        System.out.println("manage requests");           //done but need test
        System.out.println("detail request [request id]");  //done but request info need to be added
        System.out.println("accept [request id]");    //done but need test
        System.out.println("decline [request id]");   //done but need test
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

    private void deleteUser(String username) {
        System.out.println(UserController.getInstance().deleteUser(username));
    }

    private void deleteItem(String id) {
        System.out.println(ItemAndCategoryController.getInstance().deleteItem(id));
    }

    private void deleteDiscountCode(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().deleteDiscountCode(id));
    }

    private void deleteSale(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().deleteSale(id));
    }

    private void printUsers() {
        ArrayList<String> allUserNames = Database.getInstance().printFolderContent("Users");
        printList(allUserNames);
    }

    private void showAllproducts() {
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

    private void addCategory() {

    }

    private void editCategory() {

    }

    private void removeCategory() {

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


    private void viewDiscountCode(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().printDiscount(id));
    }

    private void editDiscountCode(String discountID) {
        if(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountID)==false){
            System.out.println("Error: invalid id");
            return;
        }
        System.out.println("enter -edit date- if you wish to change the date.\nenter -edit offpercent- if you wish to change the offpercentage.");
        String command=View.read.nextLine();
        if(command.equals("edit date")){
            int day=readNumber(32,"please enter new expiration date:");
            int month=readNumber(13,"please enter new expiration month:");
            int year=readNumber(2025,"please enter new expiration year:");
            Date date=new Date(year-1900,month-1,day);
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountID,date));
            return;
        }else if(command.equals("edit offpercent")){
            int percentage=readNumber(101,"please enter new discount percentage:");
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountID,percentage));
            return;
        }
        System.out.println("invalid command. you have been sent to the admin menu");
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

    private void acceptRequestId(String id) {
        System.out.println(RequestController.getInstance().acceptRequest(id));
    }

    private void declineRequestId(String id) {
        System.out.println(RequestController.getInstance().declineRequest(id));
    }

    private void detailRequest(String id) {
        System.out.println(RequestController.getInstance().getRequestDetail(id));
    }

    private void changeType(String username, String type) {
        System.out.println(UserController.getInstance().changeTypeTo(username, type));
    }
}
