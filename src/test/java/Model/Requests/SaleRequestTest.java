package Model.Requests;

import Model.Sale;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SaleRequestTest extends TestCase {

    public SaleRequest addRequest(){
        String startTime="1399-02-25 21:30";
        String endTime="1399-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        ArrayList<String> allItems=new ArrayList<>();
        Sale sale=new Sale(dateTime,dateTime1,20,"sdfsdf",allItems);
        SaleRequest saleRequest=new SaleRequest("sdfsdf",sale);
        return  saleRequest;
    }

    public void testGetNewSale() {
        System.out.println(addRequest().getNewSale().getId());
    }

    public void testTestToString() {
        System.out.println(addRequest().toString());
    }
}