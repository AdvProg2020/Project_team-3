package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;

public class SellerEditOff {
    private static String offID;

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public static void setOffID(String newID) {
        offID = newID;
    }

    public static String getOffID() {
        return offID;
    }
}
