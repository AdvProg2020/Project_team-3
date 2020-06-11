package View.Menus.MenuController.BuyerMenu;

import Controller.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BuyerMenuController {
    @FXML
    private void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void viewOrders(){
        SceneSwitcher.getInstance().setSceneTo("BuyerOrders");
    }

    @FXML
    private void viewDiscountCodes(){
        SceneSwitcher.getInstance().setSceneTo("BuyerDiscountCodes");
    }

    @FXML
    private void viewShop(){
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }
    @FXML
    private void viewDiscounts(){
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }
    @FXML
    private void viewCart(){
        SceneSwitcher.getInstance().setSceneTo("CartMenu");
    }
}
