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
    public void login(){

    }

    @Override
    public void register(){

    }
    @Override
    public void logout(){

    }
    @Override
    public void help(){

    }

    @Override
    public int getOptionCount() {
        return optionCount;
    }
}
