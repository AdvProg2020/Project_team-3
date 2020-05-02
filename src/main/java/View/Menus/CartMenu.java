package View.Menus;

import Control.Controller;

import java.util.ArrayList;

public class CartMenu extends Menu {


    @Override
    public void run(){

    }

    @Override
    public void execute(String command) {

    }

   /* @Override
    public void show(){
        help();
        String command = View.read.nextLine();
        execute(command);
    } */

    @Override
    public void help(){
        System.out.println("1-show Cart\n2-back");
    }

  /*  @Override
    public void execute(String command){
        if(command.equals("1")){
             showCart();
        }else if(command.equals("2")){
            BuyerMenu.getInstance().show();
        }else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    } */

    public void showCart(){
        ArrayList<String> shoppingCartItems= Controller.getInstance().getCurrentShoppingCart().showCart();
        printList(shoppingCartItems);
        System.out.println("please select the item you wish to edit");
        int index=readNumber(shoppingCartItems.size(),"");
    }



}
