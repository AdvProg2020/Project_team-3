package View.Menus;

import Control.Controller;
import Control.UserController;

public abstract class UserMenu extends Menu {

    public void viewPersonalInfo(){
        System.out.println(UserController.getInstance().viewPersonalInfo(Controller.getInstance().getCurrentOnlineUser().getUsername()));
        ViewPersonalInfo.getInstance().show();
    }

    public void editPersonalInfo(){

    }

    public void editName(){

    }

    public void editLastName(){

    }

    public void editEmail(){

    }

    public void editNumber(){

    }
}
