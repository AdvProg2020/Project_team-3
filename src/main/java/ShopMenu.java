public class ShopMenu {
    private static ShopMenu shopMenu;
    private ShopMenu(){ }

    public static ShopMenu getInstance(){
        if(shopMenu==null)
            shopMenu = new ShopMenu();
        return shopMenu;
    }
    public void show(){
        System.out.println("1-Cart");
        System.out.println("2-Category");
        System.out.println("3-Back");
    }

    public void execute(){

    }
}
