package View.Menus;

import Control.*;
import Model.Sale;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminMenu extends UserMenu {
    private static AdminMenu adminMenu;
    private int optionCount = 9;
    private AdminMenu(){ }

    public static AdminMenu getInstance(){
        if(adminMenu==null)
            adminMenu = new AdminMenu();
        return adminMenu;
    }

    @Override
    public void run(){
    System.out.println("you are in the admin menu");
    String command = View.read.nextLine();
    execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if(command.equals("logout")){
            logout();
        }
     if(command.equals("help")){
         help();
     }
     if(command.equals("manage users")){
         printUsers();
     }
     if(command.equals("view personal info")){
         viewPersonalInfo();
     }
     if(command.equals("create manager profile")){
         registerAdmin();
     }
     if(command.equals("manage all prducts")){
         showAllproducts();
     }
     if(command.equals("manage requests")){
         showAllRequests();
     }
     matcher=View.getMatcher("delete user (\\S+)",command);
     if(matcher.matches()){
         deleteUser(matcher.group(1));
     }
        matcher=View.getMatcher("remove (\\S+)",command);
        if(matcher.matches()){
            deleteItem(matcher.group(1));
        }

        matcher=View.getMatcher("remove discount code (\\S+)",command);
        if(matcher.matches()){
            deleteDiscountCode(matcher.group(1));
        }

        matcher=View.getMatcher("view (\\S+)",command);
        if(matcher.matches()){
        viewPersonalInfo(matcher.group(1));
        }

        matcher=View.getMatcher("accept (\\S+)",command);
        if(matcher.matches()){
            acceptRequestId(matcher.group(1));
        }

        matcher=View.getMatcher("decline (\\S+)",command);
        if(matcher.matches()){
            declineRequestId(matcher.group(1));
        }
    }

    @Override
    public void help(){
        System.out.println("view personal info"); //done
        System.out.println("edit [field]");
        System.out.println("manage users");  //done
        System.out.println("delete user [username]"); //done
        System.out.println("view [username]");    //done
        System.out.println("change type [username] [role]");
        System.out.println("create manager profile"); //done
        System.out.println("manage all products");    //done  but need test
        System.out.println("remove [product id]");    //done but need test
        System.out.println("create discount code");
        System.out.println("view discount code");         //done but need test
        System.out.println("view discount code [id]");
        System.out.println("edit discount code [id]");
        System.out.println("remove discount code [id]"); //done but need test
        System.out.println("manage requests");           //done but need test
        System.out.println("details requests [request id]");
        System.out.println("accept [request id]");
        System.out.println("decline [request id]");
        System.out.println("logout");
    }


    private void seeAllRequests(){
        ///set current menu to request menu
    }

    private void AddAdminAccount(){
       registerAdmin();
    }

    private void showAllSales(){
        ArrayList<Sale> allSales= SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
            for(Sale sale:allSales){
                System.out.println(sale);
            }
    }



    private void deleteUser(String username){
        System.out.println(UserController.getInstance().deleteUser(username));
    }

    private void deleteItem(String id){
       System.out.println(ItemAndCategoryController.getInstance().deleteItem(id));
    }

    private void deleteDiscountCode(String id){
       System.out.println(SaleAndDiscountCodeController.getInstance().deleteDiscountCode(id));
    }
    private void deleteSale(String id){
        System.out.println(SaleAndDiscountCodeController.getInstance().deleteSale(id));
    }
    private void printUsers(){
        ArrayList<String> allUserNames=Gsonsaveload.printFolderContent("Users");
        printList(allUserNames);
    }
    private void showAllproducts(){
        ArrayList<String> allItems=Gsonsaveload.printFolderContent("Items");
        printList(allItems);
    }
    private void showAllRequests(){
        ArrayList<String> allRequests=Gsonsaveload.printFolderContent("Requests");
        printList(allRequests);
    }
    private void viewAllDiscountCodes(){
        ArrayList<String> allDiscountCodes=Gsonsaveload.printFolderContent("Discount Codes");
        printList(allDiscountCodes);
    }
    private void showAllCategories(){
        //set current menu to category menu
    }

    private void addCategory(){

    }

    private void editCategory(){

    }

    private void removeCategory(){

    }

    private void removeProducts(){

    }

    private void createDiscountCode(){

    }


    private void viewDiscountCode(){

    }

    private void editDiscountCode(String discountID){

    }

    private void removeDiscountCode(){

    }

    private void manageAllProducts(){
        ArrayList<String>allItems=Gsonsaveload.printFolderContent("Items");
        printList(allItems);
    }

    private void manageRequests(){
        ArrayList<String>allRequests=Gsonsaveload.printFolderContent("Requests");
        printList(allRequests);
    }

    private void requestDetails(){

    }

    private void manageCategories(){

    }



    private void removeProduct(){

    }

    private void acceptRequestId(String id){
        RequestController.getInstance().acceptRequest(id);
    }

    private void declineRequestId(String id){
        RequestController.getInstance().declineRequest(id);
    }

}
