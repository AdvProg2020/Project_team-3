package Project.View.CLI.AdminMenu;

import Project.Controller.Database;
import Project.Controller.UserController;
import Project.View.CLI.LoginRegisterMenu;
import Project.View.CLI.MainMenu;
import Project.View.CLI.UserMenu;
import Project.View.CLI.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageUsersMenu extends UserMenu {
    private static AdminManageUsersMenu adminManageUsersMenu;

    private AdminManageUsersMenu() {
    }

    public static AdminManageUsersMenu getInstance() {
        if (adminManageUsersMenu == null)
            adminManageUsersMenu = new AdminManageUsersMenu();
        return adminManageUsersMenu;
    }

    public void run() {
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
            return;
        }
        matcher = View.getMatcher("view (\\S+)", command);
        if (matcher.matches()) {
            viewPersonalInfo(matcher.group(1));
            return;
        } else if (command.equals("create manager profile")) {
            LoginRegisterMenu.getInstance().registerAdmin();
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        if (command.equals("back")) {
            View.setCurrentMenu(AdminMenu.getInstance());
            return;
        } else if (command.equals("manage users")) {
            printUsers();
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
        System.out.println("manage users");
        System.out.println("delete user [username]"); //done
        System.out.println("view [username]");    //done
        System.out.println("create manager profile"); //done
    }

    private void deleteUser(String username) {
        System.out.println(UserController.getInstance().deleteUser(username));
    }



    private void printUsers() {
        ArrayList<String> allUserNames = Database.getInstance().printFolderContent("Users");
        printList(allUserNames);
    }
}
