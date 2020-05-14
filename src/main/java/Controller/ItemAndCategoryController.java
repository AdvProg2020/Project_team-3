package Controller;

import Model.Category;
import Model.Comment;
import Model.Item;
import Model.Rating;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

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
            return "Error: item doesn't exist";
        }
        Database.getInstance().deleteItem(item);
        UserController.getInstance().deleteItemFromSeller(id, item.getSellerName());
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

    public boolean isThereCategoryWithName(String name) {
        String path = "Resource" + File.separator + "Categories";
        String fileName = name + ".json";
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            return false;
        }
        return true;
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

    public String viewItem(String id) {
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(id)) {
            return (View.ANSI_RED + "Error: invalid id\n" + View.ANSI_RESET);
        }
        Item item = getItemById(id);
        item.addViewsBy(1);
        try {
            Database.getInstance().saveItem(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
        ArrayList<String> attributesKey = ItemAndCategoryController.getInstance().getCategoryByName(item1.getCategoryName()).getAttributes();
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
        if (!isThereItemWithId(itemId)) {
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
        if (!isThereItemWithId(itemId)) {
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
        Category father = getCategoryByName(fatherName);
        father.addSubCategory(name);
        try {
            Database.getInstance().saveCategory(father);
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


    public String addItem(String Name, String companyName, String description, double price, int instock, String categoryName, HashMap<String, String> attribute) {
        if (!isThereCategoryWithName(categoryName)) {
            return "Error: invalid category name";
        }
        Item item = new Item(Name, companyName, description, "", price, controller.currentOnlineUser.getUsername(), categoryName, attribute, instock);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addItemRequest(requestID, item);
        return "Successful: your request to add the item was sent to the admins.";
    }

    public void addItemToCategory(String itemid, String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null) {
            return;
        }
        category.addItem(itemid);
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Category getCurrentCategory() {
        return getCategoryByName(ShopMenu.getInstance().getCurrentCategory());
    }

    public ArrayList<String> getCategoryItems(String categoryName) { //<== in miad itemaye bache hasham mide, test konid!
        Category category = getCategoryByName(categoryName);
        if (category.getSubCategories().isEmpty()) return category.getAllItemsID();

        ArrayList<String> allItems = new ArrayList<>();
        for (String subCategoryName : category.getSubCategories()) {
            allItems.addAll(getCategoryItems(subCategoryName));
        }
        allItems.addAll(category.getAllItemsID());
        return allItems;
    }

    public String getCategoryItemsString(String categoryName) {
        return "paniz";
    }

    public Category getBaseCategory() {
        return getCategoryByName("Main");
    }

    public String previousCategory(String categoryName) {
        Category currentCategory = getCategoryByName(categoryName);
        try {
            Category category = getCategoryByName(currentCategory.getParent());
            if (category == null) return currentCategory.getName();
            return category.getName();
        } catch (Exception e) {
            return currentCategory.getName();
        }
    }

    public String openCategory(String name) {
        if (!isThereCategoryWithName(name)) {
            return View.ANSI_RED + "Error: No such category." + View.ANSI_RESET;
        }
        ShopMenu.getInstance().setCurrentCategory(name);
        return "Successful:";
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
        String path = "Resource" + File.separator + "Categories";
        String fileName = lastName + ".json";
        System.out.println(fileName);
        File file = new File(path + File.separator + fileName);
        System.out.println(file.exists());
        file.delete();
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
            if (!item.isInSale()) allItems.remove(item);
        }
        return allItems;
    }

    public void editItem(String changedField, String newField, String itemID) {
        String requestID = Controller.getInstance().getAlphaNumericString(5, "Requests");
        RequestController.getInstance().editItemRequest(requestID, itemID, changedField, newField);
    }

    public void addView(String itemId) {
        Item item = getItemById(itemId);
        if (item == null)
            return;
        item.addViewsBy(1);
    }
}
