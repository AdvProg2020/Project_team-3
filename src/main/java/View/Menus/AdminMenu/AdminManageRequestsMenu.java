package View.Menus.AdminMenu;

import Controller.Database;
import Controller.RequestController;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageRequestsMenu extends UserMenu {
    private static AdminManageRequestsMenu adminManageRequestsMenu;

    private AdminManageRequestsMenu() {
    }

    public static AdminManageRequestsMenu getInstance() {
        if (adminManageRequestsMenu == null)
            adminManageRequestsMenu = new AdminManageRequestsMenu();
        return adminManageRequestsMenu;
    }

    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("manage requests")) {
            showAllRequests();
        } else if (command.equals("back")) {
            View.setCurrentMenu(AdminMenu.getInstance());
        } else if (command.equals("help")) {
            help();
        }
        matcher = View.getMatcher("detail request (\\S+)", command);
        if (matcher.matches()) {
            detailRequest(matcher.group(1));
        }
        matcher = View.getMatcher("accept (\\S+)", command);
        if (matcher.matches()) {
            acceptRequestId(matcher.group(1));
        }

        matcher = View.getMatcher("decline (\\S+)", command);
        if (matcher.matches()) {
            declineRequestId(matcher.group(1));
        }

    }

    public void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the admin menu." + View.ANSI_RESET);
        System.out.println("manage requests");           //done but need test
        System.out.println("detail request [request id]");  //done but request info need to be added
        System.out.println("accept [request id]");    //done but need test
        System.out.println("decline [request id]");   //done but need test
    }

    private void showAllRequests() {
        ArrayList<String> allRequests = Database.getInstance().printFolderContent("Requests");
        printList(allRequests);
    }

    private void detailRequest(String id) {
        System.out.println(RequestController.getInstance().getRequestDetail(id));
    }

    private void acceptRequestId(String id) {
        System.out.println(RequestController.getInstance().acceptRequest(id));
    }

    private void declineRequestId(String id) {
        System.out.println(RequestController.getInstance().declineRequest(id));
    }

}
