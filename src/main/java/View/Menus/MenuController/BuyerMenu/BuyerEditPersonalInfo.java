package View.Menus.MenuController.BuyerMenu;

import Controller.Controller;
import Controller.UserController;
import Model.Users.User;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class BuyerEditPersonalInfo {


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
        firstnameTextField.setText(user.getName());
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
            return;
        }
        User user=Controller.getInstance().getCurrentOnlineUser();
        UserController.getInstance().editPersonalInfo(user.getUsername(),"Name",firstnameTextField.getText());
        showAlertBox();
    }

    public void changeSurname(ActionEvent actionEvent) {
        if(firstnameTextField.getText().equals("")){
            surnameLabel.setText("you must fill the blank!");
            surnameLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        User user=Controller.getInstance().getCurrentOnlineUser();
        UserController.getInstance().editPersonalInfo(user.getUsername(),"Surname",surnameTextField.getText());
        showAlertBox();
    }

    public void changePhoneNumber(ActionEvent actionEvent) {
        if(numberTextField.getText().equals("")){
            numberLabel.setText("you must fill the blank!");
            numberLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        if(UserController.getInstance().isValidPhoneNumber(numberTextField.getText())==false){
            numberLabel.setText("invalid input!");
            numberLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        User user=Controller.getInstance().getCurrentOnlineUser();
        UserController.getInstance().editPersonalInfo(user.getUsername(),"Number",numberTextField.getText());
        showAlertBox();
    }

    public void changeEmail(ActionEvent actionEvent) {
        if(emailTextField.getText().equals("")){
            emailLabel.setText("you must fill the blank!");
            emailLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        if(UserController.getInstance().isValidEmail(emailTextField.getText())==false){
            emailLabel.setText("invalid input!");
            emailLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        User user=Controller.getInstance().getCurrentOnlineUser();
        UserController.getInstance().editPersonalInfo(user.getUsername(),"Email",emailTextField.getText());
        showAlertBox();
    }

    public void changePassword(ActionEvent actionEvent) {
        if(passwordTextField.getText().equals("")){
            passwordLabel.setText("you must fill the blank!");
            passwordLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        User user=Controller.getInstance().getCurrentOnlineUser();
        UserController.getInstance().editPersonalInfo(user.getUsername(),"Password",passwordTextField.getText());
        showAlertBox();
    }

    public void changeImage(ActionEvent actionEvent) {
        User user=Controller.getInstance().getCurrentOnlineUser();
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image","*.png"),
                new FileChooser.ExtensionFilter("image","*.jpg")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        if(selected==null) return;
        File removed=new File(UserController.getInstance().userImagePath(user.getUsername()));
        if(!removed.getPath().equals("src/main/resources/Images/default.jpg")) removed.delete();
        Path source= Paths.get(selected.getPath());
        String ext=selected.getName().substring(selected.getName().lastIndexOf("."));
        String fullPath="src/main/resources/Images/"+user.getUsername()+ext;
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path=UserController.getInstance().userImagePath(user.getUsername());
        File file=new File(path);
        try {
            userImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void showAlertBox(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("the field changed successfully!");
        alert.show();
    }


    public void removeImage(ActionEvent actionEvent) {
        User user=Controller.getInstance().getCurrentOnlineUser();
        String path=UserController.getInstance().userImagePath(user.getUsername());
        File file=new File(path);
        file.delete();
        File defaultImage=new File("src/main/resources/Images/default.jpg");
        try {
            userImage.setImage(new Image(String.valueOf(defaultImage.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
