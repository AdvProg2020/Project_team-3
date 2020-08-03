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
            SceneSwitcher.getInstance().sendAlert(true,result);
            return;
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            SceneSwitcher.getInstance().sendAlert(false,"your account id in bank is: "+result+" please keep record of your bank account id!");
            String token=MakeRequest.getBankTokenForClient(username,password);
            Client.getInstance().setBankAccountToken(token);
            String receipt=MakeRequest.makeBankReceiptRequest("deposit","1000000","-1",result,"",Client.getInstance().getBankAccountToken());
            String result1=MakeRequest.payReceipt(receipt);
            SceneSwitcher.getInstance().back();
//            String type=MakeRequest.makeGetUserRequest().type;
//            if(type.equalsIgnoreCase("buyer")) SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
//            else if(type.equalsIgnoreCase("seller")) SceneSwitcher.getInstance().setSceneTo("SellerMenu");

        }
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }
}
