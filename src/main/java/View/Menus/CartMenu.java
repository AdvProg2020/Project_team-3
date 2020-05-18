package View.Menus;

import Controller.CartController;
import Controller.Controller;
import Controller.UserController;

import java.util.regex.Matcher;

public class CartMenu extends Menu {

    private static CartMenu cartMenu;

    private CartMenu() {
    }

    public static CartMenu getInstance() {
        if (cartMenu == null)
            cartMenu = new CartMenu();
        return cartMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_BLUE + "You are in the cart menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("show products")) {
            showProducts();
            return;
        }
        if (command.equals("show total price")) {
            showTotalPrice();
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if(command.equals("purchase")){
            purchase();
            return;
        }
        else if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setIntendedMenu("CartMenu");
            LoginRegisterMenu.getInstance().setPreviousMenu(CartMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        else if(command.equals("login") || command.equals("register")){
            LoginRegisterMenu.getInstance().setIntendedMenu("CartMenu");
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            LoginRegisterMenu.getInstance().setPreviousMenu(CartMenu.getInstance());
            return;
        }
        if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        }
        Matcher matcher = View.getMatcher("increase (\\S+)", command);
        if (matcher.matches()) {
            increase(matcher.group(1));
            return;
        }

        matcher = View.getMatcher("decrease (\\S+)", command);
        if (matcher.matches()) {
            decrease(matcher.group(1));
            return;
        }

        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewItem(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_CYAN+"You are viewing your cart. Enter your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("show products");   //done
        System.out.println("view [product id]"); //done
        System.out.println("increase [product id]");  //done
        System.out.println("decrease [product id]");  //done
        System.out.println("show total price");      //done
        System.out.println("purchase");
        System.out.println("back");
        System.out.println("logout");
    }

    public void showProducts() {
       System.out.println(CartController.getInstance().showCart());
    }

    public void increase(String itemID) {
        System.out.println(CartController.getInstance().cartIncreaseDecrease(itemID, 1));
    }

    public void decrease(String itemID) {
        System.out.println(CartController.getInstance().cartIncreaseDecrease(itemID, -1));
    }

    public void showTotalPrice() {
        System.out.println(CartController.getInstance().getCartPriceWithoutDiscountCode());
    }

    public void purchase(){
       if(!Controller.getInstance().isLogin()){
           System.out.println(View.ANSI_RED+"Error: You must be logged in."+View.ANSI_RESET);
           LoginRegisterMenu.getInstance().setPreviousMenu(CartMenu.getInstance());
           LoginRegisterMenu.getInstance().setIntendedMenu("CartMenu");
           View.setCurrentMenu(LoginRegisterMenu.getInstance());
           return;
       }
       if(CartController.getInstance().getCartPriceWithoutDiscountCode()==0D){
           System.out.println(View.ANSI_RED+"Error: Your cart is empty , cattle."+View.ANSI_RESET);
           return;
       }
       if(UserController.getInstance().returnUserType(UserController.getInstance().getCurrentOnlineUser().getUsername()).equals("Buyer")){
           PurchaseMenu.getInstance().setPreviousMenu(CartMenu.getInstance());
           View.setCurrentMenu(PurchaseMenu.getInstance());
           return;
       }
          System.out.println(View.ANSI_RED+"Error:You must be a Buyer to buy items"+View.ANSI_RESET);
    }

}
