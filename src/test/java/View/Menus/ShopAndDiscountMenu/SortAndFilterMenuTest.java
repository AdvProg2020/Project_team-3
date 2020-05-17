package View.Menus.ShopAndDiscountMenu;

import Controller.SortAndFilterController;
import View.Menus.View;
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
   public void execute() {
      SortAndFilterMenu.getInstance().execute("show available sorts");
      SortAndFilterMenu.getInstance().execute("show available filters");
      SortAndFilterMenu.getInstance().execute("show a");
      SortAndFilterMenu.getInstance().execute("sort by price high to low");
      SortAndFilterMenu.getInstance().execute("current sort");
      SortAndFilterMenu.getInstance().execute("filter by name ali");
      SortAndFilterMenu.getInstance().execute("filter by price 10 to 1000");
      SortAndFilterMenu.getInstance().execute("filter by price 173972937937197391739173917391739179371 to 830183810381038103810831083018301830183018308103801830183018");
      SortAndFilterMenu.getInstance().execute("current filters");
      SortAndFilterMenu.getInstance().execute("filter by availability");
      SortAndFilterMenu.getInstance().execute("disable filter by brand");
      SortAndFilterMenu.getInstance().execute("disable filter by availability");
      SortAndFilterMenu.getInstance().execute("disable sort");
      SortAndFilterMenu.getInstance().execute("back");
      SortAndFilterMenu.getInstance().execute("disable filter by name");
      SortAndFilterMenu.getInstance().execute("default sort and filter");
      SortAndFilterMenu.getInstance().execute("disable filter by price");
      SortAndFilterMenu.getInstance().execute("disable filter by seller");
      SortAndFilterMenu.getInstance().execute("disable filter by category");
      SortAndFilterMenu.getInstance().execute("filter by attribute test test");
      SortAndFilterMenu.getInstance().execute("disable filter by attribute");
      SortAndFilterMenu.getInstance().execute("filter by category test");
      SortAndFilterMenu.getInstance().execute("filter by brand test");
      SortAndFilterMenu.getInstance().execute("filter by seller test");
      SortAndFilterMenu.getInstance().execute("current filters");
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
      SortAndFilterMenu.getInstance().filterAttribute("test","test");
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterAttribute();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void disableFilterSellerName() {
      SortAndFilterMenu.getInstance().filterSellerName("test");
      SortAndFilterMenu.getInstance().currentFilters();
      SortAndFilterMenu.getInstance().disableFilterSellerName();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterAvailability() {
      SortAndFilterMenu.getInstance().filterAvailability();
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterBrandName() {
      SortAndFilterMenu.getInstance().filterBrandName("tets");
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterCategory() {
      SortAndFilterMenu.getInstance().filterCategory("test");
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterName() {
      SortAndFilterMenu.getInstance().filterName("test");
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterPriceRange() {
      SortAndFilterMenu.getInstance().filterPriceRange(10,100);
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterAttribute() {
      SortAndFilterMenu.getInstance().filterAttribute("test","test");
      SortAndFilterMenu.getInstance().currentFilters();
   }

   @Test
   public void filterSellerName() {
      SortAndFilterMenu.getInstance().filterSellerName("test");
      SortAndFilterMenu.getInstance().currentFilters();
   }
}
