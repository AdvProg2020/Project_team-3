package Control;

import Model.*;
import Model.Requests.Request;
import Model.Users.Buyer;
import Model.Users.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ItemAndCategoryController {
    Controller controller = Controller.getInstance();
    private static ItemAndCategoryController itemAndCategoryController;
    private ItemAndCategoryController(){}

    public static ItemAndCategoryController getInstance(){
        if(itemAndCategoryController==null)
            itemAndCategoryController=new ItemAndCategoryController();
        return itemAndCategoryController;
    }

    public void deleteItem(String id){
        String path="Resource"+File.separator+"Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
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

    public boolean searchItemInCategory(String categoryName, String itemId) {
        return false;
    }

    public String compare(String itemId1, String itemId2) {
        return "hello";
    }

    public void addToBucket(String itemId) {
        if(isThereItemWithId(itemId)==false) {
            System.out.println("we do not have item with this id!");
            return;
        }
    }

    public String removeItemFromBucket(String itemId) {
        if(isThereItemWithId(itemId)==false) {
            return "we do not have item with this id!";
        }
        if(controller.getCurrentOnlineUser()!=null && controller.getCurrentOnlineUser() instanceof Buyer){
            if(((Buyer) controller.getCurrentOnlineUser()).getCart()==null)
                return "you did not bought any thing!";
            Cart cart=((Buyer) controller.getCurrentOnlineUser()).getCart();
            if(cart.includesItem(itemId)==false){
                return "you did not bought item with this item ID!";
            }
            cart.remove(getItemById(itemId).getName());
        }else{
            if(controller.currentShoppingCart==null){
                return "you did not bought any thing!";
            }
            Cart cart=controller.currentShoppingCart;
            cart=new Cart("");
            if(cart.includesItem(itemId)==false){
                return "you did not bought item with this item ID!";
            }
            cart.remove(getItemById(itemId).getName());
        }
        return "the Item removed successfully!";
    }

    public void comment(String text, String itemId) {
        if(isThereItemWithId(itemId)==false){
            System.out.println("we do not have this Item in our storage with that ID");
            return;
        }
        Item item=getItemById(itemId);
        if(controller.currentOnlineUser.getUsername().equals(item.getBuyerUserName())){
            Comment comment=new Comment(controller.currentOnlineUser.getUsername(), itemId , text ,true);
            item.addComment(comment);
        }else{
            Comment comment=new Comment(controller.currentOnlineUser.getUsername(), itemId , text ,false);
            item.addComment(comment);
            String requestID=controller.addId(Request.getIdCount());
            RequestController.getInstance().addCommentRequest(requestID,comment);
        }
        System.out.println("your comment was successfully added!!");
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
        if(item.getBuyerUserName().equals(user.getUsername())){
            Rating rating=new Rating(score,user.getUsername(),itemId);
            item.addRating(rating);
            System.out.println("thanks for your Rating!");
            return;
        }
        System.out.println("you did not buy that item and you are not allowed for rating!");
    }

    public void addCategory(String name) {
        if(getCurrentCategory().hasSubCategoryWithName(name)==true) {
            return;
        }
        Category category=new Category(name);
        category.setParent(getCurrentCategory());
        getCurrentCategory().addSubCategory(category);
    }

    public Boolean Buy() {
        return false;
    }

    public void sortBy(String sortByWhat){
    }

    public void filterBy(String filterByWhat){

    }

    public String addItem(Item item) {
        String requestId=controller.addId(Request.getIdCount());
        RequestController.getInstance().addItemRequest(requestId,item);
        return "your request was sent for adding item to our Admins!";
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
