package View.Menus.SellerMenu;

import Controller.ItemAndCategoryController;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

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
        System.out.println(View.ANSI_WHITE+"Enter the field you wish to edit or type back to go to the product management menu."+View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    public void execute(String command){
        if(command.equals("back")){
            View.setCurrentMenu(SellerManageProductsMenu.getInstance());

        }
        else if(command.equals("back to SellerMenu")){
            View.setCurrentMenu(SellerMenu.getInstance());
        }
        else if(isValidKey(command)){
            setAttribute(command);
        }
        else if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();

        }
        else System.out.println(View.ANSI_RED + "Invalid command/field." + View.ANSI_RESET);

    }

    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter the field you wish to edit or type back to go to the product management menu."+View.ANSI_RESET);
        System.out.println("fields can be one of the following:");
        System.out.println("name,brand,price,description,stock,category name");
        System.out.println("You can also type a command in one of these formats:");
        System.out.println("back");
        System.out.println("back to SellerMenu");
        System.out.println("logout");
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    private void setAttribute(String key){
        System.out.print("Enter a new value for "+key+":");
        String value = View.getRead().nextLine();
        System.out.println("Are you sure? [*anything*/no]");
        String command = View.getRead().nextLine();
        if (command.equals("no")) {
            return;
        }
        ItemAndCategoryController.getInstance().editItem(key,value,itemID);
    }

    private boolean isValidKey(String key){
        if(key.equalsIgnoreCase("name")||key.equalsIgnoreCase("brand")
        || key.equalsIgnoreCase("price") || key.equalsIgnoreCase("stock")
        || key.equalsIgnoreCase("description") || key.equalsIgnoreCase("category name")) return true;
        if(ItemAndCategoryController.getInstance().canEditAttribute(itemID,key)) return true;
        return false;
    }
}
