package View.Menus.ShopMenu;

import Controller.SearchAndFilter;
import View.Menus.Menu;
import View.Menus.View;

public class FilterMenu extends Menu {
    private static FilterMenu filterMenu;

    private FilterMenu() {
    }

    public static FilterMenu getInstance() {
        if (filterMenu == null)
            filterMenu = new FilterMenu();
        return filterMenu;
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the shop menu."+View.ANSI_RESET);
        System.out.println("show available filters");
        System.out.println("filter [an available filter]");
        System.out.println("current filters");
        System.out.println("disable filter [a selected filter]");
    }

    @Override
    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if (command.equals("show available filters")) {
            showAvailableFilters();
        } else if (command.startsWith("filter ")) {

        } else if (command.equals("current filters")) {

        } else if (command.startsWith("disable filter ")) {

        } else if(command.equals("help")){
            help();
        } else if(command.equals("back")){
            View.setCurrentMenu(ShopMenu.getInstance());
        }  else {
            System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);
        }
    }

    public void showAvailableFilters() {
        System.out.println(SearchAndFilter.getInstance().showAllFilters());
    }

}
