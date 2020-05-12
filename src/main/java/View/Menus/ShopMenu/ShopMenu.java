package View.Menus.ShopMenu;

import ControllerTest.Database;
import ControllerTest.ItemAndCategoryController;
import Model.Category;
import View.Menus.MainMenu;
import View.Menus.Menu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu extends Menu {
    private static ShopMenu shopMenu;
    private Category currentCategory;

    private ShopMenu() {
    }

    public static ShopMenu getInstance() {
        if (shopMenu == null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }

    @Override
    public void run() {
        System.out.println(View.ANSI_YELLOW + "You are in the shop menu." + View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("view categories")) {
            viewCategories();
            return;
        }
        if (command.equals("filtering")) {
            View.setCurrentMenu(FilterMenu.getInstance());
            return;
        }
        if (command.equals("sorting")) {
            View.setCurrentMenu(SortMenu.getInstance());
            return;
        }
        if (command.equals("show products")) {
            System.out.print(ItemAndCategoryController.getInstance().getCurrentViewableItemsString());
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if (command.equals("back")) {
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        }
        if(command.equals("show all products")){
           showallItems();
           return;
        }

        Matcher matcher = View.getMatcher("show product (\\S+)", command);
        if (matcher.matches()) {
            String itemId = matcher.group(1);
            viewItem(matcher.group(1));
            return;
        }

        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    @Override
    public void help() {
        System.out.println(View.ANSI_YELLOW + "You are in the shop menu.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println("view categories"); //done
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("show all products");   //done
        System.out.println("show product [product id]"); //done
        System.out.println("show category [category name]");
        System.out.println("back");
        //System.out.println("show available filters");
        //System.out.println("filter [an available filter]");
        //System.out.println("current filters");
        //System.out.println("disable filter [a selected filter]");
        //System.out.println("show available sorts");   //done
        //System.out.println("sort [an available sort]");
        //System.out.println("current sort");
        //System.out.println("disable sort");
    }


    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void cart() {

    }

    public void showallItems(){
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(allItems);
    }

    public void viewCategories() {
        ArrayList<String> allCategories = Database.getInstance().printFolderContent("Categories");
        printList(allCategories);
    }

    public void filtering() {

    }

    public void currentFilters() {

    }

    public void disableFilter() {

    }

    public void sorting() {

    }

    public void sort() {

    }

    public void currentSort() {

    }

    public void disableSort() {

    }

    public void showProducts() {

    }

    public void showProduct(String command) {
        String itemID = command.split(" ")[2];
        viewItem(itemID);
    }

}
