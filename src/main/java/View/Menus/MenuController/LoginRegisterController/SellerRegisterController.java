package View.Menus.MenuController.LoginRegisterController;

import Controller.SceneSwitcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SellerRegisterController {
    public ComboBox roleChooser;
    public TextField usernameTextField;
    public TextField surnameTextField;
    public TextField firstNameTextField;
    public TextField emailTextField;
    public TextField phoneNumberTextField;
    public TextField companyTextField;
    public PasswordField passwordTextField;
    public TextField imageDirectory;


    public void initialize(){

        roleChooser.getItems().add("Admin");
        roleChooser.getItems().add("Seller");
        roleChooser.getItems().add("Buyer");
        roleChooser.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String value=(String) roleChooser.getValue();
                if(value.equals("Buyer")) {
                    SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
                }
                else if(value.equals("Admin")){
                    SceneSwitcher.getInstance().setSceneTo("AdminRegister");
                }
            }
        });
    }


    public void comboBoxPressed(MouseEvent mouseEvent) {
        roleChooser.getSelectionModel().select("Seller");
    }
}
