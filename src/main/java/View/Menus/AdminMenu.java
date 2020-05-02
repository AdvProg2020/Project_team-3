package View.Menus;

import Control.Gsonsaveload;
import Control.RequestController;
import Control.SaleAndDiscountCodeController;
import Control.UserController;
import Model.DiscountCode;
import Model.Requests.Request;
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
     matcher=View.getMatcher("delete user (\\S+)",command);
     if(matcher.matches()){
         deleteUser(matcher.group(1));
     }
     matcher=View.getMatcher("view (\\S+)",command);
     if(matcher.matches()){
        viewPersonalInfo(matcher.group(1));
     }

    }


   /* @Override
    public void execute(String command){
       if(command.equals("1")) {
           AddAdminAccount();
       } else if(command.equals("2")){
           deleteUser();
       }else if(command.equals("3")){
           logout();
       }else{
           System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
           show();
       }
    } */

    @Override
    public void help(){
        System.out.println("view personal info"); //done
        System.out.println("edit [field]");
        System.out.println("manage users");  //done
        System.out.println("delete user [username]"); //done
        System.out.println("view [username]");    //done
        System.out.println("change type [username] [role]");
        System.out.println("create manager profile"); //done
        System.out.println("manage all products");    //done
        System.out.println("remove [product id]");
        System.out.println("create discount code");
        System.out.println("view discount code");
        System.out.println("view discount code [id]");
        System.out.println("edit discount code [id]");
        System.out.println("remove discount code [id]");
        System.out.println("manage requests");
        System.out.println("details requests [request id]");
        System.out.println("accept [request id]");
        System.out.println("decline [request id]");
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

    private void addSpecialSale(){

    }

    private void viewUser() {

    }

    public void deleteUser(String username){
        System.out.println(UserController.getInstance().deleteUser(username));
    }

    public void printUsers(){
        ArrayList<String> allUserNames=Gsonsaveload.printFolderContent("Users");
        printList(allUserNames);
    }

    public void showAllproducts(){
        ArrayList<String> allItems=Gsonsaveload.printFolderContent("Items");
        printList(allItems);
    }

    public void showAllCategories(){
        //set current menu to category menu
    }

    public void addCategory(){

    }

    public void editCategory(){

    }

    public void removeCategory(){

    }

    public void removeProducts(){

    }

    public void createDiscountCode(){

    }

    public void viewAllDiscountCodes(){
        ArrayList<DiscountCode> allDiscountCode=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
            for(DiscountCode discountCode:allDiscountCode){
                System.out.println(discountCode);
            }

    }

    public void viewOneDiscountCode(){

    }

    public void editDiscountCode(String discountID){

    }

    public void removeDiscountCode(){

    }

    public void manageAllProducts(){

    }

    public void manageRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        int index;
        System.out.println("please enter the number of request changing its state!");
        index=readNumber(allRequests.size()-1,"");
        System.out.println(allRequests.get(index));
        System.out.println("enter a for accept or d for decline");
            String input=View.read.nextLine();
            if(input.equals("a")){
                RequestController.getInstance().acceptRequest(allRequests.get(index).getRequestId());
            }else if(input.equals("d")){
                RequestController.getInstance().declineRequest(allRequests.get(index).getRequestId());
            }
    }

    public void requestDetails(){
        ArrayList<Request>allRequests= RequestController.getInstance().getAllRequestFromDataBase();
        int index;
        System.out.println("you have "+allRequests.size()+" requests!");
        System.out.println("please enter the number of request you want to know about its details!");
        index=readNumber(allRequests.size()-1,"");
        System.out.println(allRequests.get(index));
    }

    public void manageCategories(){

    }



    public void removeProduct(){

    }




}
