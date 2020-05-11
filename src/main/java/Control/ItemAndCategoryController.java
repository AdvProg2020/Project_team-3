package Control;

import Model.*;
import Model.Users.Buyer;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
/*
* aghajan, kolliate dastan ine ke, inja in arraylist e CURRENT_VIEWABLE_ITEMS ro darim.
* yaro harja bekhad mahsolat ro bebine (ya age live bashe bokonim tu cheshe kirish) miad az controller ye getter e kiri
* seda mizane, in getter miad ye stringi chizi be niabat az CURRENT_VIEWABLE_ITEMS tof mikone tu system.out.
* hala mikhay sort koni? tartibe CURRENT_VIEWABLE_ITEMS ro sort kon. mikhai filter koni? filteresh kon.
* BOZORGTARIN bazeii ke currentviewableitems mitone darbar begire CATEGORY iie ke toshim.
*
* */

public class ItemAndCategoryController {
    Controller controller = Controller.getInstance();
    private static ItemAndCategoryController itemAndCategoryController;

    private ItemAndCategoryController() {
    }

    ArrayList<Item> currentViewableItems = new ArrayList<>();

    public static ItemAndCategoryController getInstance() {
        if (itemAndCategoryController == null)
            itemAndCategoryController = new ItemAndCategoryController();
        return itemAndCategoryController;
    }

    public String deleteItem(String id) {
        Item item = getItemById(id);
        if (item == null) {
            return "Error: item doesnt exist";
        }
        Database.getInstance().deleteItem(item);
        return "Successful:";
    }

