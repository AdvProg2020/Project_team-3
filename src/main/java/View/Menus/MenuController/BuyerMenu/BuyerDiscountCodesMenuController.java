package View.Menus.MenuController.BuyerMenu;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BuyerDiscountCodesMenuController {



    public void goToCart(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyerMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);
    }

    public void goToShopMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void goToDiscounts(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }

    public void goToBuyerOrders(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerOrders");
    }

    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void ViewEditPersonalInfo(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("EditPersonalInfo");
    }
}
