package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SellerAddOff {


    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField offPercentage;
    @FXML private ListView allItems;
    @FXML private ListView saleItems;



}
