package Project.Client.CLI.AdminMenu;

import Server.Controller.Database;
import Server.Controller.SortAndFilterController;
import Project.Client.CLI.LoginRegisterMenu;
import Project.Client.CLI.MainMenu;
import Project.Client.CLI.Menu;
import Project.Client.CLI.ShopAndDiscountMenu.SortAndFilterMenu;
import Project.Client.CLI.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageProductsMenu extends Menu {
    private static AdminManageProductsMenu adminManageProductsMenu;
    private AdminManageProductsMenu(){
    }
    public static AdminManageProductsMenu getInstance(){
        if (adminManageProductsMenu == null)
            adminManageProductsMenu = new AdminManageProductsMenu();
        return adminManageProductsMenu;
    }


    @Override
    protected void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the admin menu." + View.ANSI_RESET);
        System.out.println("view all products");
        System.out.println("sorting");
        System.out.println("filtering");
        System.out.println("remove [product id]");
    }

    @Override
    protected void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    protected void execute(String command) {
        Matcher matcher;
        if(command.equals("back")){
            SortAndFilterController.getInstance().reset();
            View.setCurrentMenu(AdminMenu.getInstance());
            return;
        }
        if(command.equals("help")){
            help();
            return;
        }
        if(command.equals("view all products")){
            showAllProducts();
            return;
        }
        if(command.equals("sorting") || command.equals("filtering")){
            SortAndFilterMenu.getInstance().setPreviousMenu(AdminManageProductsMenu.getInstance());
            View.setCurrentMenu(SortAndFilterMenu.getInstance());
            return;
        }
        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewItem(matcher.group(1));
            return;
        }
        if (command.equals("logout")) {
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
    }

    protected void showAllProducts() {
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(SortAndFilterController.getInstance().show(allItems));
    }
}
