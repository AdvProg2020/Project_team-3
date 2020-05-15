package View.Menus.AdminMenu;

import Controller.Database;
import Controller.SaleAndDiscountCodeController;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminManageDiscountCodesMenu extends UserMenu {
    private static AdminManageDiscountCodesMenu adminManageDiscountCodesMenu;

    private AdminManageDiscountCodesMenu() {
    }

    public static AdminManageDiscountCodesMenu getInstance() {
        if (adminManageDiscountCodesMenu == null)
            adminManageDiscountCodesMenu = new AdminManageDiscountCodesMenu();
        return adminManageDiscountCodesMenu;
    }

    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;

        matcher = View.getMatcher("view discount code (\\S+)", command);
        if (matcher.matches()) {
            viewDiscountCode(matcher.group(1));
            return;
        }
        matcher = View.getMatcher("edit discount code (\\S+)", command);
        if (matcher.matches()) {
            editDiscountCode(matcher.group(1));
            return;
        }
        matcher = View.getMatcher("remove discount code (\\S+)", command);
        if (matcher.matches()) {
            deleteDiscountCode(matcher.group(1));
            return;
        } else if (command.equals("view discount codes")) {
            viewAllDiscountCodes();
            View.setCurrentMenu(AdminManageDiscountCodesMenu.getInstance());
            return;
        }
        if (command.equals("help")) {
            help();
            return;
        }
        else if (command.equals("create discount code")) {
            AdminMenu.getInstance().createDiscountCode();
            return;
        }
        if (command.equals("back")) {
            View.setCurrentMenu(AdminMenu.getInstance());
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
        System.out.println("view discount codes");         //done but need test
        System.out.println("create discount code");
        System.out.println("view discount code [id]");   //done
        System.out.println("edit discount code [id]");
        System.out.println("remove discount code [id]");
    }

    private void viewDiscountCode(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().printDiscount(id));
    }

    private void editDiscountCode(String discountID) {
        System.out.println("Enter -edit date- if you wish to change the ending date.\nEnter -edit offpercent- if you wish to change the off percentage.\nEnter -edit usage- to edit how many times this code can be used.\n" +
                "Enter -edit max- to edit the maximum amount of discount");
        String command = View.getRead().nextLine();
        if (command.equals("edit date")) {
            LocalDateTime date = getDate("Enter a valid day as the end time in the following format: dd/MM/yyyy HH:mm");
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountID, date));
            return;
        } else if (command.equals("edit offpercent")) {
            int percentage = readNumber(101, "please enter new discount percentage(Integer):");
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountID, percentage));
            return;
        } else if (command.equals("edit usage")){
            int usage = readNumber(1000,"please enter e new usage count (Integer,max:1000):");
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountID,usage));
            return;
        } else if(command.equals("edit max")){
            double max = readNumber(-1D,"please enter a new max. discount (Double):");
            System.out.println(SaleAndDiscountCodeController.getInstance().editDiscountCodeMaxDiscount(discountID,max));
        }
        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
    }

    private void deleteDiscountCode(String id) {
        System.out.println(SaleAndDiscountCodeController.getInstance().deleteDiscountCode(id));
    }

    private void viewAllDiscountCodes() {
        ArrayList<String> allDiscountCodes = Database.getInstance().printFolderContent("DiscountCodes");
        printList(allDiscountCodes);
    }
}
