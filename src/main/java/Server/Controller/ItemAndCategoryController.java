package Server.Controller;

import Project.Client.CLI.ShopAndDiscountMenu.ShopMenu;
import Project.Client.CLI.View;
import Server.Model.Category;
import Server.Model.Comment;
import Server.Model.Item;
import Server.Model.Rating;
import Server.Model.Requests.ItemRequest;
import Server.Model.Requests.Request;
import Server.Model.Users.Admin;
import Server.Model.Users.Buyer;
import Server.Model.Users.Seller;
import Server.Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String requestId=Controller.getInstance().getAlphaNumericString(5,"Requests");
        RequestController.getInstance().deleteItemRequest(requestId,id);
        if(UserController.getInstance().getCurrentOnlineUser() instanceof Admin) {
            UserController.getInstance().deleteItemFromSeller(id,item.getSellerName());
            Database.getInstance().deleteItem(item);
            return "Successful: item deleted";
        }
        return "the request for deleting item has been sent to the admin!";
    }

    public boolean isThereItemWithId(String id) {
        /*String path = "Resource" + File.separator + "Items";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;*/

        ArrayList<String> allItems = new ArrayList<>();
        Connection connection = null;
        int cnt = 0;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Items WHERE id='"+id+"'");
            while(rs.next())
            {
                cnt++;
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return cnt>0;
    }

    public boolean isThereCategoryWithName(String name) {
        //asd
        int cnt=0;
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Categories WHERE name='"+name+"'");
            while(rs.next())
            {
                cnt++;
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return cnt>0;
        /*String path = "Resource" + File.separator + "Categories";
        String fileName = name + ".json";
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            return false;
        }
        return true;*/
    }


    public Item getItemById(String id) {
        /*String path = "Resource" + File.separator + "Items";
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
        return null;*/
        Gson gson = new Gson();
        ArrayList<Item> allItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Items");
            while(rs.next())
            {
                HashMap<String,String> attributes = gson.fromJson(rs.getString(9),new TypeToken<HashMap<String,String>>(){}.getType());
                ArrayList<String> buyers = gson.fromJson(rs.getString(12),new TypeToken<ArrayList<String>>(){}.getType());
                ArrayList<Rating> ratings = gson.fromJson(rs.getString("allRatings"),new TypeToken<ArrayList<Rating>>(){}.getType());
                ArrayList<Comment> comments = gson.fromJson(rs.getString("allComments"),new TypeToken<ArrayList<Comment>>(){}.getType());
                Item item = new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),Double.parseDouble(rs.getString(6)),rs.getInt(7),rs.getInt(8)
                ,attributes,rs.getString(10),rs.getString(11),buyers,ratings,comments,rs.getString("saleID"),rs.getString("imageName"),rs.getString("videoName"),rs.getString("addedTime"));
                allItems.add(item);
                System.err.println("done");
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(allItems.isEmpty()) return null;
        return allItems.get(0);
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

        ArrayList<Category> viableOptions = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Categories WHERE name='"+categoryName+"'");
            while(rs.next())
            {
                //bayad be viableoptions new ezafe konim
                String name = rs.getString(1);
                String parent = rs.getString(2);
                ArrayList<String> items = gson.fromJson(rs.getString(3),new TypeToken<ArrayList<String>>(){}.getType());
                ArrayList<String> attributes = gson.fromJson(rs.getString(4),new TypeToken<ArrayList<String>>(){}.getType());
                ArrayList<String> children = gson.fromJson(rs.getString(5),new TypeToken<ArrayList<String>>(){}.getType());
                viableOptions.add(new Category(name,parent,items,attributes,children));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(viableOptions.isEmpty()) return null;
        return viableOptions.get(0);
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

    public String comment(String text, String itemId ,String fatherCommentId) {
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
            String commentId=Controller.getInstance().getAlphaNumericString(controller.getIdSize(),"Requests");
            Comment comment = new Comment(controller.currentOnlineUser.getUsername(), itemId, text, true);
            comment.setCommentId(commentId);
            comment.setFatherCommentId(fatherCommentId);
            String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
            RequestController.getInstance().addCommentRequest(requestID, comment);
        } else {
            String commentId=Controller.getInstance().getAlphaNumericString(controller.getIdSize(),"Requests");
            Comment comment = new Comment(controller.currentOnlineUser.getUsername(), itemId, text, false);
            comment.setCommentId(commentId);
            comment.setFatherCommentId(fatherCommentId);
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
            return "Error: Invalid category name.";
        }
        if(UserController.getInstance().getCurrentOnlineUser()==null)
            return "Error: No user is logged in!";
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
        return getCategoryByName("Project.Main");
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
        ArrayList<String> allIDs = Database.getInstance().getAllItemIDs();
        ArrayList<Item> ans = new ArrayList<>();
        for(String id:allIDs){
            ans.add(getItemById(id));
        }
        return ans;
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
        if(name.equals("Project.Main")) return "Error: you cant delete Project.Main";
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
        ArrayList<Item> toBeRemoved = new ArrayList<>();
        for (Item item : allItems) {
            if (!item.isInSale()) toBeRemoved.add(item);
        }
        allItems.removeAll(toBeRemoved);
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
           return "Successful: attribute added";
    }




}
