import java.util.ArrayList;
import java.util.HashMap;

public class AdminMenu extends UserMenu{
    private static AdminMenu adminMenu;
    private int optionCount = 9;
    private AdminMenu(){ }

    public static AdminMenu getInstance(){
        if(adminMenu==null)
            adminMenu = new AdminMenu();
        return adminMenu;
    }


    @Override
    public void show(){

    }

    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }


    public void seeAllRequests(){
        ///set current menu to request menu
    }

    public void AddAdminAccount(){

    }

    public void showAllSales(){

    }

    public void addSpecialSale(){

    }

    public void viewUser() {

    }

    public void deleteUser(){

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

    public void editDiscountCode(){

    }

    public void removeDiscountCode(){

    }

    public void manageAllProducts(){

    }

    public void manageRequests(){

    }

    public void requestDetails(){

    }

    public void manageCategories(){

    }



    public void removeProduct(){

    }




}
