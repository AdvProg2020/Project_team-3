package View.Menus.MenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;

public class ShopMenuController {



    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }
}
