package Controller;

import Model.Category;
import Model.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAndCategoryControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void deleteItem() {
    }

    @Test
    public void isThereItemWithId() {
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            Assert.assertTrue(ItemAndCategoryController.getInstance().isThereItemWithId(item.getId()));
        }
        Assert.assertFalse(ItemAndCategoryController.getInstance().isThereItemWithId("Behaeen"));
    }

    @Test
    public void isThereCategoryWithName() {
        Assert.assertTrue(ItemAndCategoryController.getInstance().isThereCategoryWithName("Oven"));
        Assert.assertFalse(ItemAndCategoryController.getInstance().isThereCategoryWithName("Behaeen"));
    }

    @Test
    public void getItemById() {
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            System.out.println(ItemAndCategoryController.getInstance().getItemById(item.getId()).getId());
        }

    }

    @Test
    public void getCategoryByName() {
        Category category=ItemAndCategoryController.getInstance().getCategoryByName("Vacuum");
        Category category1=ItemAndCategoryController.getInstance().getCategoryByName("Behaeen");
        Assert.assertNotNull(category);
        Assert.assertNull(category1);
    }

    @Test
    public void searchItemInCategory() {

    }

    @Test
    public void compare() {
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.size()<2) return;
        String string=ItemAndCategoryController.getInstance().compare(allItems.get(0).getId(),allItems.get(1).getId());
        System.out.println(string);
    }

    @Test
    public void showItemComments() {
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        ArrayList<String>string=ItemAndCategoryController.getInstance().showItemComments(allItems.get(0).getId());
        for(String attribute:string){
            System.out.println(string);
        }
    }

    @Test
    public void comment() {
        UserController.getInstance().login("Alireza","Behaeen");
        String text="that was very nice!";
        ArrayList<Item>allItems= ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        Item item=allItems.get(0);
        System.out.println(item.getId());
        ItemAndCategoryController.getInstance().comment(text,item.getId());
    }

    @Test
    public void rate() {
    }

    @Test
    public void digest() {
    }

    @Test
    public void showAttributes() {
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        String attributes=ItemAndCategoryController.getInstance().showAttributes(allItems.get(0).getId());
        System.out.println(attributes);
    }

    @Test
    public void addCategory() {
        ArrayList<String>attributes=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("lavazem manzel",attributes);
        ArrayList<String>attributes1=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Vacuum",attributes1,"lavazem manzel");
        ArrayList<String>attributes2=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Oven",attributes2,"lavazem manzel");
        ArrayList<String>attributes3=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("microwave",attributes3,"lavazem manzel");
    }

    @Test
    public void testAddCategory() {
    }

    @Test
    public void sortBy() {
    }

    @Test
    public void filterBy() {
    }

    @Test
    public void addItem() {
        UserController.getInstance().login("Alireza","Behaeen");
        addCategory();
        HashMap<String,String>attributes=new HashMap<>();
        HashMap<String , String>attributes1=new HashMap();
        HashMap<String,String>attributes2=new HashMap<>();
        ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz"
        ,"this is vaccum",500,300,"Vacuum",
                attributes);
        ItemAndCategoryController.getInstance().addItem("Oven456","Benz"
        ,"this is oven",5000,3000,"Oven",attributes1);
        ItemAndCategoryController.getInstance().addItem("microwave67","Benz",
                "this is microWave",600,200,"microwave",attributes2);
    }

    @Test
    public void addItemToCategory() {

    }

    @Test
    public void getCurrentCategory() {
    }

    @Test
    public void getCurrentViewableItems() {
    }

    @Test
    public void getCurrentViewableItemsString() {
    }

    @Test
    public void setViewableToCategory() {
    }

    @Test
    public void getCategoryItems() {
    }

    @Test
    public void getCategoryItemsString() {
    }

    @Test
    public void getBaseCategory() {
    }

    @Test
    public void previousCategory() {
    }

    @Test
    public void openCategory() {
    }

    @Test
    public void currentViewableItemsContainsItem() {
    }

    @Test
    public void getAllItemFromDataBase() {
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        for(Item item:allItems){
            System.out.println(item.getId());
        }
    }

    @Test
    public void editCategoryName() {
        addCategory();
        ItemAndCategoryController.getInstance().editCategoryName("lavazem manzel","Home appliance");
    }

    @Test
    public void removeCategory() {
        addCategory();
        addItem();
        ItemAndCategoryController.getInstance().removeCategory("Vacuum");
    }

    @Test
    public void getInSaleItems() {
    }

    @Test
    public void editItem(){
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        if(allItems.isEmpty()) return;
        Item item=allItems.get(0);
        ItemAndCategoryController.getInstance().editItem("price","5000",item.getId());
        ItemAndCategoryController.getInstance().editItem("name","ACC",item.getId());
        ItemAndCategoryController.getInstance().editItem("brand","Khazar",item.getId());
        ItemAndCategoryController.getInstance().editItem("description","this is a xamp",item.getId());
        ItemAndCategoryController.getInstance().editItem("inStock","600",item.getId());
    }

}