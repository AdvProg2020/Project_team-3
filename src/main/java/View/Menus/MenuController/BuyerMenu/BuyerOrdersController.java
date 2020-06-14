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

public class BuyerOrdersController {







    public void goToUserZone(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void goToCart(ActionEvent actionEvent) {
        String path=SceneSwitcher.getInstance().getFXMLPath("CartMenu");
        Stage stage=new Stage();
        stage.setHeight(427);
        stage.setWidth(620);
        URL urls = null;
        try {
            urls = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(urls);
            stage.setScene(new Scene(parent));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public void goToShopMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void goToDiscounts(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }

    public void goToDiscountCodes(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerDiscountCodes");
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
