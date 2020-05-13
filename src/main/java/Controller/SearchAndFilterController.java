package Controller;

import Model.Item;

import java.util.ArrayList;

public class SearchAndFilterController {
    int activeSort;
    Boolean filterPriceRange = false;
    Boolean filterCategoryName = false;
    Boolean filterBrand = false;
    double minPrice;
    double maxPrice;
    String categoryName;
    String brandName;
    public static SearchAndFilterController searchAndFilterController;

    private SearchAndFilterController() {
    }

    public static SearchAndFilterController getInstance() {
        if (searchAndFilterController == null) searchAndFilterController = new SearchAndFilterController();
        return searchAndFilterController;
    }

    public ArrayList<String> show(String categoryName){
        ArrayList<String> allItemsId=ItemAndCategoryController.getInstance().getCategoryItems(categoryName);
        return show(allItemsId);
    }
    public ArrayList<String> show(ArrayList<String> allItemsId) {
        ArrayList<String> filteredItems = new ArrayList<>();
        for (String id : allItemsId) {
            if (isAccepted(id))
                filteredItems.add(id);
        }
        if (activeSort == 0) {
            filteredItems = SortComperators.getInstance().sortByView(filteredItems);
        } else if (activeSort == 1) {
            filteredItems = SortComperators.getInstance().sortByPriceLowToHigh(filteredItems);
        } else if (activeSort == 2) {
            filteredItems = SortComperators.getInstance().sortByPriceHighToLow(filteredItems);
        } else if (activeSort == 3) {
            filteredItems = SortComperators.getInstance().sortByRating(filteredItems);
        } else if (activeSort == 4) {
            filteredItems = SortComperators.getInstance().SortByCommentCount(filteredItems);
        }
        return filteredItems;
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
        }
        return "Error: invalid sort";
    }

    public void disableSort() {
        activeSort = 0;
    }

    public void activateFilterPriceRange(int minPrice, int maxPrice) {
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

    public void disableFilterPriceRange() {
        filterPriceRange = false;
    }

    public void disableFilterCategoryName() {
        filterCategoryName = false;
    }

    public void disableFilterBrandName() {
        filterBrand = false;
    }

    private boolean isAccepted(String itemId) {
        Item item = ItemAndCategoryController.getInstance().getItemById(itemId);
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
        if (ans.isBlank()) {
            ans = "you have No active filter";
        }
        return ans;
    }

    public String showActiveSort() {
        if (activeSort == 0) {
            return "you have no active sort , the items will be sorted by view count";
        } else if (activeSort == 1) {
            return "sort by price low to high";
        } else if (activeSort == 2) {
            return "sort by price high to low";
        } else if (activeSort == 3) {
            return "sort by rating";
        } else if (activeSort == 4) {
            return "sort by comment count";
        }
        return "";
    }

    public String showAllAvailableFilters() {
        return "filter price [min] to [max]" +
                "\nfilter by category name" +
                "\nfilter by brand name";

    }

    public String showAllAvailableSorts() {
        //sort number 0 is the default sort by view
        return "sort by price low to high" +      //sort number 1
                "\nsort by price high to low" +   //sort number 2
                "\nsort by rating" +              //sort number 3
                "\nsort by comment count";      //sort number 4
    }
}

