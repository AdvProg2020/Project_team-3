package Controller;

import Model.Category;
import Model.Item;
import Model.Requests.ItemRequest;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.Seller;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

public class ItemAndCategoryControllerTest {

    public void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    @Test
    public void getInstance() {
        ItemAndCategoryController itemAndCategoryController=ItemAndCategoryController.getInstance();
        assertNotNull(itemAndCategoryController);
    }

    @Test
    public void deleteItem() {
        addItem();
        ArrayList<Item>allItem=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Item item=allItem.get(0);
        ItemAndCategoryController.getInstance().deleteItem(item.getId());
        for(Item item1:allItem) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void isThereItemWithId() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            Assert.assertTrue(ItemAndCategoryController.getInstance().isThereItemWithId(item.getId()));
        }
        Assert.assertFalse(ItemAndCategoryController.getInstance().isThereItemWithId("Behaeen"));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void isThereCategoryWithName() {
        addItem();
        Assert.assertTrue(ItemAndCategoryController.getInstance().isThereCategoryWithName("Oven"));
        Assert.assertFalse(ItemAndCategoryController.getInstance().isThereCategoryWithName("Behaeen"));
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void getItemById() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            System.out.println(ItemAndCategoryController.getInstance().getItemById(item.getId()).getId());
        }
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void getCategoryByName() {
        addItem();
        Category category=ItemAndCategoryController.getInstance().getCategoryByName("Vacuum");
        Category category1=ItemAndCategoryController.getInstance().getCategoryByName("Behaeen");
        Assert.assertNotNull(category);
        Assert.assertNull(category1);
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void searchItemInCategory() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Item item=allItems.get(0);
        Category category=ItemAndCategoryController.getInstance().getCategoryByName(item.getCategoryName());
        Assert.assertTrue(ItemAndCategoryController.getInstance().searchItemInCategory(category.getName(),item.getId()));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void compare() {
        addItem();
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.size()<2) return;
        String string=ItemAndCategoryController.getInstance().compare(allItems.get(0).getId(),allItems.get(1).getId());
        System.out.println(string);
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void showItemComments() {
        comment();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        ArrayList<String>string=ItemAndCategoryController.getInstance().showItemComments(allItems.get(0).getId());
        for(String attribute:string){
            System.out.println(string);
        }
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void comment() {
        addItem();
        UserController.getInstance().registerBuyer(500,"TestComment","Hitler",
                "Arman","S","arman@gmail.com","33151603");
        User user=UserController.getInstance().getUserByUsername("TestComment");
        UserController.getInstance().login("TestComment",user.getPassword());
        String text="that was very nice!";
        ArrayList<Item>allItems= ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        Item item=allItems.get(0);
        ItemAndCategoryController.getInstance().comment(text,item.getId());
        acceptRequests();
        System.out.println(ItemAndCategoryController.getInstance().showItemComments(item.getId()));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("TestComment");
        deleteJunk();
    }



    @Test
    public void digest() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Item item=allItems.get(0);
        System.out.println(ItemAndCategoryController.getInstance().digest(item.getId()));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void showAttributes() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        String attributes=ItemAndCategoryController.getInstance().showAttributes(allItems.get(0).getId());
        System.out.println(attributes);
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
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



    @Test
    public void addItem() {
        UserController.getInstance().registerSeller(500,"TestCategory","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        //addCategory();
        User seller =UserController.getInstance().getUserByUsername("TestCategory");
        System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
        addCategory();
        HashMap<String,String>attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","expensive");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        System.out.println(ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz"
                ,"this is vaccum",500,10,"Vacuum",
                attributes));
        ItemAndCategoryController.getInstance().addItem("Oven456","Benz"
                ,"this is oven",5000,10,"Oven",attributes1);
        ItemAndCategoryController.getInstance().addItem("microwave67","Benz",
                "this is microWave",600,10,"microwave",attributes2);
        UserController.getInstance().logout();
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    public void deleteJunk(){
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("TestCategory");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        UserController.getInstance().logout();
    }

    @Test
    public void getCurrentCategory() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        ItemAndCategoryController.getInstance().openCategory("lavazem manzel");
        System.out.println(ItemAndCategoryController.getInstance().getCurrentCategory().getName());
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
    }



    @Test
    public void getCategoryItems() {
        addItem();
        ArrayList <String>allItems=ItemAndCategoryController.getInstance().getCategoryItems("lavazem manzel");
        for(String id:allItems){
            System.out.println(id);
        }
        addItem();
        ArrayList<Item>allItems1=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems1) Database.getInstance().deleteItem(item);
        deleteJunk();
    }


    @Test
    public void getBaseCategory() {
        addItem();
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        System.out.println(ItemAndCategoryController.getInstance().getBaseCategory().getName());
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
    }

