package View.Menus.SellerMenu;

import Controller.Controller;
import Controller.*;
import Model.Item;
import Model.Users.Seller;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.regex.Matcher;

public class SellerManageProductsMenu extends UserMenu {
    private static SellerManageProductsMenu sellerManageProductsMenu;
    private SellerManageProductsMenu(){ }

    public static SellerManageProductsMenu getInstance(){
        if(sellerManageProductsMenu==null)
            sellerManageProductsMenu = new SellerManageProductsMenu();
        return sellerManageProductsMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }
    @Override
    public void execute(String command) {
        Matcher matcher;
        if(command.equals("manage products")){
            manageProducts();
            return;
        }
        if(command.startsWith("view ")){
            matcher=View.getMatcher("view (\\S+)",command);
            if(matcher.matches()){
                viewItem(matcher.group(1));
            }
            return;
        }
        if(command.startsWith("view buyers ")){
            matcher=View.getMatcher("view buyers (\\S+)",command);
            if(matcher.matches()){
                viewBuyers(matcher.group(1));
            }

            return;
        }
        if(command.startsWith("edit ")){
            matcher=View.getMatcher("edit (\\S+)",command);
            if(matcher.matches()){
                editProduct(matcher.group(1));
            }
            return;
        }
        if(command.equals("back")){
            View.setCurrentMenu(SellerMenu.getInstance());
            return;
        }
        if(command.equals("help")){
            help();
            return;
        }
        if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }

        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);

    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the seller menu."+View.ANSI_RESET);
        System.out.println("manage products");
        System.out.println("view [productId]");
        System.out.println("view buyers [productId]");
        System.out.println("edit product [productId]");
    }

    public void manageProducts(){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        System.out.println(seller.getAllItemsString());
    }

    public void viewBuyers(String ID){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        if(!seller.hasItem(ID)){
            System.out.println(View.ANSI_RED+"Invalid item ID."+View.ANSI_RESET);
            return;
        }

        Item item = ItemAndCategoryController.getInstance().getItemById(ID);
        printList(item.getBuyerUserName());
    }

    public void editProduct(String ID){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        if(!seller.hasItem(ID)){
            System.out.println(View.ANSI_RED+"Invalid item ID."+View.ANSI_RESET);
            return;
        }
        SellerEditItemMenu.getInstance().setItemID(ID);
        SellerEditItemMenu.getInstance().setPreviousMenu(SellerManageProductsMenu.getInstance());
        View.setCurrentMenu(SellerEditItemMenu.getInstance());
    }
}
