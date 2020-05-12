package View.Menus.AdminMenu;

import ControllerTest.Database;
import ControllerTest.UserController;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageUsersMenu extends UserMenu {
    private static AdminManageUsersMenu adminManageUsersMenu;
    private AdminManageUsersMenu(){ }

    public static AdminManageUsersMenu getInstance(){
        if(adminManageUsersMenu==null)
            adminManageUsersMenu = new AdminManageUsersMenu();
        return adminManageUsersMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }
    @Override
    public void execute(String command) {
        Matcher matcher;
        matcher = View.getMatcher("delete user (\\S+)", command);
        if (matcher.matches()) {
            deleteUser(matcher.group(1));
        }
        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewPersonalInfo(matcher.group(1));
        }
        else if (command.equals("create manager profile")) {
            registerAdmin();
        }
        matcher = View.getMatcher("change type (\\S+) (\\S+)", command);
        if (matcher.matches()) {
            changeType(matcher.group(1), matcher.group(2));
        }
        if(command.equals("help")){
            help();
        }
        if(command.equals("back")){
            View.setCurrentMenu(AdminMenu.getInstance());
        }
        else if (command.equals("manage users")) {
            printUsers();
        }
    }
    public void help(){
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the admin menu."+View.ANSI_RESET);
        System.out.println("manage users");
        System.out.println("delete user [username]"); //done
        System.out.println("view [username]");    //done
        System.out.println("create manager profile"); //done
    }

    private void deleteUser(String username) {
        System.out.println(UserController.getInstance().deleteUser(username));
    }

    private void changeType(String username, String type) {
        System.out.println(UserController.getInstance().changeTypeTo(username, type));
    }
    private void printUsers() {
        ArrayList<String> allUserNames = Database.getInstance().printFolderContent("Users");
        printList(allUserNames);
    }
}
