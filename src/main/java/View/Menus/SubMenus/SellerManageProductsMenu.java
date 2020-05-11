package View.Menus.SubMenus;

import View.Menus.UserMenu;
import View.Menus.View;

public class SellerManageProductsMenu extends UserMenu {
    private static SellerManageProductsMenu sellerManageProductsMenu;
    private int optionCount = 6;
    private SellerManageProductsMenu(){ }

    public static SellerManageProductsMenu getInstance(){
        if(sellerManageProductsMenu==null)
            sellerManageProductsMenu = new SellerManageProductsMenu();
        return sellerManageProductsMenu;
    }

    public void run(){
        help();
        String command = View.getRead().nextLine();
        execute(command);
    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
