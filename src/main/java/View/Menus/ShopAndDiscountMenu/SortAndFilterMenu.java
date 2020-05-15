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
      if (command.equals("back")) {
         View.setCurrentMenu(ShopMenu.getInstance());
         return;
      }
      if (command.equals("disable filter by availability")) {
         disableFilterAvailability();
         return;
      }
      if (command.equals("filter by availability")) {
         filterAvailability();
         return;
      }
      if (command.equals("disable filter by price")) {
         disableFilterPriceRange();
         return;
      }
      if (command.equals("disable filter by name")) {
         disableFilterName();
         return;
      }
      if (command.equals("disable filter by brand")) {
         disableFilterBrandName();
         return;
      }
      if (command.equals("disable filter by category")) {
         disableFilterCategory();
         return;
      }
      if (command.equals("disable filter by attribute")) {
         disableFilterAttribute();
         return;
      }
      if (command.equals("disable filter by seller")) {
         disableFilterSellerName();
         return;
      }
      matcher = View.getMatcher("filter by brand (\\S+)", command);
      if (matcher.matches()) {
         filterBrandName(matcher.group(1));
         return;
      }
      matcher = View.getMatcher("filter by seller (\\S+)", command);
      if (matcher.matches()) {
         filterSellerName(matcher.group(1));
         return;
      }
      matcher = View.getMatcher("filter by category (\\S+)", command);
      if (matcher.matches()) {
         filterCategory(matcher.group(1));
         return;
      }
      matcher = View.getMatcher("filter by name (\\S+)", command);
      if (matcher.matches()) {
         filterName(matcher.group(1));
         return;
      }
      matcher = View.getMatcher("filter by attribute (\\S+) (\\S+)", command);
      if (matcher.matches()) {
         filterAttribute(matcher.group(1),matcher.group(2));
         return;
      }
      matcher = View.getMatcher("filter by price (\\d+) to (\\d+)", command);
      if (matcher.matches()) {
         try {
            int min = Integer.parseInt(matcher.group(1));
            int max = Integer.parseInt(matcher.group(2));
            filterPriceRange(min, max);
            return;
         } catch (Exception exception) {
            System.out.println("please enter Integer number for price range");
            return;
         }
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
      System.out.println(View.ANSI_CYAN + "show available filters"); //done
      System.out.println("filter [an available filter]");
      System.out.println("current filters"); //done
      System.out.println("disable filter [a selected filter]");
      System.out.println("show available sorts");   //done
      System.out.println("sort [an available sort]"); //done
      System.out.println("current sort");   //done
      System.out.println("disable sort");   //done
      System.out.println("default sort and filter" + View.ANSI_RESET);
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

   public void disableFilterName() {
      SortAndFilterController.getInstance().disableFilterName();
   }

   public void disableFilterCategory() {
      SortAndFilterController.getInstance().disableFilterCategoryName();
   }

   public void disableFilterAvailability() {
      SortAndFilterController.getInstance().disableFilterAvailability();
   }

   public void disableFilterBrandName() {
      SortAndFilterController.getInstance().disableFilterBrandName();
   }

   public void disableFilterPriceRange() {
      SortAndFilterController.getInstance().disableFilterPriceRange();
   }

   public void disableFilterAttribute() {
      SortAndFilterController.getInstance().disableFilterAttribute();
   }

   public void disableFilterSellerName() {
      SortAndFilterController.getInstance().disableFilterSellerName();
   }

   public void filterAvailability() {
      SortAndFilterController.getInstance().activateFilterAvailability();
   }

   public void filterBrandName(String brand) {
      SortAndFilterController.getInstance().activateFilterBrandName(brand);
   }

   public void filterCategory(String categoryName) {
      SortAndFilterController.getInstance().activateFilterCategoryName(categoryName);
   }

   public void filterName(String name) {
      SortAndFilterController.getInstance().activateFilterName(name);
   }

   public void filterPriceRange(int min, int max) {
      SortAndFilterController.getInstance().activateFilterPriceRange(min, max);
   }

   public void filterAttribute(String attributeKey,String attributeValue) {
      SortAndFilterController.getInstance().activateFilterAttribute(attributeKey,attributeValue);
   }

   public void filterSellerName(String sellerName) {
      SortAndFilterController.getInstance().activateFilterSellerName(sellerName);
   }

}
