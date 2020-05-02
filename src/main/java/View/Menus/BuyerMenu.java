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
        System.out.println(View.ANSI_BLUE+"You are in the Buyer menu."+View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.equals("view personal info")){
            View.setCurrentMenu(ViewPersonalInfo.getInstance());
        }
        else if(command.equals("view cart")){
            viewCart();
        }
        else if(command.equals("purchase")){
            purchase();
        }
        else if(command.equals("view orders")){
            viewOrders();
        }
        else if(command.equals("view balance")){
            viewBalance();
        }
        else if(command.equals("view discount codes")){
            viewDiscountCodes();
        }
        else if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
        }
        else if(command.equals("help")){
            help();
        }
        else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
        }
    }



    @Override
    public void help(){
        System.out.println(View.ANSI_BLUE+"You are in the Buyer menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info");
        System.out.println("view cart");
        System.out.println("purchase");
        System.out.println("view orders");
        System.out.println("view balance");
        System.out.println("view discount codes");
        System.out.println("back");
    }


    private void viewCart(){

    }

    private void purchase(){

    }

    private void viewOrders(){

    }

    private void viewBalance(){
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE+UserController.getInstance().currentOnlineUserBalance()+View.ANSI_RESET);
        View.setCurrentMenu(BuyerMenu.getInstance());
    }

    private void viewDiscountCodes(){

    }



}
