package Control;

import Model.DiscountCode;
import Model.Sale;

public class SaleAndDiscountCodeController {
    Controller controller = Controller.getInstance();
    public DiscountCode getDiscountCodeById(String id) {
        for (DiscountCode discountCode : controller.allDiscountCodes) {
            if (discountCode.getDiscountId().equals(id)) {
                return discountCode;
            }
        }
        return null;
    }

    public Sale getSaleById(String id) {
        for (Sale sale : controller.allSales) {
            if (sale.getId().equals(id)) {
                return sale;
            }
        }
        return null;
    }

    public boolean isThereSaleWithId(String id){
        return false;
    }

    public void addSale(Sale sale){}

    public void addDiscountCode(DiscountCode discountCode){}

    public void deleteSale(String id){}

    public void deleteDiscountCode(String id){}

    public boolean isThereDiscountCodeWithId(String id){
        return false;
    }
}