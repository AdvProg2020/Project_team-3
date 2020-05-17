package View.Menus.ShopAndDiscountMenu;

import Controller.SortAndFilterController;
import com.sun.source.tree.AssignmentTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class SortAndFilterMenuTest {

   @Test
   public void getInstance() {
      SortAndFilterMenu sortAndFilterMenu=SortAndFilterMenu.getInstance();
      assertNotNull(sortAndFilterMenu);
   }

   @Test
   public void run() {
   }

   @Test
   public void execute() {
   }

   @Test
   public void help() {
     SortAndFilterMenu.getInstance().help();
   }

   @Test
   public void showAvailableFilters() {
      SortAndFilterMenu.getInstance().showAvailableFilters();
   }

   @Test
   public void showAvailableSorts() {
      SortAndFilterMenu.getInstance().showAvailableSorts();
   }

   @Test
   public void currentSort() {
      SortAndFilterMenu.getInstance().sort("sort by price high to low");
      SortAndFilterMenu.getInstance().currentSort();
      SortAndFilterMenu.getInstance().sort("sort by ieeifji");
      SortAndFilterMenu.getInstance().currentSort();
   }

   @Test
   public void disableSort() {
      SortAndFilterMenu.getInstance().disableSort();
   }

   @Test
   public void sort() {
      SortAndFilterMenu.getInstance().sort("djwidjwi jdwijdiw");
      SortAndFilterMenu.getInstance().sort("sort by price high to low");
   }

   @Test
   public void currentFilters() {
      SortAndFilterMenu.getInstance().filterName("test");
      SortAndFilterMenu.getInstance().filterPriceRange(10,32);
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterName();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void defaultSortAndFilter() {
      SortAndFilterMenu.getInstance().defaultSortAndFilter();
      SortAndFilterMenu.getInstance().currentSort();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterName() {
      SortAndFilterMenu.getInstance().filterName("test");
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterName();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterCategory() {
      SortAndFilterMenu.getInstance().filterCategory("test");
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterCategory();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterAvailability() {
      SortAndFilterMenu.getInstance().filterAvailability();
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterAvailability();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterBrandName() {
      SortAndFilterMenu.getInstance().filterBrandName("test");
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterBrandName();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterPriceRange() {
      SortAndFilterMenu.getInstance().filterPriceRange(100,1000);
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterPriceRange();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterAttribute() {
   }

   @Test
   public void disableFilterSellerName() {
   }

   @Test
   public void filterAvailability() {
   }

   @Test
   public void filterBrandName() {
   }

   @Test
   public void filterCategory() {
   }

   @Test
   public void filterName() {
   }

   @Test
   public void filterPriceRange() {
   }

   @Test
   public void filterAttribute() {
   }

   @Test
   public void filterSellerName() {
   }
}
