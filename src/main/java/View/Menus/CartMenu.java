package View.Menus;

import Control.ItemAndCategoryController;

import java.util.regex.Matcher;

public class CartMenu extends Menu {


    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE + "You are in the cart menu." + View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
     if(command.equals("show products")){
         showProducts();
     }
     if(command.equals("show total price")){
         showTotalPrice();
     }
     if(command.equals("help")){
         help();
     }
     Matcher matcher=View.getMatcher("increase (\\S+)",command);
     if (matcher.matches()){
     String itemId=matcher.group(1);
     increase(itemId);
     }

     matcher=View.getMatcher("decrease (\\S+)",command);
     if (matcher.matches()){
         String itemId=matcher.group(1);
         decrease(itemId);
        }

    }

    @Override
    public void help(){
        System.out.println("show products");
        System.out.println("view [product id]");
        System.out.println("increase [product id]");  //done
        System.out.println("decrease [product id]");  //done
        System.out.println("show total price");
        System.out.println("purchase");
    }

    public void showProducts(){
    }

    public void increase(String itemID){
        ItemAndCategoryController.getInstance().cartIncreaseDecrease(itemID,1);
    }

    public void decrease(String itemID){
        ItemAndCategoryController.getInstance().cartIncreaseDecrease(itemID,-1);
    }

    public void  showTotalPrice(){

    }

}
