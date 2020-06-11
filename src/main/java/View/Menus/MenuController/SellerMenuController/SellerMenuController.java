package View.Menus.MenuController.SellerMenuController;

import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.fxml.FXML;

public class SellerMenuController {

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void manageProducts(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageProductsMenu");
    }
    @FXML
    private void addProduct(){
        SceneSwitcher.getInstance().setSceneTo("SellerAddProductMenu");
    }
    @FXML
    private void viewSalesHistory(){
        SceneSwitcher.getInstance().setSceneTo("SellerSalesHistory");
    }
    @FXML
    private void viewShop(){
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }
    @FXML
    private void viewDiscounts(){
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }
}
