package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;

public class SellerEditOff {
    private String offID;

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void setOffID(String offID) {
        this.offID = offID;
    }

    public String getOffID() {
        return offID;
    }
}
