package View.Menus;

import Controller.UserController;
import Model.Users.Admin;
import Model.Users.Seller;
import View.Menus.AdminMenu.AdminMenu;
import View.Menus.SellerMenu.SellerMenu;
import View.Menus.ShopAndDiscountMenu.DiscountsMenu;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.ShopAndDiscountMenu.SortAndFilterMenu;


public class MainMenu extends Menu {
    private static MainMenu mainMenu;
    private MainMenu() {

    }

    public static MainMenu getInstance() {
        if (mainMenu == null)
            mainMenu = new MainMenu();
        return mainMenu;
    }

    @Override
    public void run(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }

    public void execute(String command){
        if(command.equals("1")){
            userMenu();
        }else if(command.equals("2")){
            View.setCurrentMenu(ShopMenu.getInstance());
        }else if(command.equals("3")){
            View.setCurrentMenu(DiscountsMenu.getInstance());
            DiscountsMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
        }else if(command.equals("4")){
            run();
        }else if(command.equals("5")) {
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
        }else if(command.equals("6")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            run();
        }else if(command.equals("7")){
            View.setCurrentMenu(CartMenu.getInstance());
            CartMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
        }
        else if(command.equals("8")){
            exit();
        }
        else if (command.equals("filtering")||(command.equals("sorting"))) {
            SortAndFilterMenu.getInstance().setPreviousMenu(ShopMenu.getInstance());
            View.setCurrentMenu(SortAndFilterMenu.getInstance());
            return;
        }else if(command.equals("logout")){
            System.out.println(UserController.getInstance().logout());
        }
        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }

    private void exit()  {
        View.setProgramRunning(false);
    }

    private void userMenu(){
        if(UserController.getInstance().getCurrentOnlineUser() == null){
            View.setCurrentMenu(MainMenu.getInstance());
            System.out.println(View.ANSI_RED+"You must be logged in to do this action."+View.ANSI_RESET);
            LoginRegisterMenu.getInstance().setIntendedMenu("UserMenu");
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            return;
        }
        if(UserController.getInstance().getCurrentOnlineUser() instanceof Admin){
            View.setCurrentMenu(AdminMenu.getInstance());
        }else if(UserController.getInstance().getCurrentOnlineUser() instanceof Seller){
            View.setCurrentMenu(SellerMenu.getInstance());
        }else{
            View.setCurrentMenu(BuyerMenu.getInstance());
        }
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_PURPLE + "You are in the main menu.\nSelect a number." + View.ANSI_RESET);
        System.out.println("1- Enter user zone\n2- Enter the shop\n3- View ongoing sales \n4- Help\n5- Login/Register\n6- Logout\n7- Cart Menu\n8- Exit");
        System.out.println(View.ANSI_WHITE+"You can type \"help\" in any menu to get help, this has been removed \nfrom the list of commands to avoid complication."+View.ANSI_RESET);
    }


}
