package Project.Controller;

import Project.Model.Category;
import Project.Model.DiscountCode;
import Project.Model.Item;
import Project.Model.Requests.Request;
import Project.Model.Sale;
import Project.Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

public class SaleAndDiscountCodeControllerTest {

    @Test
    public void runTest(){Database.getInstance().initiate();}

    public void addCategory() {
        Category category1=ItemAndCategoryController.getInstance().getCategoryByName("Project.Main");
        ArrayList<String>attributes=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("lavazem manzel",attributes,"Project.Main");
        ArrayList<String>attributes1=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Vacuum",attributes1,"lavazem manzel");
        ArrayList<String>attributes2=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("Oven",attributes2,"lavazem manzel");
        ArrayList<String>attributes3=new ArrayList<>();
        ItemAndCategoryController.getInstance().addCategory("microwave",attributes3,"lavazem manzel");
    }

    public void addItem() {
        UserController.getInstance().registerSeller(500,"TestSale","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        acceptRequests();
        User seller =UserController.getInstance().getUserByUsername("TestSale");
        System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
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
        SaleAndDiscountCodeController.getInstance().deleteDeprecatedSales(LocalDateTime.now());
        addItem();
        acceptRequests();
        ArrayList<Item>allItems=ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        User user=UserController.getInstance().getUserByUsername("TestSale");
        UserController.getInstance().login("TestSale",user.getPassword());
        String startTime="2005-02-13T21:30";
        String endTime="2007-02-14T22:30";
        LocalDateTime dateTime = LocalDateTime.parse(startTime);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime);
        ArrayList<String>saleItems=new ArrayList<>();
        saleItems.add(allItems.get(0).getId());
        System.out.println(SaleAndDiscountCodeController.getInstance().addSale(dateTime,dateTime1,20,saleItems));
        acceptRequests();
        assertNotNull(ItemAndCategoryController.getInstance().getItemById(saleItems.get(0)).showIdWithName());
        Assert.assertTrue(!SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase().isEmpty());
        assertNotNull(SaleAndDiscountCodeController.getInstance().getSellerSalesString("TestSale"));
        assertNotNull(SaleAndDiscountCodeController.getInstance().getAllItemsIDWithSale());
        //deleteJunk();
    }

