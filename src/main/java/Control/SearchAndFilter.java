package Control;

import Model.Category;
import Model.Item;
import View.Menus.DiscountsMenu;
import View.Menus.ItemMenu;
import View.Menus.View;

import java.util.ArrayList;

public class SearchAndFilter {
    private Category filteredCategory;
    private String filteredCompanyName;
    private String filteredItemName;
    private double minimumPrice;
    private double maximumPrice;
    private ArrayList<String> availableFilters=new ArrayList<>();
    public static SearchAndFilter searchAndFilter;
    private ArrayList<Item> currentViewableItems = new ArrayList<>();


    private SearchAndFilter(){}

    public static SearchAndFilter getInstance(){
        if(searchAndFilter==null) searchAndFilter=new SearchAndFilter();
        return searchAndFilter;
    }


    public ArrayList<Item> getCurrentViewableItems() {
        return currentViewableItems;
    }

    public boolean currentViewableItemsContainsItem(String itemID){
        for(Item item:currentViewableItems){
            if(item.getId().equals(itemID)) return true;
        }
        return false;
    }


    public ArrayList<Item> filterByCategory(String categoryName){
        if(availableFilters.contains("category")) {
            currentViewableItems=disableFilter("category");
        }
        if(!availableFilters.contains("category")) availableFilters.add("category");
        setFilteredCategory(ItemAndCategoryController.getInstance().getCategoryByName(categoryName));
        if(currentViewableItems.isEmpty()){
            if(View.getCurrentMenu() instanceof ItemMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
            else if(View.getCurrentMenu() instanceof DiscountsMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getInSaleItems();
        }
        for(Item item:currentViewableItems){
            if(!item.getCategoryName().equals(categoryName)) currentViewableItems.remove(item);
        }
        return currentViewableItems;
    }

    public ArrayList<Item> filterByItemName(String itemName){
        if(availableFilters.contains("item Name")){
            currentViewableItems=disableFilter("item Name");
        }
        if(!availableFilters.contains("item Name")) availableFilters.add("item Name");
        setFilteredItemName(itemName);
        if(currentViewableItems.isEmpty()){
            if(View.getCurrentMenu() instanceof ItemMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
            else if(View.getCurrentMenu() instanceof DiscountsMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getInSaleItems();
        }
        for(Item item:currentViewableItems){
            if(!item.getName().equals(itemName)){
                currentViewableItems.remove(item);
            }
        }
        return currentViewableItems;
    }

    public ArrayList<Item> filterByCompanyName(String companyName){
        if(availableFilters.contains("company Name")){
            currentViewableItems=disableFilter("company Name");
        }
        if(!availableFilters.contains("company Name")) availableFilters.add("company Name");
        setFilteredCompanyName(companyName);

        if(currentViewableItems.isEmpty()){
            if(View.getCurrentMenu() instanceof ItemMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
            else if(View.getCurrentMenu() instanceof DiscountsMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getInSaleItems();
        }

        for(Item item:currentViewableItems){
            if(!item.getBrand().equals(companyName)){
                currentViewableItems.remove(item);
            }
        }
        return currentViewableItems;
    }

    public ArrayList<Item> filterByPrice(double minimumPrice , double maximumPrice){
        if(availableFilters.contains("price")){
            currentViewableItems=disableFilter("price");
        }
        if(!availableFilters.contains("price")) availableFilters.add("price");
        setMinimumPrice(minimumPrice);
        setMaximumPrice(maximumPrice);
        if(currentViewableItems.isEmpty()){
            if(View.getCurrentMenu() instanceof ItemMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
            else if(View.getCurrentMenu() instanceof DiscountsMenu)
                currentViewableItems=ItemAndCategoryController.getInstance().getInSaleItems();
        }

        if(maximumPrice==0 && maximumPrice==0) return currentViewableItems;
        else if(maximumPrice==0 && minimumPrice>0){
            for(Item item:currentViewableItems){
                if(item.getPrice()<minimumPrice) currentViewableItems.remove(item);
            }
            return currentViewableItems;
        }
        else if(minimumPrice==0 && maximumPrice>0){
            for(Item item:currentViewableItems){
                if(item.getPrice()>maximumPrice) currentViewableItems.remove(item);
            }
            return  currentViewableItems;
        }
        else if(maximumPrice>0 && maximumPrice>0){
            for(Item item:currentViewableItems){
                if(item.getPrice()>maximumPrice || item.getPrice()<minimumPrice)
                    currentViewableItems.remove(item);
            }
            return currentViewableItems;
        }
        return  currentViewableItems;
    }


    public ArrayList<Item> disableFilter(String disabled){
        if(disabled.equalsIgnoreCase("category")){
            availableFilters.remove("category");
            currentViewableItems.clear();
            if(availableFilters.contains("company Name")){
                currentViewableItems=filterByCompanyName(filteredCompanyName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("item Name")){
                currentViewableItems=filterByItemName(filteredItemName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("price")){
                currentViewableItems=filterByPrice(minimumPrice,maximumPrice);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }
            return currentViewableItems;
        }else if(disabled.equalsIgnoreCase("company Name")){
            availableFilters.remove("company Name");
            currentViewableItems.clear();
            if(availableFilters.contains("category")){
                currentViewableItems=filterByCategory(filteredCategory.getName());
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("item Name")){
                currentViewableItems=filterByItemName(filteredItemName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("price")){
                currentViewableItems=filterByPrice(minimumPrice,maximumPrice);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }
            return currentViewableItems;
        }else if(disabled.equalsIgnoreCase("item Name")){
            availableFilters.remove("item Name");
            currentViewableItems.clear();
            if(availableFilters.contains("company Name")){
                currentViewableItems=filterByCompanyName(filteredCompanyName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("category")){
                currentViewableItems=filterByCategory(filteredCategory.getName());
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("price")){
                currentViewableItems=filterByPrice(minimumPrice,maximumPrice);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }
            return currentViewableItems;

        }else if(disabled.equalsIgnoreCase("price")){
            availableFilters.remove("price");
            currentViewableItems.clear();
            if(availableFilters.contains("company Name")){
                currentViewableItems=filterByCompanyName(filteredCompanyName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("item Name")){
                currentViewableItems=filterByItemName(filteredItemName);
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }if(availableFilters.contains("category")){
                currentViewableItems=filterByCategory(filteredCategory.getName());
                if(currentViewableItems.isEmpty()) return currentViewableItems;
            }
            return currentViewableItems;
        }
        return null;
    }


    public String showAllFilters(){
        return "you can use different the filter with these commands"+"\n"+
                "sort price min to max"+"\n"+
                "sort price max to min"+"\n"+
                "sort company name"+"\n"+
                "sort item name "+"\n";
    }

    ///getters and setters!
    public Category getFilteredCategory() {
        return filteredCategory;
    }

    public void setFilteredCategory(Category filteredCategory) {
        this.filteredCategory = filteredCategory;
    }

    public String getFilteredCompanyName() {
        return filteredCompanyName;
    }

    public void setFilteredCompanyName(String filteredCompanyName) {
        this.filteredCompanyName = filteredCompanyName;
    }

    public String getFilteredItemName() {
        return filteredItemName;
    }

    public void setFilteredItemName(String filteredItemName) {
        this.filteredItemName = filteredItemName;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public ArrayList<String> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(ArrayList<String> availableFilters) {
        this.availableFilters = availableFilters;
    }
}
