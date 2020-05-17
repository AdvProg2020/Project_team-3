package Controller;

import Model.Category;
import Model.DiscountCode;
import Model.Item;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.DoubleAccumulator;

public class SaleAndDiscountCodeControllerTest {


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

    public void addItem() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("Ali");
        UserController.getInstance().login(seller.getUsername(),seller.getPassword());
        addCategory();
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","expensive");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz"
                ,"this is vaccum",500,10,"Vacuum",
                attributes);
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

    @Test
    public void addSale() {
        addItem();
        acceptRequests();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        User user=UserController.getInstance().getUserByUsername("Ali");
        UserController.getInstance().login("Ali",user.getPassword());
        String startTime="20-02-2005 21:30";
        String endTime="12-02-2007 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        ArrayList<String>saleItems=new ArrayList<>();
        saleItems.add(allItems.get(0).getId());
        SaleAndDiscountCodeController.getInstance().addSale(dateTime,dateTime1,20,saleItems);
        acceptRequests();
    }

    @Test
    public void addDiscountCode() {
        ArrayList<String>validUsers=new ArrayList<>();
        String startTime="25-02-2014 22:30";
        String endTime="27-02-2020 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        SaleAndDiscountCodeController.getInstance().addDiscountCode(20,dateTime1,dateTime,validUsers
                ,6,50);
    }


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
        for(DiscountCode discountCode:allDiscountCodes) Database.getInstance().deleteDiscountCode(discountCode);
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
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
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
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
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
        for(DiscountCode discountCode:allDiscountCodes) Database.getInstance().deleteDiscountCode(discountCode);
    }



    @Test
    public void deleteSale() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        SaleAndDiscountCodeController.getInstance().deleteSale(allSales.get(0).getId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(allSales.get(0).getId()));
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
    }

    @Test
    public void deleteDiscountCode() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        SaleAndDiscountCodeController.getInstance().deleteDiscountCode(allDiscountCodes.get(0).getDiscountId());
        // Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(allDiscountCodes.get(0).getDiscountId()));
       for(DiscountCode discountCode:allDiscountCodes) Database.getInstance().deleteDiscountCode(discountCode);
    }


    @Test
    public void getAllDiscountCodesFromDataBase() {
        addDiscountCode();
        ArrayList<DiscountCode>discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        for(DiscountCode discountCode:discountCodes){
            System.out.println(discountCode.getDiscountId());
        }
        for(DiscountCode discountCode:discountCodes) Database.getInstance().deleteDiscountCode(discountCode);
        for(DiscountCode discountCode:discountCodes) Database.getInstance().deleteDiscountCode(discountCode);
    }

    @Test
    public void getAllSaleFromDataBase() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        for(Sale sale:allSales){
            System.out.println(sale.getId());
        }
        for(Sale sale:allSales) Database.getInstance().deleteSale(sale);
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
        Database.getInstance().deleteDiscountCode(discountCode);
    }

    @Test
    public void editDiscountCodeEndTime() {
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        String endTime="2020-02-01 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountCode.getDiscountId()
                ,dateTime1);
        System.out.println(discountCode.getEndTime().toString());
       // Database.getInstance().deleteDiscountCode(discountCode);
    }
    @Test
    public void editDiscountCodeUsage(){
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountCode.getDiscountId(),
                10);
       Database.getInstance().deleteDiscountCode(discountCode);
    }


    @Test
    public void printDiscount() {
        addDiscountCode();
        ArrayList<DiscountCode> allDiscounts=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=allDiscounts.get(0);
        SaleAndDiscountCodeController.getInstance().printDiscount(discountCode.getDiscountId());
        for(DiscountCode discountCode1:allDiscounts) Database.getInstance().deleteDiscountCode(discountCode1);
    }

    @Test
    public void editSale() {
        //addSale();
        ArrayList<Sale> allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        String startTime="25-02-2012 22:30";
        String endTime="27-02-2015 22:30";
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"off Percentage","90");
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"start Time",startTime);
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"end Time",endTime);
        acceptRequests();
        Database.getInstance().deleteSale(allSales.get(0));
    }

    @Test
    public void getSellerSale(){
        addSale();
        System.out.println(SaleAndDiscountCodeController.getInstance().getSellerSales("alima"));
        Database.getInstance().deleteUser(UserController.getInstance().getUserByUsername("alima"));
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
    }

    @Test
    public void giveGiftDiscount(){
        UserController.getInstance().registerBuyer(500,"Arman","Hitler",
                "Arman","S","arman@gmail.com","33151603");
        SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode("Arman");
        ArrayList<DiscountCode>allDiscounts=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        for(DiscountCode discountCode:allDiscounts)Database.getInstance().deleteDiscountCode(discountCode);
    }

}