package Server.Controller;

import Server.Model.Requests.Request;
import Server.Model.Users.User;
import Project.Client.CLI.View;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class SortAndFilterControllerTest {

    public static void addItem() {
        UserController.getInstance().registerSeller(500,"testShop","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("testShop");
        System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","expensive");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        ItemAndCategoryController.getInstance().addCategory("testSort",null,"Main");
        ItemAndCategoryController.getInstance().addItem("Vacuum345search","Benz"
                ,"this is vaccum",500,10,"testSort",
                attributes);
        ItemAndCategoryController.getInstance().addItem("Oven456search","Benz"
                ,"this is oven",5000,10,"testSort",attributes1);
        ItemAndCategoryController.getInstance().addItem("microwave67search","Benz",
                "this is microWave",600,10,"testSort",attributes2);
        UserController.getInstance().logout();
        ArrayList<Request> allRequests= RequestController.getInstance().getAllRequestFromDataBase();
        acceptRequests();
    }

    public static void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    public static void deleteJunk(){
        UserController.getInstance().login("admin","12345");
        System.out.println(UserController.getInstance().deleteUser("testShop"));
        ItemAndCategoryController.getInstance().removeCategory("testSort");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        UserController.getInstance().logout();
    }
    @Test
    public void getInstance() {
        SortAndFilterController sortAndFilterController=SortAndFilterController.getInstance();
        assertNotNull(sortAndFilterController);
    }

    @Test
    public void show() {
        Database.getInstance().initiate();
        addItem();
        SortAndFilterController.getInstance().activateFilterPriceRange(0,6000);
        SortAndFilterController.getInstance().activateFilterCategoryName("testSort");
        SortAndFilterController.getInstance().activateFilterName("search");
        SortAndFilterController.getInstance().activateFilterBrandName("Benz");
        SortAndFilterController.getInstance().activateFilterAvailability();
        SortAndFilterController.getInstance().activateFilterAttribute("price","cheap");
        SortAndFilterController.getInstance().show("testSort");
        SortAndFilterController.getInstance().activateSort("sort by price low to high");
        ArrayList ans=SortAndFilterController.getInstance().show("testSort");
        SortAndFilterController.getInstance().activateSort("sort by price high to low");
        ans.addAll(SortAndFilterController.getInstance().show("testSort"));
        SortAndFilterController.getInstance().activateSort("sort by rating");
        ans.addAll(SortAndFilterController.getInstance().show("testSort"));
        SortAndFilterController.getInstance().activateSort("sort by comment count");
        ans.addAll(SortAndFilterController.getInstance().show("testSort"));
        SortAndFilterController.getInstance().activateSort("sort by date");
        ans.addAll(SortAndFilterController.getInstance().show("testSort"));
        Matcher matcher= View.getMatcher("[id: (\\S+) name: Vacuum345search price=500.0, id: (\\S+) name: microwave67search price=600.0, id: (\\S+) name: microwave67search price=600.0, id: (\\S+) name: Vacuum345search price=500.0, id: (\\S+) name: microwave67search price=600.0, id: (\\S+) name: Vacuum345search price=500.0, id: (\\S+) name: microwave67search price=600.0, id: (\\S+) name: Vacuum345search price=500.0]",ans.toString());
        System.out.println(ans.toString());
        assertTrue(matcher.find());
        deleteJunk();
    }


    @Test
    public void activateSort() {
        SortAndFilterController.getInstance().reset();
        SortAndFilterController.getInstance().activateSort("jidjdiejdowj");
        int ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,0);
        SortAndFilterController.getInstance().activateSort("sort by price low to high");
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,1);
        SortAndFilterController.getInstance().activateSort("sort by price high to low");
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,2);
        SortAndFilterController.getInstance().activateSort("sort by rating");
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,3);
        SortAndFilterController.getInstance().activateSort("sort by comment count");
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,4);
        SortAndFilterController.getInstance().activateSort("sort by date");
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,5);
        SortAndFilterController.getInstance().reset();
        ans=SortAndFilterController.getInstance().activeSort;
        assertEquals(ans,0);
    }

    @Test
    public void disableSort() {
        SortAndFilterController.getInstance().disableSort();
        assertEquals(SortAndFilterController.getInstance().activeSort,0);
    }

    @Test
    public void activateFilterPriceRange() {
        SortAndFilterController.getInstance().activateFilterPriceRange(10,1000);
        assertTrue(SortAndFilterController.getInstance().filterPriceRange);
    }

    @Test
    public void activateFilterCategoryName() {
        SortAndFilterController.getInstance().activateFilterCategoryName("test");
        assertTrue(SortAndFilterController.getInstance().filterCategoryName);
    }

    @Test
    public void activateFilterBrandName() {
        SortAndFilterController.getInstance().activateFilterBrandName("test");
        assertTrue(SortAndFilterController.getInstance().filterBrand);
    }

    @Test
    public void activateFilterName() {
        SortAndFilterController.getInstance().activateFilterName("test");
        assertTrue(SortAndFilterController.getInstance().filterName);
    }

    @Test
    public void activateFilterAvailability() {
        SortAndFilterController.getInstance().activateFilterAvailability();
        assertTrue(SortAndFilterController.getInstance().filterAvailability);
    }

    @Test
    public void activateFilterAttribute() {
        SortAndFilterController.getInstance().activateFilterAttribute("test","test");
        assertTrue(SortAndFilterController.getInstance().filterAttribute);
    }

    @Test
    public void activateFilterSellerName() {
        SortAndFilterController.getInstance().activateFilterSellerName("test");
        assertTrue(SortAndFilterController.getInstance().filterSellerName);
    }

    @Test
    public void disableFilterPriceRange() {
        SortAndFilterController.getInstance().disableFilterPriceRange();
        assertFalse(SortAndFilterController.getInstance().filterPriceRange);
    }

    @Test
    public void disableFilterCategoryName() {
        SortAndFilterController.getInstance().disableFilterCategoryName();
        assertFalse(SortAndFilterController.getInstance().filterCategoryName);
    }

    @Test
    public void disableFilterBrandName() {
        SortAndFilterController.getInstance().disableFilterBrandName();
        assertFalse(SortAndFilterController.getInstance().filterBrand);
    }

    @Test
    public void disableFilterName() {
        SortAndFilterController.getInstance().disableFilterName();
        assertFalse(SortAndFilterController.getInstance().filterName);
    }

    @Test
    public void disableFilterAvailability() {
        SortAndFilterController.getInstance().disableFilterAvailability();
        assertFalse(SortAndFilterController.getInstance().filterAvailability);
    }

    @Test
    public void disableFilterAttribute() {
        SortAndFilterController.getInstance().disableFilterAttribute();
        assertFalse(SortAndFilterController.getInstance().filterAttribute);
    }

    @Test
    public void disableFilterSellerName() {
        SortAndFilterController.getInstance().disableFilterSellerName();
        assertFalse(SortAndFilterController.getInstance().filterSellerName);
    }

    @Test
    public void showActiveFilters() {
        SortAndFilterController.getInstance().reset();
        assertEquals(SortAndFilterController.getInstance().showActiveFilters(),"You have no active filter(s)");
        SortAndFilterController.getInstance().activateFilterAttribute("test","test");
        SortAndFilterController.getInstance().activateFilterBrandName("test");
        SortAndFilterController.getInstance().activateFilterCategoryName("test");
        SortAndFilterController.getInstance().activateFilterName("test");
        SortAndFilterController.getInstance().activateFilterAvailability();
        SortAndFilterController.getInstance().activateFilterPriceRange(10,100);
        SortAndFilterController.getInstance().activateFilterSellerName("test");
        assertEquals(SortAndFilterController.getInstance().showActiveFilters(),"filter by brand\nfilter by category name: test\nfilter by price range min=10.0 max=100.0\nfilter by name: test\nfilter by attribute attribute Key: test attribute Value: test\nfilter by seller: test\nfilter by availability");
    }

    @Test
    public void showActiveSort() {
        SortAndFilterController.getInstance().activateSort("jidjdiejdowj");
        assertEquals(SortAndFilterController.getInstance().showActiveSort(),"You have no active sort , the items will be sorted by view count.");
        SortAndFilterController.getInstance().activateSort("sort by price low to high");
        assertEquals(SortAndFilterController.getInstance().showActiveSort(),"sort by price low to high");
        SortAndFilterController.getInstance().activateSort("sort by price high to low");
        assertEquals(SortAndFilterController.getInstance().showActiveSort(),"sort by price high to low");
        SortAndFilterController.getInstance().activateSort("sort by rating");
        assertEquals(SortAndFilterController.getInstance().showActiveSort(),"sort by rating");
        SortAndFilterController.getInstance().activateSort("sort by comment count");
        assertEquals(SortAndFilterController.getInstance().showActiveSort(), "sort by comment count");
        SortAndFilterController.getInstance().reset();
        assertEquals(SortAndFilterController.getInstance().showActiveSort(),"You have no active sort , the items will be sorted by view count.");
    }

    @Test
    public void showAllAvailableFilters() {
        String ans=SortAndFilterController.getInstance().showAllAvailableFilters();
        assertEquals(ans,"filter by price [min] to [max]\nfilter by category [name]\nfilter by brand [name]\nfilter by name [name]\nfilter by availability\nfilter by attribute [key] [value]\nfilter by seller [seller name]\nfilter sale");
    }

    @Test
    public void showAllAvailableSorts() {
        String ans=SortAndFilterController.getInstance().showAllAvailableSorts();
        assertEquals(ans,"sort by price low to high\nsort by price high to low\nsort by rating\nsort by comment count\nsort by date");
    }

    @Test
    public void reset() {
        SortAndFilterController.getInstance().reset();
        assertFalse(SortAndFilterController.getInstance().filterAttribute);
        assertFalse(SortAndFilterController.getInstance().filterName);
        assertFalse(SortAndFilterController.getInstance().filterCategoryName);
        assertFalse(SortAndFilterController.getInstance().filterPriceRange);
        assertFalse(SortAndFilterController.getInstance().filterSellerName);
        assertTrue(SortAndFilterController.getInstance().activeSort==0);
    }
}