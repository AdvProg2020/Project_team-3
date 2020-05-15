package View.Menus;

import Controller.CartController;
import Controller.SaleAndDiscountCodeController;

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
        System.out.println(View.ANSI_CYAN + "You are in the purchase menu." + View.ANSI_RESET);
        String address = readAddress();
        discountCode(address);

    }

    @Override
    public void execute(String command) {

    }

    @Override
    public void help() {

    }

    public String readAddress() {
        System.out.print("Please enter your address:");
        return View.read.nextLine();
    }

    public void discountCode(String address) {
        System.out.println("please enter your discount code\nif you don't have one enter --continue--");
        String discountId = View.read.nextLine();
        if (discountId.equals("continue")) {
            pay(address,false,discountId);
            return;
        }
        if (!SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountId)) {
            System.out.println("invalid id");
            discountCode(address);
            return;
        }
        if(!SaleAndDiscountCodeController.getInstance().userCanUseDiscountCode(discountId)){
            System.out.println("Deprecated discount code. Try again.");
            discountCode(address);
            return;
        }
        System.out.println("Successful: price before discount = " + CartController.getInstance().getCartPriceWithoutDiscountCode() + "price after discount = " + CartController.getInstance().getCartPriceWithDiscountCode(discountId));
        pay(address,true,discountId);
    }

    public void pay(String address,boolean hasDiscount,String discountID) {
        System.out.println("Are you sure you want to buy these items? (enter yes to continue or anything else to exit process)");
        String command = View.read.nextLine();
        if (command.equals("yes")) {
            if(hasDiscount)
                System.out.println(CartController.getInstance().buy(address,discountID));
            else
                System.out.println(CartController.getInstance().buy(address));
            CartMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            View.setCurrentMenu(CartMenu.getInstance());

            return;
        }
        View.setCurrentMenu(CartMenu.getInstance());
        CartMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
    }
}