    @Test
    public void previousCategory() {
        addItem();
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        System.out.println(ItemAndCategoryController.getInstance().previousCategory("Vacuum"));
        System.out.println(ItemAndCategoryController.getInstance().previousCategory("Main"));
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
    }

    @Test
    public void openCategory() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        System.out.println(ItemAndCategoryController.getInstance().openCategory("Vacuum"));
        System.out.println(ItemAndCategoryController.getInstance().openCategory("sdfsdf"));
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
    }



    @Test
    public void getAllItemFromDataBase() {
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            System.out.println(item.getId());
        }
        for(Item item:allItems) Database.getInstance().deleteItem(item);
        deleteJunk();
    }

    @Test
    public void editCategoryName() {
        addItem();
        ItemAndCategoryController.getInstance().renameCategory("lavazem manzel","Home appliance");
        deleteJunk();
    }

    @Test
    public void removeCategory() {
        addItem();
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        deleteJunk();
    }

  /*  @Test
    public void getInSaleItems() {
        addItem();
        UserController.getInstance().registerSeller(500,"Ali4","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User user=UserController.getInstance().getUserByUsername("Ali4");
        UserController.getInstance().login("Ali4",user.getPassword());
        String startTime="20-04-2003 21:30";
        String endTime="05-02-2005 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        ArrayList<String>saleItems=new ArrayList<>();
        SaleAndDiscountCodeController.getInstance().addSale(dateTime,dateTime1,20,saleItems);
        acceptRequests();
        addItem();
        ArrayList<Item>allItems= ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        saleItems.add(allItems.get(0).getId());
        SaleAndDiscountCodeController.getInstance().addSale(dateTime,dateTime1,20,saleItems);
        acceptRequests();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        Sale sale=allSales.get(0);
        for(Item item:allItems){
            SaleAndDiscountCodeController.getInstance().addItemToSale(item.getId(),sale.getId());
        }
        System.out.println(ItemAndCategoryController.getInstance().getInSaleItems());
    } */

    @Test
    public void editItem(){
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        Item item=allItems.get(0);
        ItemAndCategoryController.getInstance().editItem("price","5000",item.getId());
        acceptRequests();
        ItemAndCategoryController.getInstance().editItem("name","ACC",item.getId());
        acceptRequests();
        ItemAndCategoryController.getInstance().editItem("brand","Khazar",item.getId());
        acceptRequests();
        ItemAndCategoryController.getInstance().editItem("description","this is a xamp",item.getId());
        acceptRequests();
        ItemAndCategoryController.getInstance().editItem("inStock","600",item.getId());
        acceptRequests();
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }
    @Test
    public void viewItem(){
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        System.out.println(ItemAndCategoryController.getInstance().viewItem("sdfsdfsdfsdf"));
        Item item=allItems.get(0);
        System.out.println(ItemAndCategoryController.getInstance().viewItem(item.getId()));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void addView(){
        addItem();
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Item item1=allItems.get(0);
        ItemAndCategoryController.getInstance().addView(item1.getId());
        ItemAndCategoryController.getInstance().addView("sdfsdfsdfsdfsdf");
        for(Item item:allItems)Database.getInstance().deleteItem(item);
        deleteJunk();
    }


    @Test
    public void ItemHasAttribute(){
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Item item=allItems.get(0);
        System.out.println(ItemAndCategoryController.getInstance().doesItemHaveAttribute(item.getId(),"price"));
        System.out.println(ItemAndCategoryController.getInstance().doesItemHaveAttribute(item.getId(),"price"));
        System.out.println(item.getAttributes().get("price"));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }



    @Test
    public void doesSellerHadItem(){
        addItem();
        Seller user=(Seller) UserController.getInstance().getUserByUsername("TestCategory");
        System.out.println(user.getUsername());
        UserController.getInstance().login(user.getUsername(),user.getPassword());
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        System.out.println(UserController.getInstance().doesSellerHaveItem("sdf"));
        Item item=allItems.get(0);
        System.out.println(UserController.getInstance().doesSellerHaveItem(item.getId()));
        for(Item item1:allItems) Database.getInstance().deleteItem(item1);
        deleteJunk();
    }

    @Test
    public void sellerItems(){
        addItem();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        Seller seller= (Seller) UserController.getInstance().getUserByUsername("TestCategory");
        UserController.getInstance().login(seller.getUsername(),seller.getPassword());
        System.out.println(UserController.getInstance().getSellerItems());
        UserController.getInstance().logout();
        System.out.println(UserController.getInstance().getSellerItems());
        for(Item item:allItems)Database.getInstance().deleteItem(item);
        deleteJunk();
    }




}