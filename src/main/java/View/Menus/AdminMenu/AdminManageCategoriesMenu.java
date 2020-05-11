package View.Menus.AdminMenu;

import View.Menus.UserMenu;
import View.Menus.View;

import java.util.regex.Matcher;

public class AdminManageCategoriesMenu extends UserMenu {
    private static AdminManageCategoriesMenu adminManageCategoriesMenu;
    private int optionCount = 6;
    private AdminManageCategoriesMenu(){ }

    public static AdminManageCategoriesMenu getInstance(){
        if(adminManageCategoriesMenu==null)
            adminManageCategoriesMenu = new AdminManageCategoriesMenu();
        return adminManageCategoriesMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }
    @Override
    public void execute(String command) {
        Matcher matcher;

    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the admin menu."+View.ANSI_RESET);
        System.out.println("manage categories");
        System.out.println("edit category [category name]");
        System.out.println("add [category name]");
        System.out.println("remove [category name]");
    }
    private void showAllCategories() {
        //set current menu to category menu
    }

    private void addCategory() {

    }

    private void editCategory() {

    }

    private void removeCategory() {

    }


}
