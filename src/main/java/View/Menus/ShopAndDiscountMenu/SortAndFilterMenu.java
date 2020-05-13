package View.Menus.ShopAndDiscountMenu;

import Controller.SearchAndFilterController;
import View.Menus.Menu;
import View.Menus.View;

public class SortAndFilterMenu extends Menu {
    private static SortAndFilterMenu sortAndFilterMenu;

    private SortAndFilterMenu() {
    }

    public static SortAndFilterMenu getInstance() {
        if (sortAndFilterMenu == null)
            sortAndFilterMenu = new SortAndFilterMenu();
        return sortAndFilterMenu;
    }

    @Override
    public void help() {
        System.out.println(View.ANSI_WHITE+"Enter your command in the following formats or type back to go to the shop menu."+View.ANSI_RESET);
        System.out.println("show available filters"); //done
        System.out.println("filter [an available filter]");
        System.out.println("current filters");
        System.out.println("disable filter [a selected filter]");
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
        if (command.equals("show available filters")) {
            showAvailableFilters();
            return;
        }
        if(command.equals("show available sorts")){
            showAvailableSorts();
            return;
        }
            System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    public void showAvailableFilters() {
        System.out.println(SearchAndFilterController.getInstance().showAllAvailableFilters());
    }
    public void showAvailableSorts(){
        System.out.println(SearchAndFilterController.getInstance().showAllAvailableSorts());
    }

}
