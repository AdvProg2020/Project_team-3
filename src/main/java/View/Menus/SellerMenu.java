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
        System.out.println(View.ANSI_BLUE+"You are in the Seller menu."+View.ANSI_RESET);
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.equals("logout")){
            logout();
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
        System.out.println("edit [productId]");
        System.out.println("add product");
        System.out.println("remove product [productId]");
        System.out.println("show categories");
        System.out.println("view offs");
        System.out.println("view [offId]");
        System.out.println("edit [offId]");
        System.out.println("add off");
        System.out.println("view balance");
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
