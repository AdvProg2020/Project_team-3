import java.util.ArrayList;
import java.util.HashMap;

public class AdminMenu extends Menu{
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
    public String toString(){
        return "";
    }
    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

    public void editPersonalInfo(){

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

    public void deleteUser(){

    }

    public void showAllCategories(){
        //set current menu to category menu
    }

    public void addCategory(){

    }


}
