package Server.Controller;

import Server.Model.Category;
import Server.Model.DiscountCode;
import Server.Model.Item;
import Server.Model.Requests.Request;
import Server.Model.Users.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CartControllerTest {
    @BeforeClass public static void initiate(){
        Database.getInstance().initiate();
    }

    public  void acceptRequests(){
        ArrayList<Request>allRequests= RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
           System.out.println(RequestController.getInstance().acceptRequest(request.getRequestId()));
        }
    }

    public void addDiscountCode() {
        ArrayList<String>validUsers=new ArrayList<>();
        String startTime="25-02-2014 22:30";
        String endTime="27-02-2020 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        SaleAndDiscountCodeController.getInstance().addDiscountCode(20,dateTime1,dateTime,validUsers
                ,6,50);
    }

    public void addCategory() {
        Category category1= ItemAndCategoryController.getInstance().getCategoryByName("Project.Main");
        ArrayList<String>attributes=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("lavazem manzel",attributes,"Project.Main");
        ArrayList<String>attributes1=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Vacuum",attributes1,"lavazem manzel");
        ArrayList<String>attributes2=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Oven",attributes2,"lavazem manzel");
        ArrayList<String>attributes3=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("microwave",attributes3,"lavazem manzel");
    }

    public void deleteJunk(){
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        ItemAndCategoryController.getInstance().removeCategory("microwave");
        ItemAndCategoryController.getInstance().removeCategory("Oven");
        ItemAndCategoryController.getInstance().removeCategory("Vacuum");
        UserController.getInstance().deleteUser("testCart");
        UserController.getInstance().logout();
    }

    public void addItem() {
        UserController.getInstance().registerSeller(500,"testCart","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("testCart");
        System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
        addCategory();
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","expensive");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz"
                ,"this is vaccum",500,12,"Project.Main",
                attributes);
        ItemAndCategoryController.getInstance().addItem("Oven456","Benz"
                ,"this is oven",5000,12,"Project.Main",attributes1);
        System.out.println(ItemAndCategoryController.getInstance().addItem("microwave67","Benz",
                "this is microWave",600,12,"Project.Main",attributes2));
        UserController.getInstance().logout();
        ArrayList<Request> allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        acceptRequests();
    }



    @Test
    public void getInstance() {
        Database.getInstance().initiate();
        CartController cartController=CartController.getInstance();
        assertNotNull(cartController);
    }

    @Test
    public void getCurrentShoppingCart() {
        Assert.assertNotNull(CartController.getInstance().getCurrentShoppingCart());
        Assert.assertNotNull(CartController.getInstance().getCurrentShoppingCart());
    }

    @Test
    public void addItemToCart() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems) Assert.assertEquals(CartController.getInstance().addItemToCart(item.getId()),"Successful");
        Item item=allItems.get(0);
        Assert.assertNotNull(item);
        Assert.assertEquals(CartController.getInstance().addItemToCart(item.getId()),"Error: item is already in the cart");
        Assert.assertEquals(CartController.getInstance().addItemToCart("sdfsdf"), "Error: invalid id");
        Assert.assertNotNull(CartController.getInstance().showCart());
        Assert.assertFalse(Controller.getInstance().getCurrentShoppingCart().isEmpty());
        for(Item item1:allItems)Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void cartIncreaseDecrease() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems) System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        for(Item item:allItems) Assert.assertEquals("Successful",CartController.getInstance().cartIncreaseDecrease(item.getId(),2));
        for(Item item:allItems) Assert.assertEquals("Successful",CartController.getInstance().cartIncreaseDecrease(item.getId(),-1));
        Assert.assertNotNull(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().showCart());
        for(Item item:allItems) Assert.assertEquals("Successful",CartController.getInstance().cartIncreaseDecrease(item.getId(),-6));
        System.out.println(CartController.getInstance().showCart());
        System.out.println(CartController.getInstance().showCart());
        Assert.assertEquals("Error: invalid id",CartController.getInstance().cartIncreaseDecrease("sdfsdf",4));
        for(Item item:allItems)Database.getInstance().deleteItem(item);
        deleteJunk();
    }

    @Test
    public void getCartPriceWithoutDiscountCode() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems) System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),2));
        System.out.println(CartController.getInstance().showCart());
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),-1));
        System.out.println(CartController.getInstance().showCart());
        Assert.assertTrue(CartController.getInstance().getCartPriceWithoutDiscountCode()>0);
        for(Item item:allItems)Database.getInstance().deleteItem(item);
        deleteJunk();
    }

    @Test
    public void getCartPriceWithDiscountCode() {
        addItem();
        addDiscountCode();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=allDiscountCodes.get(0);
        for(Item item:allItems) System.out.println(CartController.getInstance().addItemToCart(item.getId()));
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),2));
        System.out.println(CartController.getInstance().showCart());
        for(Item item:allItems) System.out.println(CartController.getInstance().cartIncreaseDecrease(item.getId(),-1));
        System.out.println(CartController.getInstance().showCart());
        Assert.assertTrue(CartController.getInstance().getCartPriceWithoutDiscountCode()>0);
        Assert.assertTrue(CartController.getInstance().getCartPriceWithDiscountCode(discountCode.getDiscountId())>0);
        for(Item item:allItems)Database.getInstance().deleteItem(item);
        for(DiscountCode discountCode1:allDiscountCodes) Database.getInstance().deleteDiscountCode(discountCode1);
        deleteJunk();
    }
}