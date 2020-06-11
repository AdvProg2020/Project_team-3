package View.Menus.MenuController;

import Controller.SceneSwitcher;
import Controller.UserController;
import Model.Users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;

public class MainMenuController {


    public Menu menuBar;

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
        SceneSwitcher.getInstance().setSceneTo("Login");
    }

    public void userzone(ActionEvent actionEvent) {
        System.out.println("owowkspwkps");
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        SceneSwitcher.getInstance().setSceneTo("AdminMenu");
    }
}
