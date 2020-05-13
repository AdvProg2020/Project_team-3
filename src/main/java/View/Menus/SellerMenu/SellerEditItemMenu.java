package View.Menus.SellerMenu;

import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.regex.Matcher;

public class SellerEditItemMenu extends UserMenu {
    private static SellerEditItemMenu sellerEditItemMenu;
    private String itemID;
    private SellerEditItemMenu(){ }

    public static SellerEditItemMenu getInstance(){
        if(sellerEditItemMenu==null)
            sellerEditItemMenu = new SellerEditItemMenu();
        return sellerEditItemMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    public void execute(String command){
        Matcher matcher;



        if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the product management menu."+View.ANSI_RESET);
        System.out.println("edit name");
        System.out.println("edit brand");
        System.out.println("edit description");
        System.out.println("edit price");
        System.out.println("edit stock");
        System.out.println("edit category");
        System.out.println("edit detail [attribute]");
        System.out.println("back to SellerMenu");
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
