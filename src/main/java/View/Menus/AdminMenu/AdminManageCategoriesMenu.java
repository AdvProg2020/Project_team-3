package View.Menus.AdminMenu;

import ControllerTest.Database;
import ControllerTest.ItemAndCategoryController;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageCategoriesMenu extends UserMenu {
    private static AdminManageCategoriesMenu adminManageCategoriesMenu;
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
        if(command.equals("manage categories")){
            showAllCategories();
            return;
        }

        matcher=View.getMatcher("remove (\\S+)",command);
        if(matcher.matches()){
            removeCategory(matcher.group(1));
            return;
        }

        matcher=View.getMatcher("add (\\S+)",command);
        if(matcher.matches()){
            addCategory(matcher.group(1));
            return;
        }

        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the admin menu."+View.ANSI_RESET);
        System.out.println("manage categories"); //done
        System.out.println("edit category [category name]");
        System.out.println("add [category name]");
        System.out.println("remove [category name]"); //done
    }
    private void showAllCategories() {
        ArrayList<String> allCategories = Database.getInstance().printFolderContent("Categories");
        printList(allCategories);
    }

    private void addCategory(String name) {
        System.out.println("enter category attributes.enter next to continue.");
        ArrayList<String> attributes=new ArrayList<>();
        String attribute;
        while(true){
            attribute=View.getRead().nextLine();
            if(attribute.equals("next")){
                break;
            }
            attributes.add(attribute);
        }
        System.out.println("enter father category name.(press enter if you want to make a main category)");
        String fatherCategory=View.getRead().nextLine();
        System.out.println("are you sure you want to make this category.(yes or no)");
        String command=View.getRead().nextLine();
        if(command.equals("no")){
            return;
        }
        if(fatherCategory.isEmpty()){
        System.out.println(ItemAndCategoryController.getInstance().addCategory(name,attributes,fatherCategory));
        }else {
            System.out.println((ItemAndCategoryController.getInstance().addCategory(name,attributes,fatherCategory)));
        }
    }

    private void editCategory() {

    }

    private void removeCategory(String name) {
        System.out.println(ItemAndCategoryController.getInstance().removeCategory(name));
    }


}
