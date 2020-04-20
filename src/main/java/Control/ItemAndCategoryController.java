package Control;

import Model.*;
import Model.Requests.Request;
import Model.Users.Buyer;
import Model.Users.User;

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
        for(Item item:controller.allItems){
            if(item.getId().equals(id)){
                controller.allItems.remove(item);
            }
        }
    }

    public boolean isThereItemWithId(String id){
        for(Item item:controller.allItems){
            if(item.getId().equals(id)){
                return true;
            }
        }
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
        if(isThereItemWithId(itemId)==false) {
            System.out.println("we do not have item with this id!");
            return;
        }





    }

    public void removeItemFromBucket(String itemId) {
        if(isThereItemWithId(itemId)==false) {
            System.out.println("we do not have item with this id!");
            return;
        }


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
