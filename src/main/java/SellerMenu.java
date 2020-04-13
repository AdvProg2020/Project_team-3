public class SellerMenu {
    private static SellerMenu sellerMenu;
    private SellerMenu(){ }

    public static SellerMenu getInstance(){
        if(sellerMenu==null)
            sellerMenu = new SellerMenu();
        return sellerMenu;
    }
    public void show(){
        System.out.println("1-Edit personal information");
        System.out.println("2-Show my items");
        System.out.println("3-Add item");
        System.out.println("4-Show all my sales");
        System.out.println("5-Add sale");
        System.out.println("6-Logout");
    }

    public void execute(){

    }
}
