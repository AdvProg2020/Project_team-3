package View.Menus.MenuController.SellerMenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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

    public void updatePercent(KeyEvent keyEvent) {
        String text=offPercentage.getText();
        if(text.isEmpty())  return;
        try {
            int per = Integer.parseInt(text);
            if ((per > 100) || (per <= 0)) {
                offPercentage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
            } else{
                offPercentage.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            }
        }catch (Exception e){
            offPercentage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        }
    }


    @FXML
    private void createSale(){

    }

}
