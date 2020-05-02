package View.Menus;

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

    }

    @Override
    public void execute(String command) {

    }

   /* @Override
    public void show(){

    }

    @Override
    public void execute(String command){

    } */

    @Override
    public void help(){
    System.out.println("1-view personal information\n2-view sale Logs\n3-show my items\n4-add item");
    }

    public void viewCompany(){

    }

    public void showMyItems(){
    //set current menu to seller item menu
    }



    public void viewSalesHistory(){

    }

    public void manageProducts(){

    }

    public void viewProduct(){

    }

    public void viewBuyers(){

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

    public void viewSales(){

    }

    public void editSale(){

    }

    public void addSale(){

    }

    public void viewBalance(){

    }

}
