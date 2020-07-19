package Project.Client.Menus.MenuController;

/// for buyer and seller!!!!

import Project.Client.MakeRequest;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Users.Buyer;
import Project.Client.Model.Users.Seller;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.regex.Pattern;

public class TransactionMenu {
    public ComboBox transactionComboBox;
    public TextField bankAccountId;
    public TextField amount;
    public TextField bankBalance;
    public TextField userBalance;
    public TextField finalAmount;
    public TextField wageAmount;

    public void initialize(){
        setBankAccountBalance();
        setUserBalance();
        transactionComboBox.getItems().add("deposit");
        transactionComboBox.getItems().add("withdraw");
    }

    public void setBankAccountBalance(){
        String bBalance= MakeRequest.getBankAccountBalance();
        if(bBalance.equals("token expired") || bBalance.equals("token is invalid")){
            tokenError();
            bBalance= MakeRequest.getBankAccountBalance();
        }
        bankBalance.setText(bBalance);
    }

    public void setUserBalance(){
        String type=MakeRequest.makeGetUserRequest().type;
        if(type.equalsIgnoreCase("Buyer")){
            Buyer buyer=(Buyer) MakeRequest.makeGetUserRequest();
            userBalance.setText(String.valueOf(buyer.getMoney()));
        }
        else if(type.equalsIgnoreCase("seller")){
            Seller seller=(Seller) MakeRequest.makeGetUserRequest();
            userBalance.setText(String.valueOf(seller.getMoney()));
        }
    }

    public void tokenError(){
        SceneSwitcher.getInstance().setSceneAndWait("bankLogin" ,600 , 526);
    }

    public void pay(ActionEvent actionEvent) {
    }

    public void amountTyped(KeyEvent keyEvent) {
        String allChars=amount.getText() + keyEvent.getText();
        if(allChars.matches("\\d+")){
            amount.setStyle("-fx-text-fill: green");
        }
        else {
            amount.setStyle("-fx-text-fill: #a30000");
        }
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }
}
