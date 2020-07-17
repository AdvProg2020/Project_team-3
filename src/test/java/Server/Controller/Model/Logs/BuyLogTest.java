package Server.Controller.Model.Logs;

import Server.Controller.ItemAndCategoryController;
import Server.Model.Logs.BuyLog;
import Server.Controller.RequestController;
import Server.Controller.UserController;
import Server.Model.Item;
import Server.Model.Requests.Request;
import Server.Model.Users.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BuyLogTest {

    public static void addItems() {
        UserController.getInstance().registerSeller(500,"testLog","1234",
                "test","test","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("testLog");
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
        System.out.println(UserController.getInstance().deleteUser("testLog"));
        ItemAndCategoryController.getInstance().removeCategory("testSort");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        UserController.getInstance().logout();
    }

    @Test
    public void addItemAndGetPrice() {
        addItems();
        String Time="05-02-2005 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        BuyLog buyLog=new BuyLog("test","test",0, LocalDateTime.parse(Time, formatter));
        ArrayList<Item> allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        buyLog.addItem(500,10,allItems.get(0).getId(),"testLog");
        assertTrue(buyLog.hasItemID(allItems.get(0).getId()));
        assertEquals(buyLog.totalPrice(),5000,0);
        buyLog.addItem(100,10,allItems.get(1).getId(),"testLog");
        assertEquals(buyLog.totalPrice(),6000,0);
        deleteJunk();
    }

    @Test
    public void testToString() {
    }

    @Test
    public void toSimpleString() {
    }

    @Test
    public void getAllItemsID() {
    }

    @Test
    public void hasItemID() {
    }

    @Test
    public void getItemById() {
    }

    @Test
    public void getDeliveryState() {
    }

    @Test
    public void setDeliveryState() {
    }

    @Test
    public void totalPrice() {

    }

    @Test
    public void setTime() {
    }

    @Test
    public void setDiscountGrandTotal() {
    }
}