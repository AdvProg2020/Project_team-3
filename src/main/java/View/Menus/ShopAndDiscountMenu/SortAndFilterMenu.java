package View.Menus.ShopAndDiscountMenu;

import Controller.SortAndFilterController;
import View.Menus.Menu;
import View.Menus.View;

import java.util.regex.Matcher;

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
    public void run() {
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if (command.equals("show available filters")) {
            showAvailableFilters();
            return;
        }
        if (command.equals("show available sorts")) {
            showAvailableSorts();
            return;
        }
        if (command.equals("current sort")) {
            currentSort();
            return;
        }
        if (command.equals("disable sort")) {
            disableSort();
            return;
        }
        if (command.equals("current filters")) {
            currentFilters();
            return;
        }
        if (command.equals("default sort and filter")) {
            defaultSortAndFilter();
            return;
        }
        if(command.equals("back")){
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        matcher = View.getMatcher("sort (\\D+)", command);
        if (matcher.matches()) {
            sort(command);
            return;
        }
        System.out.println(View.ANSI_RED + "Invalid command." + View.ANSI_RESET);

    }

    @Override
    public void help() {
        System.out.println(View.ANSI_WHITE + "Enter your command in the following formats or type back to go to the shop menu." + View.ANSI_RESET);
        System.out.println(View.ANSI_CYAN+"show available filters"); //done
        System.out.println("filter [an available filter]");
        System.out.println("current filters"); //done
        System.out.println("disable filter [a selected filter]");
        System.out.println("show available sorts");   //done
        System.out.println("sort [an available sort]"); //done
        System.out.println("current sort");   //done
        System.out.println("disable sort");   //done
        System.out.println("default sort and filter"+View.ANSI_RESET);
    }

    public void showAvailableFilters() {
        System.out.println(SortAndFilterController.getInstance().showAllAvailableFilters());
    }

    public void showAvailableSorts() {
        System.out.println(SortAndFilterController.getInstance().showAllAvailableSorts());
    }

    public void currentSort() {
        System.out.println(SortAndFilterController.getInstance().showActiveSort());
    }

    public void disableSort() {
        SortAndFilterController.getInstance().disableSort();
    }

    public void sort(String command) {
        System.out.println(SortAndFilterController.getInstance().activateSort(command));
    }

    public void currentFilters() {
        System.out.println(SortAndFilterController.getInstance().showActiveFilters());
    }

    public void defaultSortAndFilter() {
        SortAndFilterController.getInstance().reset();
    }
}
