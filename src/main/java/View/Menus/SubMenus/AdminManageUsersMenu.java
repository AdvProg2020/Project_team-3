package View.Menus.SubMenus;

import View.Menus.UserMenu;

public class AdminManageUsersMenu extends UserMenu {
    private static AdminManageUsersMenu adminManageUsersMenu;
    private int optionCount = 6;
    private AdminManageUsersMenu(){ }

    public static AdminManageUsersMenu getInstance(){
        if(adminManageUsersMenu==null)
            adminManageUsersMenu = new AdminManageUsersMenu();
        return adminManageUsersMenu;
    }

    public void run(){

    }
    @Override
    public void execute(String command) {

    }
    public void help(){

    }
}
