package View.Menus;

import Control.ItemAndCategoryController;
import Control.SaleAndDiscountCodeController;
import Control.UserController;

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
        pay();
    }

    @Override public void execute(String command){ }
    @Override public void help(){ }

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
        System.out.println("Successfull: price before discount="+ ItemAndCategoryController.getInstance().getCartPriceWithoutDiscountCode() +"price after discount="+ItemAndCategoryController.getInstance().getCartPriceWithDiscountCode());
    }

    public void pay(){
        System.out.println("Are you sure you want to buy these items?(enter yes to continue or anything else to exit process)");
        String command=View.read.nextLine();
        if(command.equals("yes")){
            System.out.println(UserController.getInstance().buy());
            return;
        }
           View.setCurrentMenu(CartMenu.getInstance());
           View.setPreviousMenu(MainMenu.getInstance());
    }
}
