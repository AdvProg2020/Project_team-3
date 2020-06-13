package View.Menus.MenuController.BuyerMenu;

import Controller.Controller;
import Controller.UserController;
import Model.Users.User;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.MalformedURLException;

public class PersonalInfoMenuController {


    public TextField firstnameTextField;
    public TextField surnameTextField;
    public TextField numberTextField;
    public TextField emailTextField;
    public TextField passwordTextField;
    public PasswordField passwordField;
    public ImageView userImage;
    public Label numberLabel;
    public Label emailLabel;
    public Label passwordLabel;
    public Label surnameLabel;
    public Label firstnameLabel;
    public CheckBox passwordCheckBox;


    public void initialize()  {
        passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
        User user=Controller.getInstance().getCurrentOnlineUser();
        String path=UserController.getInstance().userImagePath(user.getUsername());
        File file=new File(path);
        try {
            userImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        firstnameTextField.setText(user.getUsername());
        passwordTextField.setText(user.getPassword());
        surnameTextField.setText(user.getLastName());
        emailTextField.setText(user.getEmail());
        numberTextField.setText(user.getNumber());
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }
    @FXML
    private void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void viewOrders(){
        SceneSwitcher.getInstance().setSceneTo("BuyerOrders");
    }
    @FXML
    private void viewDiscountCodes(){
        SceneSwitcher.getInstance().setSceneTo("BuyerDiscountCodes");
    }
    @FXML
    private void viewShop(){
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }
    @FXML
    private void viewDiscounts(){
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }
    @FXML
    private void viewCart(){
        SceneSwitcher.getInstance().setSceneTo("CartMenu");
    }


    public void changeFirstName(ActionEvent actionEvent) {
        if(firstnameTextField.getText().equals("")){
            firstnameLabel.setText("you must fill the blank!");
            firstnameLabel.setTextFill(Color.rgb(255,0,0));
        }

    }

    public void changeSurname(ActionEvent actionEvent) {
    }

    public void changePhoneNumber(ActionEvent actionEvent) {
    }

    public void changeEmail(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void changeImage(ActionEvent actionEvent) {
    }

}
