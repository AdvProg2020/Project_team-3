package View.Menus.MenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;

public class CartMenuController {





    public void goToUserZone(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void goToDiscountCodes(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerDiscountCodes");
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
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void viewEditPersonalInfo(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("EditPersonalInfo");
    }
}
