package View.Menus;

import Control.UserController;

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
    public void run(){

    }

    @Override
    public void execute(String command) {

    }

 /*   @Override
    public void show(){
        help();
        String command = View.read.nextLine();
        execute(command);
    }


    @Override
    public void execute(String command){
        if(command.equals("1")){
            viewPersonalInfo();
        }else if(command.equals("2")){
            viewCart();
        }else if(command.equals("3")){
            purchase();
        }else if(command.equals("4")){
            previousPurchases();
        }else if(command.equals("5")){
            viewBalance();
        }else if(command.equals("6")){
            viewDiscountCodes();
        }else if(command.equals("7")){
            MainMenu.getInstance().show();
        }else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    } */

    @Override
    public void help(){
        System.out.println(View.ANSI_BLUE+"You are in the Buyer menu."+View.ANSI_RESET);
        System.out.println("1- View personal info\n2- View Cart\n3- Purchase \n4- View orders\n5- View balance\n6- View discount codes\n7- Back");

    }


    private void viewCart(){

    }

    private void showProducts(){

    }

    private void viewProduct(){

    }

    private void increaseProduct(){

    }

    private void decreaseProduct(){

    }

    private void showTotalPrice(){

    }

    private void purchase(){

    }

    private void receiverInfo(){

    }

    private void enterDiscountCode(){

    }

    private void paymentMethod(){

    }

    private void shop(){
        //set current menu to shop
    }

    private void previousPurchases(){
        //set current menu to buyLog
    }

    private void showOrder(){

    }

    private void rateProduct(){

    }

    private void viewBalance(){
        System.out.println(UserController.getInstance().currentOnlineUserBalance());
       View.setCurrentMenu(BuyerMenu.getInstance());
    }

    private void viewDiscountCodes(){

    }



}
