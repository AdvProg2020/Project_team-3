package View.Menus.MenuController.LoginRegisterController;

import View.Menus.SceneSwitcher;
import Controller.UserController;
import Model.Users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LoginMenuController {


    public TextField usernameTextField;
    public TextField passwordTextField;
    public CheckBox passwordCheckBox;
    public PasswordField passwordField;
    public Label errorLabel;
    public Menu menu;
    public Pane pane;


    public void initialize(){
        passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    public void login(ActionEvent actionEvent) {
        boolean isValidUsername=validUsername(usernameTextField.getText());
        if(isValidUsername==false) {
            errorAfterMistake();
            return;
        }
        boolean isValidPassword=validPassword(usernameTextField.getText(),passwordTextField.getText());
        if(isValidPassword==false){
            errorAfterMistake();
            return;
        }
        UserController.getInstance().login(usernameTextField.getText(),passwordTextField.getText());
        emptyStage();
        SceneSwitcher.getInstance().back();
    }

    private boolean validUsername(String username){
        if(username.equals("")){
            errorLabel.setText("you must fill the blank!");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        if(UserController.getInstance().isThereUserWithUsername(username)==false){
            errorLabel.setText("invalid username or password");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validPassword(String username,String password){
        if(password.equals("")){
            errorLabel.setText("invalid username or password!");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        User user=UserController.getInstance().getUserByUsername(username);
        if(!user.getPassword().equals(password)){
            errorLabel.setText("invalid username or password");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    public void mainMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void registerBuyer(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }

    private void errorAfterMistake(){
        usernameTextField.setText("");
        passwordField.setText("");
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setContentText("invalid username or password");
        alert.show();
    }

    private void emptyStage(){
        usernameTextField.setText("");
        passwordTextField.setText("");
        errorLabel.setText("");
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }

    public void goToBuyerRegisterMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void goToSellerRegisterMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }
}
