package Controller;

import Model.DiscountCode;
import Model.Sale;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SaleAndDiscountCodeControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void getDiscountCodeById() {
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        for(DiscountCode discountCode:allDiscountCodes){
            System.out.println(discountCode.getDiscountId());
        }
        Assert.assertNull(SaleAndDiscountCodeController.getInstance().getDiscountCodeById("Behaeen"));
    }

    @Test
    public void getSaleById() {
       ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
       if(allSales.isEmpty()) return;
       for(Sale sale:allSales){
           System.out.println(sale.getId());
       }
       Assert.assertNull(SaleAndDiscountCodeController.getInstance().getSaleById("Behaeen"));
    }

    @Test
    public void isThereSaleWithId() {
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        for(Sale sale:allSales){
            Assert.assertTrue(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(sale.getId()));
        }
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId("Behaeen"));
    }

  /*  @Test
    public void addSale() {
        Sale sale=new Sale(2300,4500,20);
        SaleAndDiscountCodeController.getInstance().addSale(sale);
    } */

    @Test
    public void deleteSale() {
        ArrayList<Sale>allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        SaleAndDiscountCodeController.getInstance().deleteSale(allSales.get(0).getId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(allSales.get(0).getId()));
    }

    @Test
    public void deleteDiscountCode() {
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        SaleAndDiscountCodeController.getInstance().deleteDiscountCode(allDiscountCodes.get(0).getDiscountId());
        Assert.assertFalse(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(allDiscountCodes.get(0).getDiscountId()));

    }

    @Test
    public void isThereDiscountCodeWithId() {
        ArrayList<DiscountCode>allDiscountCodes=SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase();
        if(allDiscountCodes.isEmpty())return;
        for(DiscountCode discountCode:allDiscountCodes){
            Assert.assertTrue(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountCode.getDiscountId()));
        }
        SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId("Behaeen");
    }

    @Test
    public void getAllDiscountCodesFromDataBase() {
    }

    @Test
    public void getAllSaleFromDataBase() {
    }

    @Test
    public void printItemsWithSale() {
    }

    @Test
    public void editDiscountCodePercentage() {
    }

    @Test
    public void editDiscountCodeEndTime() {
    }

    @Test
    public void addDiscountCode() {
    }

    @Test
    public void printDiscount() {
    }

    @Test
    public void editSale() {
        ArrayList<Sale> allSales=SaleAndDiscountCodeController.getInstance().getAllSaleFromDataBase();
        if(allSales.isEmpty()) return;
        SaleAndDiscountCodeController.getInstance().editSale(allSales.get(0).getId(),"off Percentage","90");
    }
}