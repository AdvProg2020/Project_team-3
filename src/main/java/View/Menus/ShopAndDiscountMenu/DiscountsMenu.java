package View.Menus.ShopAndDiscountMenu;

import Controller.SaleAndDiscountCodeController;
import View.Menus.LoginRegisterMenu;
import View.Menus.Menu;
import View.Menus.View;

import java.util.regex.Matcher;

public class DiscountsMenu extends Menu {
    private static DiscountsMenu discountsMenu;

    private DiscountsMenu() {
    }

    public static DiscountsMenu getInstance() {
        if (discountsMenu == null)
            discountsMenu = new DiscountsMenu();
        return discountsMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_BLACK + "You are in the Discounts menu." + View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("offs")) {
            offs();
            return;
        }
        else if ((command.equals("filtering"))||(command.equals("sorting"))) {
            SortAndFilterMenu.getInstance().setPreviousMenu(DiscountsMenu.getInstance());
            View.setCurrentMenu(SortAndFilterMenu.getInstance());
            return;
        }
        else if (command.equals("help")) {
            help();
            return;
        }
        else if (command.equals("back")) {
            View.setCurrentMenu(getPreviousMenu());
            return;
        }
        else if(command.equals("login")){
            LoginRegisterMenu.getInstance().setPreviousMenu(DiscountsMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            return;
        }
        else if(command.equals("register")){
            LoginRegisterMenu.getInstance().setPreviousMenu(DiscountsMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            return;
        }
        else if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(DiscountsMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        Matcher matcher = View.getMatcher("show product (\\S+)", command);
        if (matcher.matches()) {
            viewItem(matcher.group(1));
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_BLACK + "You are in the Discounts menu.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println("offs");
        System.out.println("show product [product id]");
    }

    public void offs() {
        System.out.println(SaleAndDiscountCodeController.getInstance().printItemsWithSale());
    }

}
