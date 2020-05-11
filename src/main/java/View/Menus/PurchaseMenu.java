package View.Menus;

import Control.SaleAndDiscountCodeController;

public class PurchaseMenu extends Menu {
    private static PurchaseMenu purchaseMenu;

    private PurchaseMenu() {
    }

    public static PurchaseMenu getInstance() {
        if (purchaseMenu == null)
            purchaseMenu = new PurchaseMenu();
        return purchaseMenu;
    }
     @Override
    public void run() {
        System.out.println(View.ANSI_BLUE + "You are in the purchase menu." + View.ANSI_RESET);
        personalInfo();
        discountCode();
    }
    @Override
    public void execute(String command){

    }
    @Override
    public void help(){

    }
    public void personalInfo(){
        System.out.println("please enter your address");
        String address=View.read.nextLine();
    }

    public void discountCode(){
        System.out.println("please enter your discount code\nif you dont have one enter --continue--");
        String discountId=View.read.nextLine();
        if(discountId.equals("continue")){
            return;
        }
        if(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountId)==false){
            System.out.println("invalid id");
            discountCode();
            return;
        }
    }
}
