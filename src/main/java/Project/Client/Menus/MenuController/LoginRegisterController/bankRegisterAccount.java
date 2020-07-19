package Project.Client.Menus.MenuController.LoginRegisterController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class bankRegisterAccount {

    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField repeatPasswordTextField;
    public TextField firstNameTextField;
    public TextField lastNameField;

    public void registerInBank(ActionEvent actionEvent) {
        String username=usernameTextField.getText();
        String password=passwordTextField.getText();
        String repeatPassword=repeatPasswordTextField.getText();
        String firstName=firstNameTextField.getText();
        String lastName=lastNameField.getText();
        String result= MakeRequest.makeAccountRequestInBank(username,password,firstName,lastName,repeatPassword);
        Alert alert;
        if(result.equals("passwords do not match") || result.equals("username is not available") || result.equals("")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in register process!");
            alert.setContentText(result);
            alert.showAndWait();
            return;
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("successful");
            alert.setContentText("your account id in bank is: "+result+" please keep record of your bank account id!");
            alert.showAndWait();
            String token=MakeRequest.getBankTokenForClient(username,password);
            Client.getInstance().setBankAccountToken(token);
            SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
        }
    }
}
