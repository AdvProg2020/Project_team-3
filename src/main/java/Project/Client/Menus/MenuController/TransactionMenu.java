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
    private double userMoney=0;
    private double bank=0;

    public void initialize(){
        setBankAccountBalance();
        setUserBalance();
        transactionComboBox.getItems().add("deposit"); //variz be hesab
        transactionComboBox.getItems().add("withdraw");// bardasht az hesab!
        transactionComboBox.getSelectionModel().selectFirst();
    }

    public void setBankAccountBalance(){
        String bBalance= MakeRequest.getBankAccountBalance();
        if(bBalance.equals("token expired") || bBalance.equals("token is invalid")){
            tokenError();
            bBalance= MakeRequest.getBankAccountBalance();
        }
        bankBalance.setText(bBalance);
        bank=Double.parseDouble(bankBalance.getText());
    }

    public void setUserBalance(){
        String type=MakeRequest.makeGetUserRequest().type;
        if(type.equalsIgnoreCase("Buyer")){
            Buyer buyer=(Buyer) MakeRequest.makeGetUserRequest();
            userMoney=buyer.getMoney();
            userBalance.setText(String.valueOf(buyer.getMoney()));
        }
        else if(type.equalsIgnoreCase("seller")){
            Seller seller=(Seller) MakeRequest.makeGetUserRequest();
            userMoney=seller.getMoney();
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
            finalAmount.setText("");
            wageAmount.setText("");
            String selectedItem=(String) transactionComboBox.getSelectionModel().getSelectedItem();
            if(selectedItem.equals("deposit")) deposit(allChars);
            else withdraw(allChars);
        }
        else {
            amount.setStyle("-fx-text-fill: #a30000");
            finalAmount.setText("invalid input");
            wageAmount.setText("invalid input!");
            finalAmount.setStyle("-fx-text-fill: #a30000");
            wageAmount.setStyle("-fx-text-fill: #a30000");
        }
    }


    public void deposit(String string){
        int amountMoney=Integer.parseInt(string);
        int limit=Integer.parseInt(MakeRequest.getWalletLimit());
        if((userMoney-amountMoney)< limit){
            finalAmount.setText("limit error!");
            wageAmount.setText("limit error!");
            bankBalance.setText(String.valueOf(bank));
            userBalance.setText(String.valueOf(userMoney));
            finalAmount.setStyle("-fx-text-fill: #a30000");
            wageAmount.setStyle("-fx-text-fill: #a30000");
            return;
        }
        bankBalance.setText(String.valueOf((amountMoney+bank)));
        userBalance.setText(String.valueOf(userMoney-amountMoney));
    }

    public void withdraw(String string){
        int amountMoney=Integer.parseInt(string);
        if(bank-amountMoney<0){
            finalAmount.setText("limit error!");
            wageAmount.setText("limit error!");
            bankBalance.setText(String.valueOf(bank));
            userBalance.setText(String.valueOf(userMoney));
            finalAmount.setStyle("-fx-text-fill: #a30000");
            wageAmount.setStyle("-fx-text-fill: #a30000");
            return;
        }
        bankBalance.setText(String.valueOf((bank-amountMoney)));
        userBalance.setText(String.valueOf(userMoney+amountMoney));
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }
}
