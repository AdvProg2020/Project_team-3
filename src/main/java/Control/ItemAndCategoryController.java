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
import java.util.Iterator;

public class ItemAndCategoryController {
    Controller controller = Controller.getInstance();
    private static ItemAndCategoryController itemAndCategoryController;
    private ItemAndCategoryController(){}
    ArrayList<Item> currentViewableItems = new ArrayList<>();
    public static ItemAndCategoryController getInstance(){
        if(itemAndCategoryController==null)
            itemAndCategoryController=new ItemAndCategoryController();
        return itemAndCategoryController;
    }

    public String deleteItem(String id){
        Item item=getItemById(id);
        if(item==null){
            return "Error: item doesnt exist";
        }
        Database.getInstance().deleteItem(item);
        return "Successful:";
    }

    public boolean isThereItemWithId(String id){
        String path="Resource"+File.separator+"Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
            return true;
    }

    public String showCart(){
        String string=getCurrentShoppingCart().toString();
        if(string.isEmpty()){
            return "Cart is empty";
        }else{
            return string;
        }
    }
    public String addItemToCart(String itemId){
        if(isThereItemWithId(itemId)==false){
            return "Error: invalid id";
        }
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid,int count){ //for decrease count needs to be negative
        if(isThereItemWithId(itemid)==false){
            return "Error: invalid id";
        }
        if(count<0){
        count=getCurrentShoppingCart().getItemCount(itemid)+count;
         }else{
            count+=getCurrentShoppingCart().getItemCount(itemid);
        }
        return getCurrentShoppingCart().changeCountBy(itemid,count);
    }

    public double getCartPrice(){
        return getCurrentShoppingCart().getCartPrice();
    }

