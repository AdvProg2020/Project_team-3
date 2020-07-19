package Project.Client.Menus.MenuController.LoginRegisterController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class bankLogin {

    public TextField usernameTextField;
    public TextField passwordTextField;

    public void loginInBank(ActionEvent actionEvent) {
        String result= MakeRequest.getBankTokenForClient(usernameTextField.getText(),passwordTextField.getText());
        if(result.equals("invalid username or password")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in Login process!");
            alert.setContentText(result);
            return;
        }
        else{
            Client.getInstance().setBankAccountToken(result);
        }
    }

    public void goToRegisterBank(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeSecondStage();
        SceneSwitcher.getInstance().setSceneTo("bankRegisterAccount");
    }
}
