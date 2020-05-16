package View.Menus.AdminMenu;

import Controller.Database;
import Controller.ItemAndCategoryController;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageCategoriesMenu extends UserMenu {
    private static AdminManageCategoriesMenu adminManageCategoriesMenu;

    private AdminManageCategoriesMenu() {
    }

    public static AdminManageCategoriesMenu getInstance() {
        if (adminManageCategoriesMenu == null)
            adminManageCategoriesMenu = new AdminManageCategoriesMenu();
        return adminManageCategoriesMenu;
    }

    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("manage categories")) {
            showAllCategories();
            return;
        }
        if (command.equals("back")) {
            View.setCurrentMenu(AdminMenu.getInstance());
            return;
        }
        matcher = View.getMatcher("remove (\\S+)", command);
        if (matcher.matches()) {
            removeCategory(matcher.group(1));
            return;
        }

        matcher = View.getMatcher("add (\\S+)", command);
        if (matcher.matches()) {
            addCategory(matcher.group(1));
            return;
        }
        matcher = View.getMatcher("edit category (\\S+)",command);
        if(matcher.matches()){
            editCategory(matcher.group(1));
            return;
        }
        if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }

        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
    }

    public void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the admin menu." + View.ANSI_RESET);
        System.out.println("manage categories"); //done
        System.out.println("edit category [category name]");
        System.out.println("add [category name]");  //done
        System.out.println("remove [category name]"); //done
    }

    private void showAllCategories() {
        ArrayList<String> allCategories = Database.getInstance().printFolderContent("Categories");
        printList(allCategories);
    }

    private void addCategory(String name) {
        System.out.println("Enter category attributes. Enter next to continue.");
        ArrayList<String> attributes = new ArrayList<>();
        String attribute;
        while (true) {
            attribute = View.getRead().nextLine();
            if (attribute.equals("next")) {
                break;
            }
            attributes.add(attribute);
        }
        System.out.println("Enter father category name. [press enter if you want to make a main category]");
        String fatherCategory = View.getRead().nextLine();
        System.out.println("Are you sure you want to make this category? [*anything*/no]");
        String command = View.getRead().nextLine();
        if (command.equals("no")) {
            return;
        }
        if (fatherCategory.isBlank()) {
            System.out.println(ItemAndCategoryController.getInstance().addCategory(name, attributes,"Main"));
        } else {
            System.out.println((ItemAndCategoryController.getInstance().addCategory(name, attributes, fatherCategory)));
        }
    }

    private void editCategory(String categoryName) {
     if(categoryName.equals("Main")){
         System.out.println("Error: you cant edit Main");
         return;
     }
     if(!ItemAndCategoryController.getInstance().isThereCategoryWithName(categoryName)){
         System.out.println("Error: invalid category name");
         return;
     }
     System.out.println(View.ANSI_BLUE+"enter your command in one of these formats\nadd attribute [attribute]\nedit name [name]\nback"+View.ANSI_RESET);
     String command=View.getRead().nextLine();
     if(command.equals("back")){
         return;
     }
     Matcher matcher=View.getMatcher("add attribute ([A-Za-z0-9]+)",command);
     if(matcher.matches()){
         addAttribute(categoryName,matcher.group(1));
         return;
     }
     matcher=View.getMatcher("edit name (\\S+)",command);
     if(matcher.matches()){
         renameCategory(categoryName,matcher.group(1));
         return;
     }
     System.out.println(View.ANSI_RED+"invalid command"+View.ANSI_RESET);
    }

    private void removeCategory(String name) {
        if(name.equals("Main")){
            System.out.println("Error: you cant delete Main");
        }
        System.out.println(ItemAndCategoryController.getInstance().removeCategory(name));
    }

    private void renameCategory(String oldName,String newName){
       System.out.println(ItemAndCategoryController.getInstance().renameCategory(oldName,newName));
    }

    private void addAttribute(String categoryName,String newAttribute){
        System.out.println(ItemAndCategoryController.getInstance().addAttributeToCategory(categoryName,newAttribute));
    }

}
