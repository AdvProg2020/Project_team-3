package View.Menus.SubMenus;

import View.Menus.UserMenu;

public class SellerManageOffsMenu extends UserMenu {
    private static SellerManageOffsMenu sellerManageOffsMenu;
    private int optionCount = 6;
    private SellerManageOffsMenu(){ }

    public static SellerManageOffsMenu getInstance(){
        if(sellerManageOffsMenu==null)
            sellerManageOffsMenu = new SellerManageOffsMenu();
        return sellerManageOffsMenu;
    }

    public void run(){

    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
