public class BuyerMenu {
    private static BuyerMenu buyerMenu;
    private BuyerMenu(){ }

    public static BuyerMenu getInstance(){
        if(buyerMenu==null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
    }
    public void show(){
        System.out.println("1-Edit personal information");
        System.out.println("2-shop");
        System.out.println("3-previous purchases");
        System.out.println("4-Logout");
    }

    @Override
    public String toString(){
        return "1-Edit personal information\n2-shop\n3-previous purchases\n4-Logout";
    }
    public void execute(){

    }
}
