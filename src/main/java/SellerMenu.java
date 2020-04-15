import java.util.HashMap;

public class SellerMenu extends UserMenu{
    private static SellerMenu sellerMenu;
    private int optionCount = 6;
    private SellerMenu(){ }

    public static SellerMenu getInstance(){
        if(sellerMenu==null)
            sellerMenu = new SellerMenu();
        return sellerMenu;
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
