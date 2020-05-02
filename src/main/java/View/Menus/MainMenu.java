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

    }

  /*  @Override
    public void show() {
        System.out.println(View.ANSI_PURPLE + "You are in the main menu." + View.ANSI_RESET);
        System.out.println("1- Enter user zone\n2- Enter the shop\n3- View ongoing sales \n4- Help\n5- Logout\n6- Exit");
        String command = View.read.nextLine();
        execute(command);
    }


    @Override
    public void execute(String command){
        if(command.equals("1")){
            userMenu();
        }else if(command.equals("2")){
            ShopMenu.getInstance().show();
        }else if(command.equals("3")){
            DiscountsMenu.getInstance().show();
        }else if(command.equals("4")){
            show();
        }else if(command.equals("5")) {
            logout();
            show();
        }else if(command.equals("6")){
            exit();
        } else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    } */

    private void exit()  {

    }

    private void userMenu(){
        if(UserController.getInstance().getCurrentOnlineUser() == null){
            View.setCurrentMenu(MainMenu.getInstance());
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
        System.out.println(View.ANSI_PURPLE + "You are in the main menu." + View.ANSI_RESET);
        System.out.println("1- Enter user zone\n 2- Enter the shop\n 3- View ongoing sales \n 4- Help");
    }


}
