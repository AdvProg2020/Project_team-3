package Controller;

import Model.Sale;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaleAndDiscountCodeControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void getDiscountCodeById() {
    }

    @Test
    public void getSaleById() {
    }

    @Test
    public void isThereSaleWithId() {
    }

    @Test
    public void addSale() {
        Sale sale=new Sale(34,35,20);
        SaleAndDiscountCodeController.getInstance().addSale(sale);
    }

    @Test
    public void deleteSale() {
    }

    @Test
    public void deleteDiscountCode() {
    }

    @Test
    public void isThereDiscountCodeWithId() {
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
    }
}