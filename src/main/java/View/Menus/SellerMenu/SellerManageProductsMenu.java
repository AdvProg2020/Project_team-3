package View.Menus.SellerMenu;

import Controller.Controller;
import Model.Users.Seller;
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
        }
        if(command.startsWith("view ")){
            matcher=View.getMatcher("view (\\S+)",command);
            if(matcher.matches()){
                viewItem(matcher.group(1));
            }
        }
        if(command.startsWith("view buyers ")){
            viewBuyers(command);
        }
        if(command.startsWith("edit ")){
            editProduct(command);
        }
        if(command.equals("back")){
            View.setCurrentMenu(SellerMenu.getInstance());
        }
        if(command.equals("help")){
            help();
        }
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

    public void viewBuyers(String command){

    }

    public void editProduct(String command){

    }
}
