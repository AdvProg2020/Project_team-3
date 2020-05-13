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
        if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
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
            String itemId = matcher.group(1);
            viewItem(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
    }

    @Override
    public void help() {
        System.out.println("show products");   //done
        System.out.println("view [product id]"); //done
        System.out.println("increase [product id]");  //done
        System.out.println("decrease [product id]");  //done
        System.out.println("show total price");      //done
        System.out.println("purchase");
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
       if(Controller.getInstance().isLogin()==false){
           System.out.println("Error: please login to continue");
           return;
       }
       if(UserController.getInstance().returnUserType(UserController.getInstance().getCurrentOnlineUser().getUsername()).equals("Buyer")){
           View.setCurrentMenu(PurchaseMenu.getInstance());
           View.setPreviousMenu(CartMenu.getInstance());
           return;
       }
          System.out.println("you must be a Buyer to buy items");
    }

}
