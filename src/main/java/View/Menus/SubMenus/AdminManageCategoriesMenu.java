package View.Menus.SubMenus;

import View.Menus.UserMenu;

public class AdminManageCategoriesMenu extends UserMenu {
    private static AdminManageCategoriesMenu adminManageCategoriesMenu;
    private int optionCount = 6;
    private AdminManageCategoriesMenu(){ }

    public static AdminManageCategoriesMenu getInstance(){
        if(adminManageCategoriesMenu==null)
            adminManageCategoriesMenu = new AdminManageCategoriesMenu();
        return adminManageCategoriesMenu;
    }

    public void run(){

    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
