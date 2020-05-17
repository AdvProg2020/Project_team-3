package Model;

import Controller.ItemAndCategoryController;
import Controller.SortAndFilterController;
import Controller.SortAndFilterControllerTest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SaleTest {

    public Sale createSale(){
        SortAndFilterControllerTest.addItem();
        String startTime="20-02-2005 21:30";
        String endTime="12-02-2007 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endTime, formatter);
        ArrayList<String> itemId=new ArrayList<>();
        itemId.add(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
        itemId.add(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(1).getId());
        return new Sale(dateTimeStart,dateTimeEnd,20,"testShop",itemId);
    }
    @Test
    public void acceptStatus() {
    }

    @Test
    public void editStatus() {
    }

    @Test
    public void addStatus() {
    }

    @Test
    public void getEndTime() {
    }

    @Test
    public void getStartTime() {
    }

    @Test
    public void getOffPercentage() {
    assertEquals(createSale().getOffPercentage(),20);
    }

    @Test
    public void setStartTime() {
    }

    @Test
    public void setEndTime() {
    }

    @Test
    public void setOffPercentage() {
    }

    @Test
    public void getId() {
    }

    @Test
    public void getAllItemId() {
    }

    @Test
    public void addItemToSale() {
    }

    @Test
    public void removeItemFromSale() {
    }

    @Test
    public void getSellerUsername() {
        assertEquals(createSale().getSellerUsername(),"testShop");
    }

    @Test
    public void itemsInfo() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void toSimpleString() {
    }
}