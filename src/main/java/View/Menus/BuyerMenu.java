package View.Menus;

import Controller.CartController;
import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.ShopAndDiscountMenu.DiscountsMenu;
import View.Menus.ShopAndDiscountMenu.ShopMenu;

import java.util.regex.Matcher;

public class BuyerMenu extends UserMenu {
    private static BuyerMenu buyerMenu;

    private BuyerMenu() {
    }

    public static BuyerMenu getInstance() {
        if (buyerMenu == null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_BLUE + "You are in the Buyer menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("logout")) {
            logout();
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        }
        if (command.equals("offs")) {
            DiscountsMenu.getInstance().setPreviousMenu(BuyerMenu.getInstance());
            View.setCurrentMenu(DiscountsMenu.getInstance());
            return;
        }
        if (command.equals("view personal info")) {
            viewPersonalInfo();
            return;
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
            return;
        } else if (command.equals("products")) {
            ShopMenu.getInstance().setPreviousMenu(BuyerMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
            return;
        } else if (command.equals("view cart")) {
            CartMenu.getInstance().setPreviousMenu(BuyerMenu.getInstance());
            View.setCurrentMenu(CartMenu.getInstance());
            return;
        } else if (command.equals("view orders")) {
            viewOrders();
            return;
        } else if (command.startsWith("show order ")) {
            //do sth
            return;
        } else if (command.startsWith("rate ")) {
            matcher = View.getMatcher("rate (\\S+) ([1-2-3-4-5])", command);
            if (matcher.matches()) {
                rate(matcher.group(1), Integer.parseInt(matcher.group(2)));
            }
            return;
        } else if (command.equals("view balance")) {
            viewBalance();
            return;
        } else if (command.equals("view discount codes")) {
            viewDiscountCodes();
            return;
        } else if (command.equals("back")) {
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        } else if (command.equals("help")) {
            help();
            return;
        }

        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }


    @Override
    public void help() {
        System.out.println(View.ANSI_BLUE + "You are in the Buyer menu.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println("view personal info");
        System.out.println("edit [field]");
        System.out.println("view cart");
        System.out.println("view orders");
        System.out.println("show order [orderId]");
        System.out.println("rate [productId] [1-5]");
        System.out.println("view balance");
        System.out.println("view discount codes");
        System.out.println("logout");
        System.out.println("back");
    }

    private void showTotalPrice() {
        User user = UserController.getInstance().getCurrentOnlineUser();
        System.out.print(View.ANSI_BLUE + "Total Price:" + View.ANSI_RESET);
        System.out.println(CartController.getInstance().getCurrentShoppingCart().getCartPriceWithoutDiscountCode());
    }

    private void purchase() {
        View.setCurrentMenu(PurchaseMenu.getInstance());
    }

    private void viewOrders() {
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        System.out.println(buyer.getBuyLogsString());
    }

    private void viewBalance() {
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE + UserController.getInstance().currentOnlineUserBalance() + View.ANSI_RESET);
    }


    private void viewDiscountCodes() {
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        System.out.println(buyer.getDiscountCodes());
    }

    private void rate(String itemID, int score) {
        System.out.println(ItemAndCategoryController.getInstance().rate(score, itemID));
    }

}
