package View.Menus.MenuController;

import Controller.SceneSwitcher;
import Controller.UserController;
import Model.Users.User;
import javafx.event.ActionEvent;

public class MainMenuController {


    public void registerBuyer(){
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(){
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void registerAdmin(){
        SceneSwitcher.getInstance().setSceneTo("AdminRegister");
    }

    public void login(){
        //scene ro bebare rooye login menu
    }

    public void userzone(ActionEvent actionEvent) {
        System.out.println("owowkspwkps");
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        SceneSwitcher.getInstance().setSceneTo("AdminMenu");
    }
}
