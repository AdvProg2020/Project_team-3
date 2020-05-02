package View.Menus;

import Control.UserController;
import Model.Users.Admin;
import Model.Users.Seller;

public class MainMenu extends Menu {
    private static MainMenu mainMenu;
    private int optionCount = 4;

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

  /*  @Override
    public void show() {
        System.out.println(View.ANSI_PURPLE + "You are in the main menu." + View.ANSI_RESET);
        System.out.println("1- Enter user zone\n2- Enter the shop\n3- View ongoing sales \n4- Help\n5- Logout\n6- Exit");
        String command = View.read.nextLine();
        execute(command);
    }*/


    public void execute(String command){
        if(command.equals("1")){
            userMenu();
        }else if(command.equals("2")){
            View.setCurrentMenu(ShopMenu.getInstance());
        }else if(command.equals("3")){
            View.setCurrentMenu(DiscountsMenu.getInstance());
        }else if(command.equals("4")){
            run();
        }else if(command.equals("5")) {
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
        }else if(command.equals("6")){
            logout();
            run();
        }
        else if(command.equals("7")){
            exit();
        }

        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            run();
        }
    }

    private void exit()  {
        View.setProgramRunning(false);
    }

    private void userMenu(){
        if(UserController.getInstance().getCurrentOnlineUser() == null){
            View.setCurrentMenu(MainMenu.getInstance());
            System.out.println(View.ANSI_RED+"You are not logged in."+View.ANSI_RESET);
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
        System.out.println("1- Enter user zone\n2- Enter the shop\n3- View ongoing sales \n4- Help\n5- Login/Register\n6- Logout\n7- Exit");
        System.out.println(View.ANSI_WHITE+"You can type \"help\" in any menu to get help, this has been removed \nfrom the list of commands to avoid complication."+View.ANSI_RESET);
    }


}
