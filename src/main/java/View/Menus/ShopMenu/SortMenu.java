package View.Menus.ShopMenu;

import View.Menus.Menu;
import View.Menus.View;

public class SortMenu extends Menu {
    private static SortMenu sortMenu;

    private SortMenu() {
    }

    public static SortMenu getInstance() {
        if (sortMenu == null)
            sortMenu = new SortMenu();
        return sortMenu;
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the shop menu." + View.ANSI_RESET);
        System.out.println("show available sorts");   //done
        System.out.println("sort [an available sort]");
        System.out.println("current sort");
        System.out.println("disable sort");
    }

    @Override
    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("show available sorts")) {
            showAvailableSorts();
        } else if (command.startsWith("sort ")) {

        } else if (command.equals("current sort")) {

        } else if (command.equals("disable sort")) {

        } else if (command.equals("help")) {
            help();
        } else if (command.equals("back")) {
            View.setCurrentMenu(ShopMenu.getInstance());
        } else {
            System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
        }
    }

    public void showAvailableSorts() {

    }
}
