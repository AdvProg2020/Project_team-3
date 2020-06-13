package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SellerManageOffs {

    @FXML
    ListView listView;


    @FXML
    private void initialize(){
        //inja miam listview ro update mikonim
        //click rooye yeki az ona mibare menuye edit sale

    }

    @FXML
    private void startSale(){
        SceneSwitcher.getInstance().setSceneTo("SellerAddOff");
    }

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
}
