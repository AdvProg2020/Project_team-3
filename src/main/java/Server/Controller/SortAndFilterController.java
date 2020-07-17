package Server.Controller;

import Server.Model.Item;

import java.util.ArrayList;

public class SortAndFilterController {
   int activeSort;
   Boolean filterPriceRange = false;
   Boolean filterCategoryName = false;
   Boolean filterBrand = false;
   Boolean filterName = false;
   Boolean filterAvailability = false;
   Boolean filterAttribute = false;
   Boolean filterSellerName = false;
   Boolean filterSale = false;
   double minPrice;
   double maxPrice;
   String categoryName;
   String brandName;
   String name;
   String attributeKey;
   String attributeValue;
   String sellerName;
   public static SortAndFilterController sortAndFilterController;

   private SortAndFilterController() {
   }

   public static SortAndFilterController getInstance() {
      if (sortAndFilterController == null) sortAndFilterController = new SortAndFilterController();
      return sortAndFilterController;
   }

   public ArrayList<String> show(String categoryName) {
      ArrayList<String> allItemsId = ItemAndCategoryController.getInstance().getCategoryItems(categoryName);
      return show(allItemsId);
   }

   public ArrayList<String> show(ArrayList<String> allItemsId) {
      ArrayList<String> filteredItems = new ArrayList<>();
      for (String id : allItemsId) {
         if (isAccepted(id))
            filteredItems.add(id);
      }
      if (activeSort == 0) {
         filteredItems = SortComparators.getInstance().sortByView(filteredItems);
      } else if (activeSort == 1) {
         filteredItems = SortComparators.getInstance().sortByPriceLowToHigh(filteredItems);
      } else if (activeSort == 2) {
         filteredItems = SortComparators.getInstance().sortByPriceHighToLow(filteredItems);
      } else if (activeSort == 3) {
         filteredItems = SortComparators.getInstance().sortByRating(filteredItems);
      } else if (activeSort == 4) {
         filteredItems = SortComparators.getInstance().SortByCommentCount(filteredItems);
      } else if (activeSort == 5) {
         filteredItems = SortComparators.getInstance().SortByDate(filteredItems);
      }
      ArrayList<String> itemIdWithName = new ArrayList<>();
      for (String filteredItem : filteredItems) {
         itemIdWithName.add(ItemAndCategoryController.getInstance().getItemById(filteredItem).showIdWithName());
      }
      return itemIdWithName;
   }

   public String activateSort(String sortName) {
      if (sortName.equals("sort by price low to high")) {
         activeSort = 1;
         return "Successful";
      } else if (sortName.equals("sort by price high to low")) {
         activeSort = 2;
         return "Successful";
      } else if (sortName.equals("sort by rating")) {
         activeSort = 3;
         return "Successful";
      } else if (sortName.equals("sort by comment count")) {
         activeSort = 4;
         return "Successful";
      } else if (sortName.equals("sort by date")) {
         activeSort = 5;
         return "Successful";
      }
      return "Error: invalid sort";
   }

   public void disableSort() {
      activeSort = 0;
   }

   public void activateFilterSale(){
      filterSale=true;
   }

   public void activateFilterPriceRange(double minPrice, double maxPrice) {
      filterPriceRange = true;
      this.minPrice = minPrice;
      this.maxPrice = maxPrice;
   }

   public void activateFilterCategoryName(String categoryName) {
      filterCategoryName = true;
      this.categoryName = categoryName;
   }

   public void activateFilterBrandName(String brandName) {
      filterBrand = true;
      this.brandName = brandName;
   }

   public void activateFilterName(String name) {
      filterName = true;
      this.name = name;
   }

   public void activateFilterAvailability() {
      filterAvailability = true;
   }

   public void activateFilterAttribute(String attributeKey,String attributeValue) {
      filterAttribute = true;
      this.attributeKey=attributeKey;
      this.attributeValue=attributeValue;
   }

   public void activateFilterSellerName(String sellerName) {
      filterSellerName = true;
      this.sellerName = sellerName;
   }

   public void disableFilterSale(){
      filterSale=false;
   }

   public void disableFilterPriceRange() {
      filterPriceRange = false;
   }

   public void disableFilterCategoryName() {
      filterCategoryName = false;
   }

   public void disableFilterBrandName() {
      filterBrand = false;
   }

   public void disableFilterName() {
      filterName = false;
   }

   public void disableFilterAvailability() {
      filterAvailability = false;
   }

   public void disableFilterAttribute() {
      filterAttribute = false;
   }

   public void disableFilterSellerName() {
      filterSellerName = false;
   }

