package View.Menus;

import Control.ItemAndCategoryController;
import Control.UserController;
import Model.Cart;
import Model.Users.Buyer;
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
        if(command.equals("offs")){
            View.previousMenu = BuyerMenu.getInstance();
            View.setCurrentMenu(DiscountsMenu.getInstance());
        }
        if(command.equals("view personal info")){
            viewPersonalInfo();
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
        }
        /*else if(command.equals("show products")){
            viewCart();
        }*/
        else if(command.equals("products")){
            View.previousMenu = BuyerMenu.getInstance();
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        /*else if(command.startsWith("increase ")){
            increaseProduct(command);
        }
        else if(command.startsWith("decrease ")){
            decreaseProduct(command);
        }
        else if(command.equals("show total price")){
            showTotalPrice();
        }*/
        else if(command.equals("view cart")){
            View.previousMenu = BuyerMenu.getInstance();
            View.setCurrentMenu(CartMenu.getInstance());
        }/*
        else if(command.equals("purchase")){
            purchase();//
        }*/
        else if(command.equals("view orders")){
            viewOrders();
        }
        else if(command.startsWith("show order ")){
            //
        }
        else if(command.startsWith("rate ")){
            //
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
        /*else if(command.startsWith("view ")){
            matcher=View.getMatcher("view (\\S+)",command);
            if(matcher.matches()){
                viewItem(matcher.group(1));
            }
        }*/
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
        //System.out.println("show products");
        //System.out.println("view [productId]");
        //System.out.println("increase [productId]");
        //System.out.println("decrease [productId]");
        //System.out.println("show total price");
        //System.out.println("purchase");
        System.out.println("view orders");
        System.out.println("show order [orderId]");
        System.out.println("rate [productId] [1-5]");
        System.out.println("view balance");  //done
        System.out.println("view discount codes");
        System.out.println("logout");
        System.out.println("back");
    }

    private void showTotalPrice(){
        User user = UserController.getInstance().getCurrentOnlineUser();
        System.out.print(View.ANSI_BLUE+"Total Price:"+View.ANSI_RESET);
        System.out.println(ItemAndCategoryController.getInstance().getCurrentShoppingCart().getCartPrice());
    }

    private void purchase(){
        View.setCurrentMenu(PurchaseMenu.getInstance());
    }

    private void viewOrders(){
        Buyer buyer = (Buyer)UserController.getInstance().getCurrentOnlineUser();
        System.out.println(buyer.getBuyLogsString());
    }

    private void viewBalance(){
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE+UserController.getInstance().currentOnlineUserBalance()+View.ANSI_RESET);
    }

    private void increaseProduct(String command){
        if(command.split(" ").length!=2){
            System.out.println(View.ANSI_RED+"Invalid product ID."+View.ANSI_RESET);
            return;
        }
        String id = command.split(" ")[1];
        Cart cart = ItemAndCategoryController.getInstance().getCurrentShoppingCart();
        cart.changeCountBy(id,1);
        System.out.println("Increased successfully.");
    }
    private void decreaseProduct(String command){
        if(command.split(" ").length!=2){
            System.out.println(View.ANSI_RED+"Invalid product ID."+View.ANSI_RESET);
            return;
        }
        String id = command.split(" ")[1];
        Cart cart = ItemAndCategoryController.getInstance().getCurrentShoppingCart();
        cart.changeCountBy(id,-1);
        System.out.println("Decreased successfully.");
    }

    private void viewDiscountCodes(){
        Buyer buyer = (Buyer)UserController.getInstance().getCurrentOnlineUser();
        System.out.println(buyer.getDiscountCodes());
    }



}
