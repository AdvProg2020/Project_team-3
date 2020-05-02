package View.Menus;

import Control.Controller;
import Control.UserController;

public abstract class UserMenu extends Menu {

    @Override
    public void run(){

    }
    @Override
    public void help(){

    }

    public void viewPersonalInfo(){
        System.out.println(UserController.getInstance().viewPersonalInfo(Controller.getInstance().getCurrentOnlineUser().getUsername()));
    }

    public void viewPersonalInfo(String username){
        System.out.println(UserController.getInstance().viewPersonalInfo(username));
    }

}
