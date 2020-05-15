package Controller;

import Model.DiscountCode;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SaleAndDiscountCodeControllerTest {

    public void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getDiscountCodeById() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        for(DiscountCode discountCode:allDiscountCodes){
            System.out.println(discountCode.getDiscountId());
        }
        Assert.assertNull(SaleAndDiscountCodeController.getInstance().getDiscountCodeById("Behaeen"));
    }

    @Test
    public void getSaleById() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        for(Sale sale:allSales){
            System.out.println(sale.getId());
        }
        Assert.assertNull(SaleAndDiscountCodeController.getInstance().getSaleById("Behaeen"));
    }

    @Test
    public void isThereSaleWithId() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        for(Sale sale:allSales){
            Assert.assertTrue(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(sale.getId()));
        }
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId("Behaeen"));
    }

    @Test
    public void addSale() {
        User user=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login("Alireza",user.getPassword());
        String startTime="1399-02-25 21:30";
        String endTime="1399-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        ArrayList<String>saleItems=new ArrayList<>();
        SaleAndDiscountCodeController.getInstance().addSale(dateTime,dateTime1,20,saleItems);
        acceptRequests();
    }

    @Test
    public void deleteSale() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        SaleAndDiscountCodeController.getInstance().deleteSale(allSales.get(0).getId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(allSales.get(0).getId()));
    }

    @Test
    public void deleteDiscountCode() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        SaleAndDiscountCodeController.getInstance().deleteDiscountCode(allDiscountCodes.get(0).getDiscountId());
        // Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(allDiscountCodes.get(0).getDiscountId()));
    }

    @Test
    public void isThereDiscountCodeWithId() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        for(DiscountCode discountCode:allDiscountCodes){
            Assert.assertTrue(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountCode.getDiscountId()));
        }
        SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId("Behaeen");
    }

    @Test
    public void getAllDiscountCodesFromDataBase() {
        addDiscountCode();
        ArrayList<DiscountCode>discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        for(DiscountCode discountCode:discountCodes){
            System.out.println(discountCode.getDiscountId());
        }
    }

    @Test
    public void getAllSaleFromDataBase() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        for(Sale sale:allSales){
            System.out.println(sale.getId());
        }
    }

    @Test
    public void printItemsWithSale() {
    }

    @Test
    public void editDiscountCodePercentage() {
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountCode.getDiscountId(),50);
    }

    @Test
    public void editDiscountCodeEndTime() {
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        String endTime="1400-02-31 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountCode.getDiscountId()
                ,dateTime1);
    }
    @Test
    public void editDiscountCodeUsage(){
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountCode.getDiscountId(),
                10);
    }

    @Test
    public void addDiscountCode() {
        ArrayList<String>validUsers=new ArrayList<>();
        String startTime="2014-02-25 22:30";
        String endTime="2020-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        SaleAndDiscountCodeController.getInstance().addDiscountCode(20,dateTime1,dateTime,validUsers
                ,6,50);
    }

    @Test
    public void printDiscount() {
        addDiscountCode();
        ArrayList<DiscountCode> allDiscounts=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=allDiscounts.get(0);
        SaleAndDiscountCodeController.getInstance().printDiscount(discountCode.getDiscountId());
    }

    @Test
    public void editSale() {
        addSale();
        ArrayList<Sale> allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        String startTime="1400-02-25 22:30";
        String endTime="1400-02-27 22:30";
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"off Percentage","90");
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"start Time",startTime);
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"end Time",endTime);
        acceptRequests();
    }



}