    public Item getItemById(String id) {
        String path="Resource"+File.separator+"Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return null;
        }
        Gson gson=new Gson();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
                return gson.fromJson(content,Item.class);}
         catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category getCategoryByName(String categoryName){
        String path="Resource"+File.separator+"Category";
        String name=categoryName+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()) return  null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content , Category.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean searchItemInCategory(String categoryName, String itemId) {
        Category category=getCategoryByName(categoryName);
        if(category==null) return false;
            if(category.hasItemWithID(itemId)) return true;
        return false;
    }

    public String compare(String itemId1, String itemId2) {
        return "hello";
    }

    public ArrayList<String> showItemComments(String itemid){
        ArrayList<String> allComments=new ArrayList<>();
        for (Comment comment : getItemById(itemid).getAllComments()) {
            allComments.add(comment.getUsername()+": "+comment.getText());
        }
        return allComments;
    }

    public String  comment(String text, String itemId){
        if(isThereItemWithId(itemId)==false){
            return "Error: invalid id";
        }
        Item item=getItemById(itemId);
        if(controller.currentOnlineUser==null){
            return "Error: please sign in to comment";
        }
        if(item.isBuyerWithUserName(controller.currentOnlineUser.getUsername())){
            Comment comment=new Comment(controller.currentOnlineUser.getUsername(), itemId , text ,true);
            String requestID=controller.getAlphaNumericString(controller.getIdSize(),"Requests");
            RequestController.getInstance().addCommentRequest(requestID,comment);
        }else{
            Comment comment=new Comment(controller.currentOnlineUser.getUsername(), itemId , text ,false);
            String requestID=controller.getAlphaNumericString(controller.getIdSize(),"Requests");
            RequestController.getInstance().addCommentRequest(requestID,comment);
        }
        return"Succesdful: your comment has been added";
    }

    public void rate(int score, String itemId) {
        if(isThereItemWithId(itemId)==false){
            System.out.println("we do not have this Item in our storage with that ID");
            return;
        }
        User user=controller.currentOnlineUser;
        if(!(user instanceof Buyer)){
            System.out.println("you can not Rate the Items!");
            return;
        }
        Item item=getItemById(itemId);
        if(item.isBuyerWithUserName(controller.currentOnlineUser.getUsername())){
            Rating rating=new Rating(score,user.getUsername(),itemId);
            item.addRating(rating);
            System.out.println("thanks for your Rating!");
            return;
        }
        System.out.println("you did not buy that item and you are not allowed for rating!");
    }

    public String digest(String itemId){
   return getItemById(itemId).toString();
    }

    public String showAttributes(String itemId){
        return getItemById(itemId).showAttributes();
    }


    public void addCategory(String name , ArrayList<String>attributes) {
        if(getCurrentCategory().hasSubCategoryWithName(name)==true) {
            return;
        }
        Category category=new Category(name);
        category.setParent(getCurrentCategory().getName());
        getCurrentCategory().addSubCategory(category.getName());
        category.setAttributes(attributes);
        try {
            Database.getInstance().saveCategory(getCurrentCategory());
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean buy() {
        return false;
    }

    public void sortBy(String sortByWhat){
    }

    public void filterBy(String filterByWhat){

    }

    public String addItem(Item item){
        String requestID=controller.getAlphaNumericString(controller.getIdSize(),"Requests");
        RequestController.getInstance().addItemRequest(requestID,item);
        return "Successful: your request to add the item was sent to the admins.";
    }

    public Category getCurrentCategory() {
        return controller.currentCategory;
    }

    public ArrayList<Item> getCurrentViewableItems() {
        if(currentViewableItems==null){
            currentViewableItems=idToItemInCurrentCategory();
        }
        return currentViewableItems;
    }

    public ArrayList<Item> idToItemInCurrentCategory() {
        ArrayList<Item>allItems=new ArrayList<>();
        Category current=controller.currentCategory;
        ArrayList<String>itemIDs=current.getAllItemsID();
        Item item;
            for(String id:itemIDs){
                item=ItemAndCategoryController.getInstance().getItemById(id);
                allItems.add(item);
            }
        return allItems;
    }


    public void setCurrentCategory(Category currentCategory) {
        controller.currentCategory = currentCategory;
    }

    public Cart getCurrentShoppingCart() {
        return controller.ShoppingCart;
    }



    public boolean currentViewableItemsContainsItem(String itemID){
        return currentViewableItems.contains(getItemById(itemID));
    }


    public ArrayList<Item> getAllItemFromDataBase(){
        String path="Resource"+File.separator+"Items";
        File file=new File(path);
        File [] allFiles=file.listFiles();
        String fileContent = null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Item> allItems=new ArrayList<>();
        for(File file1:allFiles){
            try {
                fileContent=new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            allItems.add(gson.fromJson(fileContent , Item.class));
        }
        return  allItems;
    }



    private static  void DFSCategory(Category category , ArrayList<Category> removed){
        removed.add(category);
        for(String id:category.getAllItemsID()){
            Item item=getInstance().getItemById(id);
            Database.getInstance().deleteItem(item);
        }
        Iterator<String> iterator=category.getSubCategories().iterator();
        while(iterator.hasNext()){
            DFSCategory(getInstance().getCategoryByName(iterator.next()), removed);
        }
    }

    public void editCategoryName(String lastName , String newName){
        Category category=getCategoryByName(lastName);
        category.setName(newName);
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /////////////nemidonam che jori bayad attribute ru avaz kard!
    public void removeCategory(String name){
        Category category=getCategoryByName(name);
        ArrayList<Category>removed=new ArrayList<>();
        DFSCategory(category,removed);
        for(Category category1:removed){
            Database.getInstance().deleteCategory(category1);
        }
    }

    public ArrayList<Item> getInSaleItems(){
        ArrayList<Item> allItems=getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            if(item.isInSale()==false) allItems.remove(item);
        }
        return  allItems;
    }

    public void loadMainCategory(){
        Category category=new Category("Main");
        try {
            Database.getInstance().saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.currentCategory=category;
    }

}
