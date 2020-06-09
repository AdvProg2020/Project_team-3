package Controller.MenuController.LoginRegisterController;

import Controller.SceneSwitcher;
import Controller.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public class BuyerRegisterController {
    public ComboBox roleChooser;
    public PasswordField passwordTextField;
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


    public void initialize(){


    }


    public void comboBoxPressed(MouseEvent mouseEvent) {
        roleChooser.getSelectionModel().select("Buyer");
    }


    public void fileChooserOpen(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image","*.png"),
                new FileChooser.ExtensionFilter("image","*.jpg")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        imageDirectory.setText(selected.getPath());
        Path source= Paths.get(selected.getPath());
        String ext=selected.getName().substring(selected.getName().lastIndexOf("."));
        String fullPath="src/main/resources/Images/"+usernameTextField.getText()+ext;
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent actionEvent) {
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
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in Register process!");
            alert.setContentText("you must correct your mistakes for successful register!");
            alert.show();
            validateTextFieldsAfterError(validation);
            return;
        }
        double money=UserController.getInstance().validateMoney(moneyTextField.getText());
        UserController.getInstance().registerBuyer(money,usernameTextField.getText(),passwordTextField.getText(),firstNameTextField.getText(),surnameTextField.getText(),emailTextField.getText(),numberTextField.getText());
        emptyAllText();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("successful register!");
        alert.setContentText("your account is ready for use!");
        alert.show();
    }

    private boolean validUsername(String username){
        if(username.equals("")){
            usernameLabel.setText("you must fill the blank!");
            usernameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        if(UserController.getInstance().isThereUserWithUsername(username)==true){
            usernameLabel.setText("there is a user exist with this username!");
            usernameLabel.setTextFill(Color.rgb(255,0,0));
            return false;
        }
        return true;
    }

    private boolean validPassword(String password){
        if(password.equals("")){
            passwordLabel.setText("you must fill the blank!");
            firstnameLabel.setTextFill(Color.rgb(255,0,0));
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
        passwordLabel.setText("");
        firstNameTextField.setText("");
        surnameTextField.setText("");
        numberTextField.setText("");
        phoneNumberLabel.setText("");
        emailTextField.setText("");
        emailLabel.setText("");
        moneyTextField.setText("");
        moneyLabel.setText("");
    }


}
