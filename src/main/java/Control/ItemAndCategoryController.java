package Control;

import Model.Cart;
import Model.Category;
import Model.Item;

import java.util.ArrayList;

public class ItemAndCategoryController {
    Controller controller = Controller.getInstance();

    public void deleteItem(String id){}

    public boolean isThereItemWithId(String id){
        return false;
    }

    public void removeItemByID(String itemID){
        for(Item item :controller.allItems){
            if(item.getId().equals(itemID)){
                controller.allItems.remove(item);
            }
        }
    }

    public Item getItemById(String id) {
        for (Item item : controller.allItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean searchItemInCategory(String categoryName, String itemId) {
        return false;
    }

    public String compare(String itemId1, String itemId2) {
        return "hello";
    }

    public void addToBucket(String itemId) {

    }

    public void removeItemFromBucket(String itemId) {

    }

    public void comment(String text, String itemId) {

    }

    public void rate(int score, String itemId) {
    }

    public Boolean addCategory(String Name) {
        return false;
    }

    public Boolean Buy() {
        return false;
    }

    public void sortBy(String sortByWhat){
    }

    public void filterBy(String filterByWhat){

    }

    public boolean addItem(Item item) {
        return false;
    }

    public Category getCurrentCategory() {
        return controller.currentCategory;
    }

    public ArrayList<Item> getCurrentViewableItems() {
        return controller.currentViewableItems;
    }

    public void setCurrentCategory(Category currentCategory) {
        controller.currentCategory = currentCategory;
    }

    public Cart getCurrentShoppingCart() {
        return controller.currentShoppingCart;
    }

    public void setCurrentShoppingCart(Cart currentShoppingCart) {
        controller.currentShoppingCart = currentShoppingCart;
    }
}
