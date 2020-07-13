package Project.Client.CLI;

import Server.Controller.ItemAndCategoryController;
import Server.Controller.UserController;
import Project.Client.CLI.ShopAndDiscountMenu.DiscountsMenu;
import Project.Client.CLI.ShopAndDiscountMenu.ShopMenu;

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
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
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
            matcher = View.getMatcher("show order (\\S+)", command);
            if (matcher.matches()) {
               try{
                   showOrder(Integer.parseInt(matcher.group(1))-1);
               }catch (Exception exception){
                   System.out.println("Error: invalid id");
                   return;
               }
            }
            return;
        } else if (command.startsWith("rate ")) {
            matcher = View.getMatcher("rate (\\S+) ([1-2-3-4-5])", command);
            if (matcher.matches()) {
                rate(matcher.group(1), Integer.parseInt(matcher.group(2)));
                return;
            }

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

    private void showOrder(int index) {
        System.out.println(UserController.getInstance().getBuyLog(index));
    }

    private void viewOrders() {
        System.out.println(UserController.getInstance().getAllBuyLogs());
    }

    private void viewBalance() {
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE + UserController.getInstance().currentOnlineUserBalance() + View.ANSI_RESET);
    }

    private void viewDiscountCodes() {
        System.out.println(UserController.getInstance().getBuyerDiscountCode());
    }

    private void rate(String itemID, int score) {
        System.out.println(ItemAndCategoryController.getInstance().rate(score, itemID));
    }

}