    public boolean isThereItemWithId(String id) {
        String path = "Resource" + File.separator + "Items";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public String showCart() {
        String string = getCurrentShoppingCart().toString();
        if (string.isEmpty()) {
            return "Cart is empty";
        } else {
            return string;
        }
    }

    public boolean isThereCategoryWithName(String name) {
        String path = "Resource" + File.separator + "Categories";
        String fileName = name + ".json";
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public String addItemToCart(String itemId) {
        if (isThereItemWithId(itemId) == false) {
            return "Error: invalid id";
        }
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid, int count) { //for decrease count needs to be negative
        if (isThereItemWithId(itemid) == false) {
            return "Error: invalid id";
        }
        if (count < 0) {
            count = getCurrentShoppingCart().getItemCount(itemid) + count;
        } else {
            count += getCurrentShoppingCart().getItemCount(itemid);
        }
        return getCurrentShoppingCart().changeCountBy(itemid, count);
    }

    public double getCartPriceWithoutDiscountCode() {
        return getCurrentShoppingCart().getCartPriceWithoutDiscountCode();
    }

    public double getCartPriceWithDiscountCode() {
        return getCurrentShoppingCart().getCartPriceWithDiscountCode();
    }

    public Item getItemById(String id) {
        String path = "Resource" + File.separator + "Items";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category getCategoryByName(String categoryName) {
        String path = "Resource" + File.separator + "Categories";
        String name = categoryName + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) return null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, Category.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean searchItemInCategory(String categoryName, String itemId) {
        Category category = getCategoryByName(categoryName);
        if (category == null) return false;
        if (category.hasItemWithID(itemId)) return true;
        return false;
    }

    public String compare(String itemId1, String itemId2) {
        Item item1 = getItemById(itemId1);
        Item item2 = getItemById(itemId2);
        if ((item1 == null) || (item2 == null)) {
            return "Error: invalid id";
        }
        String string;
        ArrayList<String> attributesKey = item1.getAttributesKey();
        HashMap<String, String> item1Attributes = item1.getAttributes();
        HashMap<String, String> item2Attributes = item2.getAttributes();
        string = "price: " + item1.getPriceWithSale() + "----" + item2.getPrice() + "\n";
        for (String key : attributesKey) {
            string += key + ": " + item1Attributes.get(key) + "----" + item2Attributes.get(key) + "\n";
        }
        return string;
    }

    public ArrayList<String> showItemComments(String itemid) {
        ArrayList<String> allComments = new ArrayList<>();
        for (Comment comment : getItemById(itemid).getAllComments()) {
            allComments.add(comment.getUsername() + ": " + comment.getText());
        }
        return allComments;
    }

    public String comment(String text, String itemId) {
        if (isThereItemWithId(itemId) == false) {
            return "Error: invalid id";
        }
        Item item = getItemById(itemId);
        if (controller.currentOnlineUser == null) {
            return "Error: please sign in to comment";
        }
        if (item.isBuyerWithUserName(controller.currentOnlineUser.getUsername())) {
            Comment comment = new Comment(controller.currentOnlineUser.getUsername(), itemId, text, true);
            String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
            RequestController.getInstance().addCommentRequest(requestID, comment);
        } else {
            Comment comment = new Comment(controller.currentOnlineUser.getUsername(), itemId, text, false);
            String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
            RequestController.getInstance().addCommentRequest(requestID, comment);
        }
        return "Successful: your comment has been added";
    }

    public String rate(int score, String itemId) {
        if (isThereItemWithId(itemId) == false) {
            return "Error: invalid id";
        }
        User user = controller.currentOnlineUser;
        if (!(user instanceof Buyer)) {
            return "Error: you cant rate an item";
        }
        Buyer buyer = (Buyer) user;
        Item item = getItemById(itemId);
        if (!item.isBuyerWithUserName(buyer.getUsername())) {
            return "Error: you must first buy this item";
        }
        if (item.hasUserRated(buyer.getUsername())) {
            return "Error: you have already rated this item";
        }
        Rating rating = new Rating(score, buyer.getUsername(), itemId);
        item.addRating(rating);
        try {
            Database.getInstance().saveItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful: your have rated the item";
    }

    public String digest(String itemId) {
        return getItemById(itemId).toString();
    }

    public String showAttributes(String itemId) {
        return getItemById(itemId).showAttributes();
    }

    public String addCategory(String name, ArrayList<String> attributes, String fatherName) {
        if (isThereCategoryWithName(name)) {
            return "Error: category with this name already exist";
        }
        if (!isThereCategoryWithName(fatherName)) {
            return "Error: father category doesnt exist";
        }
        Category category = new Category(name, attributes, fatherName);
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    public String addCategory(String name, ArrayList<String> attributes) {
        if (isThereCategoryWithName(name)) {
            return "Error: category with this name already exist";
        }
        Category category = new Category(name, attributes);
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    public Boolean buy() {
        return false;
    }

    public void sortBy(String sortByWhat) {

    }

    public void filterBy(String filterByWhat) {

    }

    public String addItem(Item item) {
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addItemRequest(requestID, item);
        return "Successful: your request to add the item was sent to the admins.";
    }

    public Category getCurrentCategory() {
        return controller.currentCategory;
    }

    public ArrayList<Item> getCurrentViewableItems() {
        if (currentViewableItems == null) {
            currentViewableItems = idToItemInCurrentCategory();
        }
        return currentViewableItems;
    }

    public ArrayList<Item> idToItemInCurrentCategory() {
        ArrayList<Item> allItems = new ArrayList<>();
        Category current = controller.currentCategory;
        ArrayList<String> itemIDs = current.getAllItemsID();
        Item item;
        for (String id : itemIDs) {
            item = ItemAndCategoryController.getInstance().getItemById(id);
            allItems.add(item);
        }
        return allItems;
    }

    public Cart getCurrentShoppingCart() {
        return controller.ShoppingCart;
    }


    public boolean currentViewableItemsContainsItem(String itemID) {
        return currentViewableItems.contains(getItemById(itemID));
    }


    public ArrayList<Item> getAllItemFromDataBase() {
        String path = "Resource" + File.separator + "Items";
        File file = new File(path);
        File[] allFiles = file.listFiles();
        String fileContent = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Item> allItems = new ArrayList<>();
        for (File file1 : allFiles) {
            try {
                fileContent = new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            allItems.add(gson.fromJson(fileContent, Item.class));
        }
        return allItems;
    }

    public void editCategoryName(String lastName, String newName) {
        Category category = getCategoryByName(lastName);
        category.setName(newName);
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String removeCategory(String name) {
        Category category = getCategoryByName(name);
        if (category == null) {
            return "Error: invalid name";
        }
        for (String subCategory : category.getSubCategories()) {
            if (subCategory != null)
                removeCategory(subCategory);
        }
        Database.getInstance().deleteCategory(category);
        return "Successful";
    }

    public ArrayList<Item> getInSaleItems() {
        ArrayList<Item> allItems = getInstance().getAllItemFromDataBase();
        for (Item item : allItems) {
            if (item.isInSale() == false) allItems.remove(item);
        }
        return allItems;
    }

  /*  public void loadMainCategory(){
        Category category=new Category("Main");
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.currentCategory=category;
    } */

}
