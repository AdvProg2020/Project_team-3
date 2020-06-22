package View.Menus.MenuController.LoginRegisterController;

import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SellerRegisterController {
    public TextField usernameTextField;
    public TextField surnameTextField;
    public TextField firstNameTextField;
    public TextField emailTextField;
    public TextField phoneNumberTextField;
    public TextField companyTextField;
    public PasswordField passwordField;
    public TextField imageDirectory;
    public TextField passwordTextField;
    public TextField moneyTextField;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label firstnameLabel;
    public Label surnameLabel;
    public Label emailLabel;
    public Label phoneNumberLabel;
    public Label companyNameLabel;
    public Label moneyLabel;
    public CheckBox passwordCheckBox;

    public void initialize(){
        passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
        passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    public void openFileChooser(ActionEvent actionEvent) {
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
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Register(ActionEvent actionEvent) {
        Boolean [] validation=new Boolean[8];
        Boolean canRegister=true;
        validation[0]=validUsername(usernameTextField.getText());
        validation[1]=validPassword(passwordTextField.getText());
        validation[2]=validFirstName(firstNameTextField.getText());
        validation[3]=validSurname(surnameTextField.getText());
        validation[4]=validEmail(emailTextField.getText());
        validation[5]=validPhoneNumber(phoneNumberTextField.getText());
        validation[6]=validMoney(moneyTextField.getText());
        validation[7]=validCompanyName(companyTextField.getText());
        for(Boolean isValid:validation){
            if(isValid==false){
                canRegister=false;
            }
        }
        if(canRegister==false){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in Register process!");
            alert.setContentText("please fill all the fields correctly");
            alert.show();
            validateTextFieldsAfterError(validation);
            validateLabelsAfterError(validation);
            return;
        }
        double money=UserController.getInstance().validateMoney(moneyTextField.getText());
        UserController.getInstance().registerSeller(money,usernameTextField.getText(),passwordField.getText(),firstNameTextField.getText(),surnameTextField.getText(),emailTextField.getText(),phoneNumberTextField.getText(),companyTextField.getText());
        emptyAllText();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("successful!");
        alert.setContentText("your request has been sent to the admin");
        alert.show();
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
        if(UserController.getInstance().isThereUserWithUsername(username)==true){
            usernameLabel.setText("a username already exist with this username");
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

    private boolean validPhoneNumber(String phoneNumber){
        if(phoneNumber.equals("")){
            phoneNumberLabel.setText("you must fill the blank!");
            phoneNumberLabel.setTextFill(Color.rgb(255,0,0));
        }
        if(UserController.getInstance().isValidPhoneNumber(phoneNumber)==false){
            phoneNumberLabel.setText("invalid phone number!");
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
        if(UserController.getInstance().isValidEmail(email)==false){
            emailLabel.setText("invalid email!");
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
        if(UserController.getInstance().validateMoney(money)==-1){
            moneyLabel.setText("invalid input!");
            moneyLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validCompanyName(String companyName){
        if(companyName.equals("")){
            companyNameLabel.setText("you must fill the blank!");
            companyNameLabel.setTextFill(Color.rgb(255,0,0));
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
            phoneNumberTextField.setText("");
        }
        if(validations[6]==false){
            moneyTextField.setText("");
        }
        if(validations[7]==false){
            companyTextField.setText("");
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
        phoneNumberTextField.setText("");
        phoneNumberLabel.setText("");
        emailTextField.setText("");
        emailLabel.setText("");
        moneyTextField.setText("");
        moneyLabel.setText("");
        imageDirectory.setText("");
        companyTextField.setText("");
        companyNameLabel.setText("");
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }

    public void mainMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void login(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneAndWait("Login");
    }

    public void buyerRegister(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("SellerRegister");
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }

    public void goToBuyerRegisterMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("SellerRegister");
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
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
        if(validations[7]==true){
            companyNameLabel.setText("");
        }
    }

}
