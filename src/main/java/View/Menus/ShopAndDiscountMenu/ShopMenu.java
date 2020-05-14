package View.Menus.ShopAndDiscountMenu;

import Controller.Database;
import Controller.ItemAndCategoryController;
import Controller.SortAndFilterController;
import View.Menus.LoginRegisterMenu;
import View.Menus.Menu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu extends Menu {
    private static ShopMenu shopMenu;
    private String categoryName="Main";

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
        // System.out.println(View.ANSI_YELLOW+"You are currently in the "+currentCategory.getName()+" category."+View.ANSI_RESET);
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
        if ((command.equals("filtering")) || (command.equals("sorting"))) {
            SortAndFilterMenu.getInstance().setPreviousMenu(ShopMenu.getInstance());
            View.setCurrentMenu(SortAndFilterMenu.getInstance());
            return;
        }
        if (command.equals("show products")) {
            System.out.println(SortAndFilterController.getInstance().show(categoryName));
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if (command.equals("back")) {
            View.setCurrentMenu(getPreviousMenu());
            return;
        }
        if (command.equals("show all products")) {
            showAllItems();
            return;
        }
        else if(command.equals("login")){
            LoginRegisterMenu.getInstance().setPreviousMenu(ShopMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            return;
        }
        else if(command.equals("register")){
            LoginRegisterMenu.getInstance().setPreviousMenu(ShopMenu.getInstance());
            View.setCurrentMenu(LoginRegisterMenu.getInstance());
            return;
        }
        else if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(ShopMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
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
        // System.out.println(View.ANSI_YELLOW+"You are currently in the "+currentCategory.getName()+" category."+View.ANSI_RESET);
        System.out.println("view all categories"); //done
        System.out.println("open [category name]"); //done
        System.out.println("previous category");  //done
        System.out.println("filtering");  //done
        System.out.println("sorting");   //done
        System.out.println("show products");  //done
        System.out.println("show all products");   //done
        System.out.println("show product [product id]"); //done
        System.out.println("back");
    }

    private void openCategory(String name) {
        String ans=ItemAndCategoryController.getInstance().openCategory(name);
        System.out.println(ans);
        if(ans.startsWith("Successful")){
            categoryName=name;
        }
    }

    private void previousCategory() {
        categoryName = ItemAndCategoryController.getInstance().previousCategory(categoryName);
    }

    public void setCurrentCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCurrentCategory() {
        return categoryName;
    }

    public void showAllItems() {
        ArrayList<String> allItems = Database.getInstance().printFolderContent("Items");
        printList(allItems);
    }

    public void viewSubCategories() {
        printList(ItemAndCategoryController.getInstance().getCategoryByName(categoryName).getSubCategories());
    }

    public void viewAllCategories() {
        printList(Database.getInstance().printFolderContent("Categories"));
    }


}
