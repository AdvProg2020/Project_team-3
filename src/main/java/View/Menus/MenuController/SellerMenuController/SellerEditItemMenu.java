package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;

public class SellerEditItemMenu {
    private static String itemID;
    //
    //
    // bayad in controller ye fielde static dashte bashe , itemi ke gharare edit beshe , chizmiz haro tu initialize bar
    // asase inke on iteme attribute hash chie neshon bedim
    //
    //
    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageProductsMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public static String getItemID() {
        return itemID;
    }

    public static void setItemID(String itemID) {
        SellerEditItemMenu.itemID = itemID;
    }
}
