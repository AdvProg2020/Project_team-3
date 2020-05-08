package View.Menus;

import Control.ItemAndCategoryController;
import Control.UserController;
import Model.Users.User;

import java.util.regex.Matcher;

public class BuyerMenu extends UserMenu {
    private static BuyerMenu buyerMenu;
    private int optionCount = 4;
    private BuyerMenu(){ }

    public static BuyerMenu getInstance(){
        if(buyerMenu==null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE+"You are in the Buyer menu."+View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if(command.equals("logout")){
            logout();
            View.setCurrentMenu(MainMenu.getInstance());
        }
        if(command.equals("view personal info")){
            viewPersonalInfo();
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
        }
        else if(command.equals("show products")){
            viewCart();
        }
        else if(command.equals("products")){
            View.previousMenu = BuyerMenu.getInstance();
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        else if(command.startsWith("view ")){

        }
        else if(command.startsWith("increase ")){

        }
        else if(command.startsWith("decrease ")){

        }
        else if(command.equals("show total price")){

        }
        else if(command.equals("view cart")){
            viewCart();
        }
        else if(command.equals("purchase")){
            purchase();
        }
        else if(command.equals("view orders")){
            viewOrders();
        }
        else if(command.startsWith("show order ")){

        }
        else if(command.startsWith("rate ")){

        }
        else if(command.equals("view balance")){
            viewBalance();
        }
        else if(command.equals("view discount codes")){
            viewDiscountCodes();
        }
        else if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
        }
        else if(command.equals("help")){
            help();
        }
        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }



    @Override
    public void help(){
        System.out.println(View.ANSI_BLUE+"You are in the Buyer menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info");
        System.out.println("edit [field]");
        System.out.println("view cart");
        System.out.println("show products");
        System.out.println("view [productId]");
        System.out.println("increase [productId]");
        System.out.println("decrease [productId]");
        System.out.println("show total price");
        System.out.println("purchase");
        System.out.println("view orders");
        System.out.println("show order [orderId]");
        System.out.println("rate [productId] [1-5]");
        System.out.println("view balance");  //done
        System.out.println("view discount codes");
        System.out.println("logout");
        System.out.println("back");
    }


    private void viewCart(){
        User user = UserController.getInstance().getCurrentOnlineUser();
        System.out.println(ItemAndCategoryController.getInstance().getCurrentShoppingCart().toString());
    }

    private void purchase(){
        View.setCurrentMenu(PurchaseMenu.getInstance());
    }

    private void viewOrders(){

    }

    private void viewBalance(){
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE+UserController.getInstance().currentOnlineUserBalance()+View.ANSI_RESET);
    }

    private void viewDiscountCodes(){

    }



}