   private boolean isAccepted(String itemId) {
      Item item = ItemAndCategoryController.getInstance().getItemById(itemId);
      if (item == null) {
         return false;
      }
      int inStock = item.getInStock();
      String itemName = item.getName();
      if (item == null) {
         return false;
      }
      double price = item.getPriceWithSale();
      if ((filterPriceRange == true) && ((price < minPrice) || (price > maxPrice))) {
         return false;
      }
      if ((filterCategoryName == true) && (!item.getCategoryName().equals(categoryName))) {
         return false;
      }
      if ((filterBrand == true) && (!item.getBrand().equals(brandName))) {
         return false;
      }
      if ((filterName == true) && (itemName.contains(name) == false)) {
         return false;
      }
      if ((filterAvailability == true) && (inStock == 0)) {
         return false;
      }
      if ((filterAttribute == true) && (item.getAttributes().containsKey(attributeKey))&&(!item.getAttributes().get(attributeKey).equals(attributeValue))) {
         return false;
      }
      if ((filterAttribute == true) && !(item.getAttributes().containsKey(attributeKey))) {
         return false;
      }
      if ((filterSellerName == true) && (!item.getSellerName().equals(sellerName))) {
         return false;
      }
      if((filterSale)&&(item.isInSale()==false)){
         return false;
      }
      return true;
   }

   public String showActiveFilters() {
      String ans = "";
      if (filterBrand == true) {
         ans += "filter by brand";
      }
      if (filterCategoryName == true) {
         ans += "\nfilter by category name: " + categoryName;
      }
      if (filterPriceRange == true) {
         ans += "\nfilter by price range min=" + minPrice + " max=" + maxPrice;
      }
      if (filterName == true) {
         ans += "\nfilter by name: " + name;
      }
      if (filterAttribute == true) {
         ans += "\nfilter by attribute attribute Key: " + attributeKey+" attribute Value: "+attributeValue;
      }
      if (filterSellerName == true) {
         ans += "\nfilter by seller: " + sellerName;
      }
      if(filterAvailability == true){
         ans +="\nfilter by availability";
      }
      if (ans.isEmpty()) {
         ans = "You have no active filter(s)";
      }
      return ans;
   }

   public String showActiveSort() {
      if (activeSort == 1) {
         return "sort by price low to high";
      } else if (activeSort == 2) {
         return "sort by price high to low";
      } else if (activeSort == 3) {
         return "sort by rating";
      } else if (activeSort == 4) {
         return "sort by comment count";
      }else if (activeSort == 5){
         return "sort by date";
      }
      return "You have no active sort , the items will be sorted by view count.";
   }

   public String showAllAvailableFilters() {
      return "filter by price [min] to [max]" +
              "\nfilter by category [name]" +
              "\nfilter by brand [name]" +
              "\nfilter by name [name]" +
              "\nfilter by availability" +
              "\nfilter by attribute [key] [value]" +
              "\nfilter by seller [seller name]"+
              "\nfilter sale";
   }

   public String showAllAvailableSorts() {
      //sort number 0 is the default sort by view
      return "sort by price low to high" +     //sort number 1
              "\nsort by price high to low" +  //sort number 2
              "\nsort by rating" +             //sort number 3
              "\nsort by comment count"+       //sort number 4
              "\nsort by date";                //sort number 5
   }

   public void reset() {
      filterBrand = false;
      filterCategoryName = false;
      filterPriceRange = false;
      filterName = false;
      filterAttribute = false;
      filterAvailability = false;
      activeSort = 0;
      filterAvailability = false;
      filterSale=false;
      filterSellerName=false;
   }

   public Boolean getFilterAttribute() {
      return filterAttribute;
   }
   public Boolean getFilterBrand() {
      return filterBrand;
   }
   public Boolean getFilterCategoryName() {
      return filterCategoryName;
   }
   public Boolean getFilterAvailability() {
      return filterAvailability;
   }
   public Boolean getFilterName() {
      return filterName;
   }
   public Boolean getFilterPriceRange() {
      return filterPriceRange;
   }
   public Boolean getFilterSellerName() {
      return filterSellerName;
   }
   public String getMinPrice() {
      return Double.toString(minPrice);
   }
   public String getMaxPrice() {
      return  Double.toString(maxPrice);
   }
   public String getCategoryName() {
      return categoryName;
   }
   public String getBrandName() {
      return brandName;
   }
   public String getName() {
      return name;
   }
   public String getAttributeKey() {
      return attributeKey;
   }
   public String getAttributeValue() {
      return attributeValue;
   }
   public String getSellerName() {
      return sellerName;
   }
}