    @Test
    public void addDiscountCode() {
        SaleAndDiscountCodeController.getInstance().deleteDeprecatedDiscountCodes(LocalDateTime.now());
        ArrayList<String>validUsers=new ArrayList<>();
        String startTime="2014-02-25T22:30";
        String endTime="2020-02-27T22:30";
        LocalDateTime dateTime = LocalDateTime.parse(startTime);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime);
        SaleAndDiscountCodeController.getInstance().addDiscountCode(20,dateTime1,dateTime,validUsers
                ,6,50);
        Assert.assertTrue(!SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase().isEmpty());
    }

    public void deleteJunk(){
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("TestSale");
        UserController.getInstance().deleteUser("Arman");
        if(ItemAndCategoryController.getInstance().getAllItemFromDataBase().size()!=0)
        UserController.getInstance().deleteItemFromSeller(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId(),"TestSale");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        File file=new File("Resource");
        file.delete();
        Database.getInstance().initiate();
        UserController.getInstance().logout();
    }

    public void acceptRequests(){
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    @Test
    public void getInstance() {
        SaleAndDiscountCodeController saleAndDiscountCodeController=SaleAndDiscountCodeController.getInstance();
        assertNotNull(saleAndDiscountCodeController);
    }

    @Test
    public void getDiscountCodeById() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        for(DiscountCode discountCode:allDiscountCodes){
            Assert.assertTrue(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountCode.getDiscountId()));
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
        deleteJunk();
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
        deleteJunk();
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
        deleteJunk();
    }



    @Test
    public void deleteSale() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        SaleAndDiscountCodeController.getInstance().deleteSale(allSales.get(0).getId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(allSales.get(0).getId()));
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
        deleteJunk();
    }

    @Test
    public void deleteDiscountCode() {
        addDiscountCode();
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        SaleAndDiscountCodeController.getInstance().deleteDiscountCode(allDiscountCodes.get(0).getDiscountId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(allDiscountCodes.get(0).getDiscountId()));
       for(DiscountCode discountCode:allDiscountCodes) Database.getInstance().deleteDiscountCode(discountCode);
       deleteJunk();
    }


    @Test
    public void getAllDiscountCodesFromDataBase() {
        addDiscountCode();
        ArrayList<DiscountCode>discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        for(DiscountCode discountCode:discountCodes){
            System.out.println(discountCode.getDiscountId());
        }
        Assert.assertTrue(!discountCodes.isEmpty());
        for(DiscountCode discountCode:discountCodes) Database.getInstance().deleteDiscountCode(discountCode);
        for(DiscountCode discountCode:discountCodes) Database.getInstance().deleteDiscountCode(discountCode);
        deleteJunk();
    }

    @Test
    public void getAllSaleFromDataBase() {
        addSale();
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        for(Sale sale:allSales){
            System.out.println(sale.getId());
        }
        Assert.assertTrue(!allSales.isEmpty());
        for(Sale sale:allSales) Database.getInstance().deleteSale(sale);
        deleteJunk();
    }
    @Test
    public void editDiscountCodePercentage() {
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountCode.getDiscountId(),50);
        discountCodes.clear();
        discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        Assert.assertEquals(discountCodes.get(0).getDiscountPercentage(),50,3);
        Database.getInstance().deleteDiscountCode(discountCode);
        deleteJunk();
    }

    @Test
    public void editDiscountCodeEndTime() {
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        String endTime="2020-02-01T22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountCode.getDiscountId()
                ,dateTime1);
        discountCodes.clear();
        discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        Assert.assertEquals(discountCodes.get(0).getEndTime(),dateTime1);
        System.out.println(discountCode.getEndTime().toString());
        deleteJunk();
        Database.getInstance().deleteDiscountCode(discountCode);
    }
    @Test
    public void editDiscountCodeUsage(){
        addDiscountCode();
        ArrayList<DiscountCode> discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=discountCodes.get(0);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountCode.getDiscountId(),
                10);
        discountCodes.clear();
        discountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        Assert.assertEquals(discountCodes.get(0).getUsageCountInt(),10);
       Database.getInstance().deleteDiscountCode(discountCode);
       deleteJunk();
    }


    @Test
    public void printDiscount() {
        addDiscountCode();
        ArrayList<DiscountCode> allDiscounts=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        DiscountCode discountCode=allDiscounts.get(0);
        Assert.assertNotNull(allDiscounts);
        Assert.assertNotNull(allDiscounts.get(0));
        SaleAndDiscountCodeController.getInstance().printDiscount(discountCode.getDiscountId());
        for(DiscountCode discountCode1:allDiscounts) Database.getInstance().deleteDiscountCode(discountCode1);
        deleteJunk();
    }

    @Test
    public void editSale() {
        addSale();
        ArrayList<Sale> allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        String startTime="2006-02-25T22:30";
        String endTime="2007-02-11T22:30";
        if(allSales.size()==0) System.out.println("ssss");
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"off Percentage","90");
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"start Time",startTime);
        acceptRequests();
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"end Time",endTime);
        acceptRequests();
        allSales.clear();
        allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        Sale sale=allSales.get(0);
        Assert.assertEquals(sale.getOffPercentage(),90);
        Assert.assertEquals(sale.getStartTime(),LocalDateTime.parse(startTime));
        Assert.assertEquals(sale.getEndTime(),LocalDateTime.parse(endTime));
        Assert.assertNotNull(sale);
        Assert.assertEquals(sale.getOffPercentage(),90);
        Database.getInstance().deleteSale(allSales.get(0));
    }

    @Test
    public void getSellerSale(){
        addSale();
        System.out.println(SaleAndDiscountCodeController.getInstance().getSellerSales("TestSale"));
        Assert.assertNotNull(SaleAndDiscountCodeController.getInstance().getSellerSales("TestSale"));
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        Assert.assertNotNull(allSales);
        for(Sale sale:allSales)Database.getInstance().deleteSale(sale);
        deleteJunk();
    }

    @Test
    public void giveGiftDiscount(){
        UserController.getInstance().registerBuyer(500,"Arman","Hitler",
                "Arman","S","arman@gmail.com","33151603");
        SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode("Arman");
        ArrayList<DiscountCode>allDiscounts=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        Assert.assertTrue(allDiscounts.get(0).hasUser("Arman"));
        for(DiscountCode discountCode:allDiscounts)Database.getInstance().deleteDiscountCode(discountCode);
        Assert.assertNotNull(allDiscounts.get(0));
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("Ali");
        UserController.getInstance().logout();
        deleteJunk();
    }

}