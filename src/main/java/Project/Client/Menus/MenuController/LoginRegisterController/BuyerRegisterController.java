package Project.Client.Menus.MenuController.LoginRegisterController;

import Project.Client.Client;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.MakeRequest;
import Project.Client.CLI.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BuyerRegisterController {
    public String desPath;
    public String srcPath;
    public PasswordField passwordField;
    public TextField surnameTextField;
    public TextField moneyTextField;
    public TextField emailTextField;
    public TextField numberTextField;
    public CheckBox passwordCheckBox;
    public TextField usernameTextField;
    public TextField imageDirectory;
    public Label firstnameLabel;
    public Label phoneNumberLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label moneyLabel;
    public Label emailLabel;
    public Label surnameLabel;
    public TextField firstNameTextField;
    public TextField passwordTextField;
    @FXML private AnchorPane pane;
    public void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("second.wav");
        passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    public void fileChooserOpen(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("JPG","*.jpg")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        if(selected==null) return;
        Path source= Paths.get(selected.getPath());
        String ext=selected.getName().substring(selected.getName().lastIndexOf("."));
        if(validUsername(usernameTextField.getText())==false) return;
        imageDirectory.setText(selected.getPath());
        String fullPath="src/main/resources/Images/"+usernameTextField.getText()+ext;
        srcPath=selected.getPath();
        desPath=fullPath;
    }

    public void register(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        Boolean [] validation=new Boolean[7];
        Boolean canRegister=true;
        validation[0]=validUsername(usernameTextField.getText());
        validation[1]=validPassword(passwordTextField.getText());
        validation[2]=validFirstName(firstNameTextField.getText());
        validation[3]=validSurname(surnameTextField.getText());
        validation[4]=validEmail(emailTextField.getText());
        validation[5]=validPhoneNumber(numberTextField.getText());
        validation[6]=validMoney(moneyTextField.getText());
        for(Boolean isValid:validation){
            if(isValid==false){
                canRegister=false;
            }
        }
        if(canRegister==false){
            MusicManager.getInstance().playSound("error");
            SceneSwitcher.getInstance().sendAlert(true,"please fill all the fields correctly");
            validateTextFieldsAfterError(validation);
            validateLabelsAfterError(validation);
            return;
        }
        double money=validateMoney(moneyTextField.getText());
        String text = MakeRequest.makeRegisterBuyerRequest(firstNameTextField.getText(),surnameTextField.getText(),usernameTextField.getText(),passwordTextField.getText(),emailTextField.getText(),numberTextField.getText(),money);
//        if(!imageDirectory.getText().equals(""))MakeRequest.sendImageToServer(srcPath,desPath);
        if(!imageDirectory.getText().equals(""))Client.getInstance().sendImageToServer(srcPath,desPath);
        emptyAllText();
        MusicManager.getInstance().playSound("notify");
        SceneSwitcher.getInstance().sendAlert(text.contains("rror"),text);

    }

    public double validateMoney(String money) {
        double moneyDouble = -1;
        try {
            moneyDouble = Double.parseDouble(money);
        } catch (Exception e) {
            return -1;
        }
        return moneyDouble;
    }

    private boolean validUsername(String username){
        if(username.contains(" ")){
            usernameLabel.setText("invalid username!");
            usernameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        if(username.equals("")){
            usernameLabel.setText("you must fill the blank!");
            usernameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validPassword(String password){
        if(password.equals("")){
            passwordLabel.setText("you must fill the blank!");
            passwordLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validFirstName(String firstName){
        if(firstName.equals("")){
            firstnameLabel.setText("you must fill the blank!");
            firstnameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validSurname(String surname){
        if(surname.equals("")){
            surnameLabel.setText("you must fill the blank!");
            surnameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        return getMatcher(email, "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$").matches();
    }

    public boolean isValidPhoneNumber(String number) {
        return  getMatcher(number, "\\d\\d\\d\\d\\d(\\d+)$").matches();
    }

    public static Matcher getMatcher(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    private boolean validPhoneNumber(String phoneNumber){
        if(phoneNumber.equals("")){
            phoneNumberLabel.setText("you must fill the blank!");
            phoneNumberLabel.setTextFill(Color.rgb(255,0,0));
        }
        if(isValidPhoneNumber(phoneNumber)==false){
            phoneNumberLabel.setText("invalid phone number");
            phoneNumberLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validEmail(String email){
        if(email.equals("")){
            emailLabel.setText("you must fill the blank!");
            emailLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        if(isValidEmail(email)==false){
            emailLabel.setText("invalid email");
            emailLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validMoney(String money){
        if(money.equals("")){
            moneyLabel.setText("you must fill the blank!");
            moneyLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        if(validateMoney(money)==-1){
            moneyLabel.setText("invalid input!");
            moneyLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private void validateTextFieldsAfterError(Boolean [] validations){
        if(validations[0]==false){
            usernameTextField.setText("");
        }
        if(validations[1]==false){
            passwordTextField.setText("");
        }
        if(validations[2]==false){
            firstNameTextField.setText("");
        }
        if(validations[3]==false){
            surnameTextField.setText("");
        }
        if(validations[4]==false){
            emailTextField.setText("");
        }
        if(validations[5]==false){
            numberTextField.setText("");
        }
        if(validations[6]==false){
            moneyTextField.setText("");
        }
    }

    private void emptyAllText(){
        usernameTextField.setText("");
        usernameLabel.setText("");
        passwordTextField.setText("");
        passwordField.setText("");
        passwordLabel.setText("");
        firstNameTextField.setText("");
        surnameTextField.setText("");
        numberTextField.setText("");
        phoneNumberLabel.setText("");
        emailTextField.setText("");
        emailLabel.setText("");
        moneyTextField.setText("");
        moneyLabel.setText("");
        imageDirectory.setText("");
    }

    public void back(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }

    public void mainMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerRegister");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void login(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneAndWait("Login");
    }

    public void registerSeller(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerRegister");
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void Exit(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().closeWindow();
    }

    public void goToSellerRegisterMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerRegister");
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void validateLabelsAfterError(Boolean[] validations){
        if(validations[0]==true){
            usernameLabel.setText("");
        }
        if(validations[1]==true){
            passwordLabel.setText("");
        }
        if(validations[2]==true){
            firstnameLabel.setText("");
        }
        if(validations[3]==true){
            surnameLabel.setText("");
        }
        if(validations[4]==true){
            emailLabel.setText("");
        }
        if(validations[5]==true){
            phoneNumberLabel.setText("");
        }
        if(validations[6]==true){
            moneyLabel.setText("");
        }
    }


}
