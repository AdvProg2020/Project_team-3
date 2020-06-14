package View.Menus.MenuController.SellerMenuController;

import Controller.SaleAndDiscountCodeController;
import Controller.UserController;
import Model.Sale;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;

public class SellerEditOff {
    private static String offID;

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageOffs");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void initialize(){
        Sale sale = SaleAndDiscountCodeController.getInstance().getSaleById(offID);
        currentOffPercent.setText(Integer.toString(sale.getOffPercentage()));
        currentStartDate.setText(sale.getStartTime().toString());
        currentEndDate.setText(sale.getEndTime().toString());
    }

    @FXML private Text currentOffPercent;

    @FXML private Text currentStartDate;

    @FXML private Text currentEndDate;

    @FXML private TextField newOffPercent;
    @FXML private DatePicker newStartDate;
    @FXML private DatePicker newEndDate;

    @FXML
    private void editOffPercent(){

    }
    @FXML
    private void editStartDate(){

    }
    @FXML
    private void editEndDate(){

    }

    public static void setOffID(String newID) {
        offID = newID;
    }

    public static String getOffID() {
        return offID;
    }
}
