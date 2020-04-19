package Control;

import Model.DiscountCode;
import Model.Requests.Request;
import Model.Sale;

public class SaleAndDiscountCodeController {
    Controller controller = Controller.getInstance();
    private static SaleAndDiscountCodeController saleAndDiscountCodeController;

    private SaleAndDiscountCodeController(){}

    public static SaleAndDiscountCodeController getInstance(){
        if(saleAndDiscountCodeController==null){
            saleAndDiscountCodeController=new SaleAndDiscountCodeController();
        }
        return saleAndDiscountCodeController;
    }


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
        for(Sale sale:controller.allSales){
            if(sale.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public String addSale(Sale sale){
        String requestId=controller.addId(Request.getIdCount());
        RequestController.getInstance().addSaleRequest(requestId,sale);
        return "your request for adding Sale was sent to our Admins!";
    }

    public void addDiscountCode(DiscountCode discountCode){
        controller.allDiscountCodes.add(discountCode);
    }

    public void deleteSale(String id){
        for(Sale sale:controller.allSales){
            if(sale.getId().equals(id)){
                controller.allSales.remove(sale);
            }
        }
    }

    public void deleteDiscountCode(String id){
        for(DiscountCode discountCode:controller.allDiscountCodes){
            if(discountCode.getDiscountId().equals(id)){
                controller.allDiscountCodes.remove(discountCode);
            }
        }
    }

    public boolean isThereDiscountCodeWithId(String id){
        for(DiscountCode discountCode:controller.allDiscountCodes){
            if(discountCode.getDiscountId().equals(id)){
                return true;
            }
        }
        return false;
    }
}