package View.Menus;

import Control.Controller;
import Model.Users.Seller;

import java.util.regex.Matcher;

public class SellerMenu extends UserMenu {
    private static SellerMenu sellerMenu;
    private int optionCount = 6;
    private SellerMenu(){ }

    public static SellerMenu getInstance(){
        if(sellerMenu==null)
            sellerMenu = new SellerMenu();
        return sellerMenu;
    }

    @Override
    public void run(){
        System.out.println(View.ANSI_BLUE+"You are in the Seller menu."+View.ANSI_RESET);
        String command = View.read.nextLine();
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
        }
        if(command.startsWith("view ")){
            matcher=View.getMatcher("view (\\S+)",command);
            if(matcher.matches()){
                viewItem(matcher.group(1));
            }
        }
        if(command.startsWith("view buyers ")){
            viewBuyers(command);
        }
        if(command.startsWith("edit product ")){
            editProduct();
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
        }
        if(command.startsWith("view off ")){
            viewOff(command);
        }
        if(command.startsWith("edit off ")){
            editSale();
        }
        if(command.equals("add off")){
            addSale();
        }
        if(command.equals("view balance")){
            viewBalance();
        }
        if(command.equals("logout")){
            logout();
        }
        if(command.equals("offs")){
            View.previousMenu = SellerMenu.getInstance();
            View.setCurrentMenu(DiscountsMenu.getInstance());
        }
        else if(command.equals("products")){
            View.previousMenu = SellerMenu.getInstance();
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
        System.out.println("view [productId]");
        System.out.println("view buyers [productId]");
        System.out.println("edit product [productId]");
        System.out.println("add product");
        System.out.println("remove product [productId]");
        System.out.println("show categories");
        System.out.println("view offs");
        System.out.println("view off [offId]");
        System.out.println("edit off [offId]");
        System.out.println("add off");
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

    public void viewBuyers(String command){

    }

    public void editProduct(){

    }

    public void addItem(){
        String Name=readName("Please enter item Name");
        String company=readName("Please enter your Brand name");
        String description=readName("Please enter your item description");
        double price=readDoubleNumber("please enter item price");
        int inStock=readNumber(-1,"how many of this item do you wish to sell?");
    }

    public void removeProduct(){

    }

    public void showCategories(){

    }

    public void viewOff(String command){

    }

    private void viewAllOffs(){

    }

    public void editSale(){

    }

    public void addSale(){

    }

    public void viewBalance(){

    }

}
