package View.Menus.MenuController.LoginRegisterController;

import Controller.*;
import Model.Users.Buyer;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import Model.Users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginMenuController {


    public TextField usernameTextField;
    public TextField passwordTextField;
    public CheckBox passwordCheckBox;
    public PasswordField passwordField;
    public Label errorLabel;
    public Menu menu;
    public Pane pane;


    public void initialize(){
        MusicManager.getInstance().setSongName("second.wav");
        passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        usernameTextField.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 14));
        passwordTextField.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 14));
        errorLabel.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
    }

    public void login(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
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
        String message=UserController.getInstance().login(usernameTextField.getText(),passwordTextField.getText());
        if(message.startsWith("Error")){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.showAndWait();
            return;
        }
        if(SceneSwitcher.getInstance().getLastRecentScene().equals("CartMenu")){
            if(Controller.getInstance().getCurrentOnlineUser() instanceof Buyer){
                SceneSwitcher.getInstance().setSceneTo("CartMenu");
            }
            else {
                SceneSwitcher.getInstance().setSceneTo("MainMenu");
                CartController.getInstance().getCurrentShoppingCart().empty();
            }
            return;
        }
        emptyStage();
        back();
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



    public void registerBuyer(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeSecondStage();
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeSecondStage();
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }


    private void errorAfterMistake(){
        usernameTextField.setText("");
        passwordField.setText("");
        MusicManager.getInstance().playSound("error");
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


    public void back() {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeSecondStage();
    }

    public void goToBuyerRegisterMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeSecondStage();
        SceneSwitcher.getInstance().saveScene(SceneSwitcher.getInstance().getMainSceneName());
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void goToSellerRegisterMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeSecondStage();
        SceneSwitcher.getInstance().saveScene(SceneSwitcher.getInstance().getMainSceneName());
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }
}
