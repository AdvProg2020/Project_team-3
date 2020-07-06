package Project.View.CLI.SellerMenu;

import Project.Controller.*;
import Project.View.CLI.LoginRegisterMenu;
import Project.View.CLI.MainMenu;
import Project.View.CLI.ShopAndDiscountMenu.SortAndFilterMenu;
import Project.View.CLI.UserMenu;
import Project.View.CLI.View;

import java.util.regex.Matcher;

public class SellerManageProductsMenu extends UserMenu {
    private static SellerManageProductsMenu sellerManageProductsMenu;

    private SellerManageProductsMenu() {
    }

    public static SellerManageProductsMenu getInstance() {
        if (sellerManageProductsMenu == null)
            sellerManageProductsMenu = new SellerManageProductsMenu();
        return sellerManageProductsMenu;
    }

    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("manage products")) {
            manageProducts();
            return;
        }

        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewItem(matcher.group(1));
            return;
        }


        matcher = View.getMatcher("view buyers (\\S+)", command);
        if (matcher.matches()) {
            viewBuyers(matcher.group(1));
            return;
        }

        if (command.equals("add product")) {
            SellerMenu.getInstance().addItem();
            return;
        }
        if (command.startsWith("edit product ")) {
            matcher = View.getMatcher("edit product (\\S+)", command);
            if (matcher.matches()) {
                editProduct(matcher.group(1));
            }
            return;
        }
        if (command.startsWith("remove product")) {
            matcher = View.getMatcher("remove product (\\S+)", command);
            if (matcher.matches()) {
                removeProduct(matcher.group(1));
            }
            return;
        }
        if (command.equals("back")) {
            SortAndFilterController.getInstance().reset();
            View.setCurrentMenu(SellerMenu.getInstance());
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if (command.equals("sorting") || command.equals("filtering")) {
            SortAndFilterMenu.getInstance().setPreviousMenu(SellerManageProductsMenu.getInstance());
            View.setCurrentMenu(SortAndFilterMenu.getInstance());
            return;
        }
        if (command.equals("logout")) {
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }

        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    public void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the seller menu." + View.ANSI_RESET);
        System.out.println("manage products");
        System.out.println("view [productId]");
        System.out.println("view buyers [productId]");
        System.out.println("edit product [productId]");
        System.out.println("remove product [productId]");
        System.out.println("sorting");
        System.out.println("filtering");
    }

    public void manageProducts() {
        printList(SortAndFilterController.getInstance().show(UserController.getInstance().getSellerItems()));
    }

    public void viewBuyers(String ID) {
        if (!UserController.getInstance().doesSellerHaveItem(ID)) {
            System.out.println(View.ANSI_RED + "Invalid item ID." + View.ANSI_RESET);
            return;
        }
        printList(ItemAndCategoryController.getInstance().getItemBuyer(ID));
    }

    public void editProduct(String ID) {
        if (!UserController.getInstance().doesSellerHaveItem(ID)) {
            System.out.println(View.ANSI_RED + "Invalid item ID." + View.ANSI_RESET);
            return;
        }
        SellerEditItemMenu.getInstance().setItemID(ID);
        SellerEditItemMenu.getInstance().setPreviousMenu(SellerManageProductsMenu.getInstance());
        View.setCurrentMenu(SellerEditItemMenu.getInstance());
    }

    public void removeProduct(String itemID) {
        System.out.println(ItemAndCategoryController.getInstance().deleteItem(itemID));
    }
}
