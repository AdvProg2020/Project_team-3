package View.Menus.SubMenus;

import View.Menus.UserMenu;

public class AdminManageRequestsMenu extends UserMenu {
    private static AdminManageRequestsMenu adminManageRequestsMenu;
    private int optionCount = 6;
    private AdminManageRequestsMenu(){ }

    public static AdminManageRequestsMenu getInstance(){
        if(adminManageRequestsMenu==null)
            adminManageRequestsMenu = new AdminManageRequestsMenu();
        return adminManageRequestsMenu;
    }

    public void run(){

    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
