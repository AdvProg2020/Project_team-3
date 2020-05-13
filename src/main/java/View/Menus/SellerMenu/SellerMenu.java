package View.Menus.SellerMenu;

import Controller.Controller;
import Controller.ItemAndCategoryController;
import Model.Users.Seller;
import View.Menus.DiscountsMenu;
import View.Menus.MainMenu;
import View.Menus.ShopMenu.ShopMenu;
import View.Menus.UserMenu;
import View.Menus.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class SellerMenu extends UserMenu {
    private static SellerMenu sellerMenu;
    private SellerMenu(){ }

    public static SellerMenu getInstance(){
        if(sellerMenu==null)
            sellerMenu = new SellerMenu();
        return sellerMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE+"You are in the Seller menu."+View.ANSI_RESET);
        String command = View.getRead().nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        Matcher matcher;
        if(command.equals("view personal info")){
            viewPersonalInfo();
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
        }
        if(command.equals("view company information")){
            viewCompany();
        }
        if(command.equals("view sales history")){
            viewSalesHistory();
        }
        if(command.equals("manage products")){
            manageProducts();
            View.setCurrentMenu(SellerManageProductsMenu.getInstance());
        }
        if(command.equals("add product")){
            addItem();
        }
        if(command.equals("remove product")){
            removeProduct();
        }
        if(command.equals("show categories")){
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        if(command.equals("view offs")){
            viewAllOffs();
            View.setCurrentMenu(SellerManageOffsMenu.getInstance());
        }
        if(command.equals("view balance")){
            viewBalance();
        }
        if(command.equals("logout")){
            logout();
        }
        if(command.equals("offs")){
            View.setPreviousMenu(SellerMenu.getInstance());
            View.setCurrentMenu(DiscountsMenu.getInstance());
        }
        else if(command.equals("products")){
            View.setPreviousMenu(SellerMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
        }
        else if(command.equals("help")){
            help();
        }
        else if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
        }
    }

    @Override
    public void help(){
        System.out.println(View.ANSI_BLUE+"You are in the Seller menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info");
        System.out.println("edit [field]");
        System.out.println("view company information");
        System.out.println("view sales history");
        System.out.println("manage products");
        //System.out.println("view [productId]");
        //System.out.println("view buyers [productId]");
        //System.out.println("edit product [productId]");
        System.out.println("add product");
        System.out.println("remove product [productId]");
        System.out.println("show categories");
        System.out.println("view offs");
        //System.out.println("view off [offId]");
        //System.out.println("edit off [offId]");
        //System.out.println("add off");
        System.out.println("view balance");
    }

    public void viewCompany(){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        System.out.println("Your current company is:"+seller.getCompanyName());
    }

    public void viewSalesHistory(){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        System.out.println(seller.getSaleLogsString());
    }

    public void manageProducts(){
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        System.out.println(seller.getAllItemsString());
    }

    public void addItem(){
        String Name=readName("Please enter item Name");
        String company=readName("Please enter your Brand name");
        String description=readName("Please enter your item description");
        double price=readDoubleNumber("please enter item price");
        int inStock=readNumber(-1,"how many of this item do you wish to sell?");
        String category=readName("please enter category name");
       HashMap<String,String> attribute=new HashMap<>();
        ArrayList<String> attributeValue=new ArrayList<>();
        while(true){
            System.out.println("please enter a new attribute. (type next to continue)");
            String a=View.getRead().nextLine();
            if(a.equals("next")){
                break;
            }
            System.out.println("please enter attribute value.");
            String b=View.getRead().nextLine();
            attributeValue.add(a);
            attribute.put(a,b);
        }
        System.out.println(ItemAndCategoryController.getInstance().addItem(Name,company,description,price,inStock,category,attributeValue,attribute));
    }

    public void removeProduct(){

    }

    public void showCategories(){

    }
    private void viewAllOffs(){

    }

    public void viewBalance(){

    }

}
