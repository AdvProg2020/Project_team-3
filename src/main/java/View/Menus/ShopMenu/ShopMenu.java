package View.Menus.ShopMenu;

import Controller.Database;
import Controller.ItemAndCategoryController;
import Model.Category;
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
        System.out.println(View.ANSI_YELLOW+"You are currently in the "+currentCategory.getName()+" category."+View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("view subcategories")) {
            viewSubCategories();
            return;
        }
        if (command.equals("view all categories")) {
            viewAllCategories();
            return;
        }
        if (command.equals("previous category")) {
            previousCategory();
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
            View.setCurrentMenu(View.getPreviousMenu());
            return;
        }
        if (command.equals("show all products")) {
            showAllItems();
            return;
        }

        matcher = View.getMatcher("show product (\\S+)", command);
        if (matcher.matches()) {
            viewItem(matcher.group(1));
            return;
        }

        matcher = View.getMatcher("open (\\S+)", command);
        if (matcher.matches()) {
            openCategory(matcher.group(1));
            return;
        }

        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    @Override
    public void help() {
        System.out.println(View.ANSI_YELLOW + "You are in the shop menu.\nType your command in one of these formats:" + View.ANSI_RESET);
        System.out.println(View.ANSI_YELLOW+"You are currently in the "+currentCategory.getName()+" category."+View.ANSI_RESET);
        System.out.println("view categories");
        System.out.println("view all categories");
        System.out.println("open [category name]");
        System.out.println("previous category");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("show all products");   //done
        System.out.println("show product [product id]"); //done
        //System.out.println("show category [category name]");
        System.out.println("back");

    }

    private void openCategory(String name) {
        System.out.println(ItemAndCategoryController.getInstance().openCategory(name,currentCategory));
    }

    private void previousCategory(){
        currentCategory = ItemAndCategoryController.getInstance().previousCategory(currentCategory);
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void cart() {

    }

    public void showAllItems() {
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(allItems);
    }

    public void viewSubCategories() {
        printList(currentCategory.getSubCategories());
    }

    public void viewAllCategories() {
        printList(Database.getInstance().printFolderContent("Categories"));
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
