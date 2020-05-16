package View.Menus.SellerMenu;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import View.Menus.LoginRegisterMenu;
import View.Menus.MainMenu;
import View.Menus.ShopAndDiscountMenu.DiscountsMenu;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
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
            return;
        }
        matcher = View.getMatcher("edit (\\S+)", command);
        if (matcher.matches()) {
            editPersonalInfo(matcher.group(1));
            return;
        }
        if(command.equals("view company information")){
            viewCompany();
            return;
        }
        if(command.equals("view sales history")){
            viewSalesHistory();
            return;
        }
        if(command.equals("manage products")){
            manageProducts();
            View.setCurrentMenu(SellerManageProductsMenu.getInstance());
            return;
        }
        if(command.equals("add product")){
            addItem();
            return;
        }
        if(command.equals("show categories")){
            View.setCurrentMenu(ShopMenu.getInstance());
            return;
        }
        if(command.equals("view offs")){
            SellerManageOffsMenu.getInstance().viewAllOffs();
            View.setCurrentMenu(SellerManageOffsMenu.getInstance());
            return;
        }
        if(command.equals("view balance")){
            viewBalance();
            return;
        }
        if(command.equals("logout")){
            LoginRegisterMenu.getInstance().setPreviousMenu(MainMenu.getInstance());
            LoginRegisterMenu.getInstance().logout();
            return;
        }
        if(command.equals("offs")){
            DiscountsMenu.getInstance().setPreviousMenu(SellerMenu.getInstance());
            View.setCurrentMenu(DiscountsMenu.getInstance());
            return;
        }
        else if(command.equals("products")){
            ShopMenu.getInstance().setPreviousMenu(SellerMenu.getInstance());
            View.setCurrentMenu(ShopMenu.getInstance());
            return;
        }
        else if(command.equals("help")){
            help();
            return;
        }
        else if(command.equals("back")){
            View.setCurrentMenu(MainMenu.getInstance());
            return;
        }

        System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
    }

    @Override
    public void help(){
        System.out.println(View.ANSI_BLUE+"You are in the Seller menu.\nType your command in one of these formats:"+View.ANSI_RESET);
        System.out.println("view personal info");
        System.out.println("edit [field]");
        System.out.println("view company information");
        System.out.println("view sales history");
        System.out.println("manage products");
        System.out.println("add product");
        System.out.println("show categories");
        System.out.println("view offs");
        System.out.println("view balance");
    }

    public void viewCompany(){
        System.out.println("Your current company is:"+UserController.getInstance().getSellerCompany());
    }

    public void viewSalesHistory(){
        System.out.println(UserController.getInstance().getSaleHistory());
    }

    public void manageProducts(){
        System.out.println(UserController.getInstance().getSellerItems());
    }

    public void addItem(){
        String Name=readName("Please enter item Name");
        String company=readName("Please enter your Brand name");
        String description=readName("Please enter your item description");
        double price=readDoubleNumber("please enter item price");
        View.getRead().nextLine();
        int inStock=readNumber(-1,"how many of this item do you wish to sell?");
        String category=readName("please enter category name");
        if(ItemAndCategoryController.getInstance().isThereCategoryWithName(category)==false){
            System.out.println("Error: invalid category Name");
            return;
        }
        HashMap<String,String> attributeValue=new HashMap<>();
        ArrayList<String> attributeKey=ItemAndCategoryController.getInstance().getCategoryByName(category).getAttributes();
        System.out.println("please enter the attribute value(enter exit to quit the process)");
        if(attributeKey!=null) {
            for (String key : attributeKey) {
                System.out.println(key + ":");
                String value = View.getRead().nextLine();
                if (value.equals("exit")) {
                    return;
                }
                attributeValue.put(key, value);
            }
        }
        System.out.println(ItemAndCategoryController.getInstance().addItem(Name,company,description,price,inStock,category,attributeValue));
    }

    public void viewBalance(){
        System.out.print("Your current balance is:");
        System.out.println(View.ANSI_BLUE + UserController.getInstance().currentOnlineUserBalance() + View.ANSI_RESET);
    }

}
