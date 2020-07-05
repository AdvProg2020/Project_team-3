package Project.Model;

import Project.Controller.ItemAndCategoryController;
import Project.Controller.SortAndFilterControllerTest;
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
    public void getOffPercentage() {
    assertEquals(createSale().getOffPercentage(),20);
    }


    @Test
    public void getSellerUsername() {
        assertEquals(createSale().getSellerUsername(),"testShop");
    }

}