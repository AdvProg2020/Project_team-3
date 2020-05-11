package View.Menus.SubMenus;

import View.Menus.UserMenu;

public class AdminManageDiscountCodesMenu extends UserMenu {
    private static AdminManageDiscountCodesMenu adminManageDiscountCodesMenu;
    private int optionCount = 6;
    private AdminManageDiscountCodesMenu(){ }

    public static AdminManageDiscountCodesMenu getInstance(){
        if(adminManageDiscountCodesMenu==null)
            adminManageDiscountCodesMenu = new AdminManageDiscountCodesMenu();
        return adminManageDiscountCodesMenu;
    }

    public void run(){

    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
