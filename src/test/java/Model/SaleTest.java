package Model;

import Model.Requests.ItemRequest;
import Model.Requests.SaleRequest;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SaleTest extends TestCase {

    public Sale addSale(){
         String startTime="1399-02-25 21:30";
         String endTime="1399-02-27 22:30";
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
         LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
         ArrayList<String> allItems=new ArrayList<>();
         Sale sale=new Sale(dateTime,dateTime1,20,"sdfsdf",allItems);
         return sale;
    }

    public void testAcceptStatus() {
        addSale().acceptStatus();
        System.out.println(addSale().status);
    }

    public void testEditStatus() {
        addSale().editStatus();
        System.out.println(addSale().status);
    }

    public void testAddStatus() {
        addSale().addStatus();
        System.out.println(addSale().status);
    }

    public void testGetEndTime() {
        System.out.println(addSale().getEndTime());
    }

    public void testGetStartTime() {
        System.out.println(addSale().getStartTime());
    }

    public void testGetOffPercentage() {
        System.out.println(addSale().getOffPercentage());
    }

    public void testSetStartTime() {
        String startTime="1399-02-25 21:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        addSale().setStartTime(dateTime);
    }

    public void testSetEndTime() {
        String endTime="1399-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        addSale().setEndTime(dateTime1);
    }

    public void testSetOffPercentage() {
        addSale().setOffPercentage(90);
        System.out.println(addSale().getOffPercentage());
    }

    public void testGetId() {
        System.out.println(addSale().getId());
    }

    public void testGetAllItemId() {
        HashMap<String,String> attributes=new HashMap<>();
        Item item=new Item("alireza","Benz","sdf","",500,
                "sdfsdf","asedf",attributes,600);
        addSale().addItemToSale(item.getId());
        System.out.println(addSale().getAllItemId());
    }


    public void testSaleHasItemWithID() {
        HashMap <String,String> attributes=new HashMap<>();
        Item item=new Item("alireza","Benz","sdf","",500,
                "sdfsdf","asedf",attributes,600);
        ItemRequest itemRequest=new ItemRequest("sdf",item);
        System.out.println(addSale().saleHasItemWithID(item.getId()));

    }

    public void testGetSellerUsername() {
        System.out.println(addSale().getSellerUsername());
    }

    public void testItemsInfo() {
        HashMap <String,String> attributes=new HashMap<>();
        Item item=new Item("alireza","Benz","sdf","",500,
                "sdfsdf","asedf",attributes,600);
        ItemRequest itemRequest=new ItemRequest("sdf",item);
        System.out.println(addSale().itemsInfo());
    }

    public void testTestToString() {
        System.out.println(addSale().toString());
    }

    public void testToSimpleString() {
        System.out.println(addSale().toSimpleString());
    }
}