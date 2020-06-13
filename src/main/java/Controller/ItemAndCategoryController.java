package Controller;

import Model.Category;
import Model.Comment;
import Model.Item;
import Model.Rating;
import Model.Requests.ItemEdit;
import Model.Requests.ItemRequest;
import Model.Requests.Request;
import Model.Users.Buyer;
import Model.Users.Seller;
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
import java.util.Iterator;

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
        User user=UserController.getInstance().getCurrentOnlineUser();
        Item item = getItemById(id);
        if (item == null) {
            return View.ANSI_RED+"Error: item doesn't exist."+View.ANSI_RESET;
        }
        if(user instanceof Seller){
            Seller seller=(Seller) user;
            if(!seller.hasItem(id)){
                return "Error: You can only delete your own items!";
            }
        }
        if(user instanceof Buyer){
            return "Error: Internal Error.";
        }
        UserController.getInstance().deleteItemFromSeller(id,item.getSellerName());
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
            return (View.ANSI_RED + "Error: Invalid ID\n" + View.ANSI_RESET);
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
            return "Error: Invalid ID";
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

    public ArrayList<String> showItemComments(String itemID) {
        ArrayList<String> allComments = new ArrayList<>();
        String status;
        for (Comment comment : getItemById(itemID).getAllComments()) {
            if(comment.hasBought()){
                status = "\u2713";
            }
            else{
                status = "";
            }
            allComments.add(comment.getUsername()+"("+status+")" + ": " + comment.getText());
        }
        return allComments;
    }

    public String comment(String text, String itemId) {
        if (!isThereItemWithId(itemId)) {
            return "Error: Invalid ID";
        }
        Item item = getItemById(itemId);
        if (controller.currentOnlineUser == null) {
            return "Error: Please sign in to comment";
        }
        if(controller.currentOnlineUser instanceof Buyer==false){
            return "Error: Only customers can post comments.";
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
        return "Successful: Your comment has been submitted.";
    }

    public String rate(int score, String itemId) {
        if (!isThereItemWithId(itemId)) {
            return "Error: invalid ID.";
        }
        User user = controller.currentOnlineUser;
        if (!(user instanceof Buyer)) {
            return "Error: Only customers can rate items.";
        }
        Buyer buyer = (Buyer) user;
        Item item = getItemById(itemId);
        if (!item.isBuyerWithUserName(buyer.getUsername())) {
            return "Error: You must first buy this item.";
        }
        if (item.hasUserRated(buyer.getUsername())) {
            return "Error: You have already rated this item.";
        }
        Rating rating = new Rating(score, buyer.getUsername(), itemId);
        item.addRating(rating);
        Database.getInstance().saveItem(item);
        return "Successful: Submitted Rating.";
    }

    public String digest(String itemId) {
        return getItemById(itemId).toString();
    }

    public String showAttributes(String itemId) {
        return getItemById(itemId).showAttributes();
    }

    public String addCategory(String name, ArrayList<String> attributes, String fatherName) {
        if (isThereCategoryWithName(name)) {
            return "Error: Category with this name already exists";
        }
        if (!isThereCategoryWithName(fatherName)) {
            return "Error: Father category is nonexistent.";
        }
        Category category = new Category(name, attributes, fatherName);
            Database.getInstance().saveCategory(category);
        Category father = getCategoryByName(fatherName);
        father.addSubCategory(name);
            Database.getInstance().saveCategory(father);
        return "Successful: Created Category.";
    }

    public String addItem(String Name, String companyName, String description, double price, int instock, String categoryName, HashMap<String, String> attribute) {
        if (!isThereCategoryWithName(categoryName)) {
            return "Error: Invalid category name.";
        }
        if(UserController.getInstance().getCurrentOnlineUser()==null)
            return "Error: No user is logged in!";
        Item item = new Item(Name, companyName, description, "", price, controller.currentOnlineUser.getUsername(), categoryName, attribute, instock);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addItemRequest(requestID, item);
        return "Successful: Admins have been notified of your request to add this item.";
    }

    public String addItem(String Name, String companyName, String description, double price, int instock, String categoryName, HashMap<String, String> attribute,String image,String video) {
        if (!isThereCategoryWithName(categoryName)) {
            return View.ANSI_RED+"Error: Invalid category name."+View.ANSI_RESET;
        }
        if(UserController.getInstance().getCurrentOnlineUser()==null)
            return View.ANSI_RED+"Error: No user is logged in!"+View.ANSI_RESET;
        Item item = new Item(Name, companyName, description, "", price, controller.currentOnlineUser.getUsername(), categoryName, attribute, instock,image,video);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addItemRequest(requestID, item);
        return "Successful: Admins have been notified of your request to add this item.";
    }

    public void addItemToCategory(String itemid, String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null) {
            return;
        }
        category.addItem(itemid);
            Database.getInstance().saveCategory(category);
    }

    public Category getCurrentCategory() {
        return getCategoryByName(ShopMenu.getInstance().getCurrentCategory());
    }

    public ArrayList<String> getCategoryItems(String categoryName) { //<== in miad itemaye bache hasham mide, test konid!
        Category category = getCategoryByName(categoryName);
        if(category==null){
            return new ArrayList<String>();
        }
        if (category.getSubCategories().isEmpty()) return category.getAllItemsID();

        ArrayList<String> allItems = new ArrayList<>();
        for (String subCategoryName : category.getSubCategories()) {
            allItems.addAll(getCategoryItems(subCategoryName));
        }
        allItems.addAll(category.getAllItemsID());
        return allItems;
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

    public String renameCategory(String oldName,String newName){
        Category category=getCategoryByName(oldName);
        if(category==null){
            return  "Error: Invalid category name";
        }
        if(isThereCategoryWithName(newName)){
            return "Error: Category with this name already exists";
        }
        category.rename(newName);  //this will also change parent and father and item category name
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            if((request instanceof ItemRequest)&&((ItemRequest) request).getNewItem().getCategoryName().equals(oldName)){
              ((ItemRequest) request).getNewItem().setCategoryName(newName);
                    Database.getInstance().saveRequest(request);
            }
        }
        category.setName(newName);
        Database.getInstance().saveCategory(category);
        Database.getInstance().deleteCategory(getCategoryByName(oldName));
        return "Successful: Renamed Category.";
    }

    public String removeCategory(String name) {
        if(!isThereCategoryWithName(name)) return "Error: Invalid category name!";
        Category category=getCategoryByName(name);
        Category parent=getCategoryByName(category.getParent());
        if(parent!=null){
        parent.getSubCategories().remove(category.getName());
        }
        ArrayList<Category>removedCategories=new ArrayList<>();
        DFSCategory(name,removedCategories);
        for(Category category1:removedCategories){
            Database.getInstance().deleteCategory(category1);
        }
        if(parent!=null){
        Database.getInstance().saveCategory(parent); }
        Database.getInstance().deleteCategory(category);
        return "Successful: Deleted Category.";
    }

    private void DFSCategory(String categoryName,ArrayList<Category>removed){
        Category category=getCategoryByName(categoryName);
        Item item;
        for(String id:category.getAllItemsID()){
            item=getItemById(id);
            if(item!=null)
            Database.getInstance().deleteItem(item);
        }
        Iterator<String>subCats=category.getSubCategories().iterator();
        while(subCats.hasNext()){
            Category category1=getCategoryByName(subCats.next());
            removed.add(category1);
            DFSCategory(category1.getName(),removed);
        }
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
        Database.getInstance().saveItem(item);
    }

    public double getScore(String itemID){
        Item item = getItemById(itemID);
        if(item==null)
            return 0D;
        return item.getRating();
    }

    public ArrayList<String> getItemBuyer(String itemId){
        Item item=getItemById(itemId);
        if(item==null){
            return null;
        }
        return item.getBuyerUserName();
    }

    public boolean doesItemHaveAttribute(String id,String key){
        Item item=getItemById(id);
        if(item==null) return false;
        return item.hasAttribute(key);
    }
    public String getCategoryInfo(String categoryName){
        Category category=getCategoryByName(categoryName);
        if(category==null) return "Error: category doesnt exist";
        return category.toString();
    }
    public String addAttributeToCategory(String categoryName,String attribute){
        Category category=getCategoryByName(categoryName);
        if(category==null){
            return "Error: invalid category name";
        }
        if(category.getAttributes().contains(attribute)){
            return "Error: category already has this attribute";
        }
        category.addAttribute(attribute);
        for (String itemId : category.getAllItemsID()) {
            Item item=getItemById(itemId);
            if(item==null) continue;
            item.addAttribute(attribute,"");
            Database.getInstance().saveItem(item);
        }
        Database.getInstance().saveCategory(category);
           return "Successful: attribute added.";
    }

}
