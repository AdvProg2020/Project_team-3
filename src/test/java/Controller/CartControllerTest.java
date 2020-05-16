package Controller;

import Model.Cart;
import Model.Category;
import Model.Item;
import Model.Requests.Request;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CartControllerTest {

    public void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    public void addCategory() {
        Category category1=ItemAndCategoryController.getInstance().getCategoryByName("Main");
        ArrayList<String>attributes=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("lavazem manzel",attributes,"Main");
        ArrayList<String>attributes1=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Vacuum",attributes1,"lavazem manzel");
        ArrayList<String>attributes2=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Oven",attributes2,"lavazem manzel");
        ArrayList<String>attributes3=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("microwave",attributes3,"lavazem manzel");
    }


    public void addItem() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("Ali");
        UserController.getInstance().login(seller.getUsername(),seller.getPassword());
        addCategory();
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","expensive");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz"
                ,"this is vaccum",500,12,"Vacuum",
                attributes);
        ItemAndCategoryController.getInstance().addItem("Oven456","Benz"
                ,"this is oven",5000,12,"Oven",attributes1);
        ItemAndCategoryController.getInstance().addItem("microwave67","Benz",
                "this is microWave",600,12,"microwave",attributes2);
        UserController.getInstance().logout();
        ArrayList<Request> allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }



    @Test
    public void getInstance() {
    }

    @Test
    public void getCurrentShoppingCart() {
        Assert.assertNotNull(CartController.getInstance().getCurrentShoppingCart());
    }

    @Test
    public void addItemToCart() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems) System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        for(Item item:allItems) System.out.println(item.getId());
        Item item=allItems.get(0);
        System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        System.out.println(CartController.getInstance().addItemToCart("sdfsdf"));
        System.out.println(CartController.getInstance().showCart());
        for(Item item1:allItems)Database.getInstance().deleteItem(item1);
    }

    @Test
    public void cartIncreaseDecrease() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems) System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),2));
        System.out.println(CartController.getInstance().showCart());
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),-1));
        System.out.println(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().showCart());
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),-6));
        System.out.println(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().cartIncreaseDecrease(allItems.get(0).getId(),1));
        System.out.println(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().cartIncreaseDecrease("sdfsdf",4));
        for(Item item:allItems)Database.getInstance().deleteItem(item);
    }

    @Test
    public void getCartPriceWithoutDiscountCode() {


    }

    @Test
    public void getCartPriceWithDiscountCode() {
    }

    @Test
    public void buy() {
    }

    @Test
    public void testBuy() {
    }
}