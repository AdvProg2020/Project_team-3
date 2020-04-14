import java.util.HashMap;

public class SellerMenu extends Menu{
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
    public String toString(){
        return "";
    }

    @Override
    public void execute(String command){

    }

    @Override
    public void help(){

    }

    public void editPersonalInfo(){

    }

    public void showMyItems(){
    //set current menu to seller item menu
    }

    public void addItem(){

    }

    public void showAllMySale(){

    }

    public void addSale(){

    }

}
