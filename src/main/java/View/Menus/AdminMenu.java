package View.Menus;

import Control.Gsonsaveload;
import Control.RequestController;
import Control.UserController;
import Model.Requests.Request;

import java.util.ArrayList;

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

    }

    @Override
    public void execute(String command) {

    }

   /* @Override
    public void show(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
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
    System.out.println("1-add admin account\n2-delete user\n3-logout");
    }


    private void seeAllRequests(){
        ///set current menu to request menu
    }

    private void AddAdminAccount(){
       registerAdmin();
    }

    private void showAllSales(){

    }

    private void addSpecialSale(){

    }

    private void viewUser() {

    }

    public void deleteUser(){
        ArrayList<String> allUserNames=Gsonsaveload.printFolderContent("Users");
        printList(allUserNames);
        System.out.println("please select the User you wish to remove");
        int index=readNumber(allUserNames.size(),"")-1;
        System.out.println(UserController.getInstance().deleteUser(allUserNames.get(index)));
        //AdminMenu.getInstance().show();
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
