import java.util.HashMap;

public class BuyerMenu extends Menu{
    private static BuyerMenu buyerMenu;
    private int optionCount = 4;
    private BuyerMenu(){ }

    public static BuyerMenu getInstance(){
        if(buyerMenu==null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
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

    public void editPersonalInfo( ){

    }

    public void shop(String command){
        //set current menu to shop
    }

    public void previousPurchases(){
        //set current menu to buyLog
    }



}
