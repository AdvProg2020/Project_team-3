package Model.Requests;

import Model.Item;
import Model.Sale;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SaleEditTest extends TestCase {

    public SaleEdit addRequest(){
        SaleEdit saleEdit=new SaleEdit("sdf","sdf","sdfsdf","sdf");
        return  saleEdit;
    }


    public void testGetSaleID() {
        System.out.println(addRequest().getSaleID());
    }

    public void testGetNewFieldValue() {
        System.out.println(addRequest().getNewFieldValue());
    }

    public void testGetChangedFieled() {
        System.out.println(addRequest().getChangedFieled());
    }

    public void testTestToString() {
        System.out.println(addRequest().toString());
    }
}