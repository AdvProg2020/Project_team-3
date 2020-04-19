package View.Menus;

public class BuyerMenu extends UserMenu {
    private static BuyerMenu buyerMenu;
    private int optionCount = 4;
    private BuyerMenu(){ }

    public static BuyerMenu getInstance(){
        if(buyerMenu==null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
    }
    @Override
    public void show(){

    }


    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

    public void viewCart(){

    }

    public void showProducts(){

    }

    public void viewProduct(){

    }

    public void increaseProduct(){

    }

    public void decreaseProduct(){

    }

    public void showTotalPrice(){

    }

    public void purchase(){

    }

    public void receiverInfo(){

    }

    public void enterDiscountCode(){

    }

    public void paymentMethod(){

    }

    public void shop(){
        //set current menu to shop
    }

    public void previousPurchases(){
        //set current menu to buyLog
    }

    public void showOrder(){

    }

    public void rateProduct(){

    }

    public void viewBalance(){

    }

    public void viewDiscountCodes(){

    }



}
