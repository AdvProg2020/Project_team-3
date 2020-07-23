package Project.Client.Menus.MenuController;

/// for buyer and seller!!!!

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Users.Buyer;
import Project.Client.Model.Users.Seller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    public TextArea description;
    public ComboBox getTransaction;
    public ListView transactionList;
    public TextField receiptId;
    private double userMoney=0;
    private double bank=0;
    private double wagePercent=0;
    private String allChars="";

    public void initialize(){
        setBankAccountBalance();
        setUserBalance();
        transactionComboBox.getItems().add("deposit"); //variz be hesab
        transactionComboBox.getItems().add("withdraw");// bardasht az hesab!
        transactionComboBox.getSelectionModel().selectFirst();
        getTransactionInitiate();
        getTransaction.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selected=(String) getTransaction.getSelectionModel().getSelectedItem();
                listViewUpdate(selected);
            }
        });
        transactionComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selected=(String) transactionComboBox.getSelectionModel().getSelectedItem();
                if(selected.equalsIgnoreCase("deposit")) depositChecker();
                else withdrawChecker();
            }
        });

    }

    public void getTransactionInitiate(){
        getTransaction.getItems().add("all"); //*
        getTransaction.getItems().add("input");//-
        getTransaction.getItems().add("output");//+
    }

    public void listViewUpdate(String selected){
        transactionList.getItems().clear();
        String returned="";
        if(selected.equals("all")) returned=MakeRequest.getTransaction("*");
        else if(selected.equals("input")) returned=MakeRequest.getTransaction("-");
        else if(selected.equals("output")) returned=MakeRequest.getTransaction("+");
        if(returned.equals("")) return;
        String [] token=returned.split("\\*");
        for(String string:token){
            string=string.replaceAll("\"","");
            string=string.replaceAll(",","\n");
            string=string.substring(1,string.length()-1);
            transactionList.getItems().add(string);
        }
    }


    public void setBankAccountBalance(){
        String bBalance= MakeRequest.getBankAccountBalance();
        if(bBalance.equals("token expired") || bBalance.equals("token is invalid")){
            tokenError();
            bBalance= MakeRequest.getBankAccountBalance();
        }
        bankBalance.setText(bBalance);
        bank=Double.parseDouble(bankBalance.getText());
        wagePercent=Integer.parseInt(MakeRequest.getWagePercent());
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
        if(finalAmount.getText().equals("invalid input") || finalAmount.getText().equals("limit error!")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Transaction Error!");
            alert.setContentText("you must correct the invalid fields!");
            alert.showAndWait();
            return;
        }
        if(bankAccountId.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Transaction Error!");
            alert.setContentText("you must fill the blank!");
            alert.showAndWait();
            return;
        }
        String selectedItem=(String) transactionComboBox.getSelectionModel().getSelectedItem();
        if(selectedItem.equals("deposit")){
            deposit();
        }
        else if(selectedItem.equals("withdraw")){
            withDraw();
        }
        String type=MakeRequest.makeGetUserRequest().type;
        if(type.equalsIgnoreCase("buyer")) SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
        else SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }

    public void amountTyped(KeyEvent keyEvent) {
        allChars=amount.getText() + keyEvent.getText();
        if(allChars.matches("\\d+")){
            amount.setStyle("-fx-text-fill: green");
            finalAmount.setText("");
            wageAmount.setText("");
            String selectedItem=(String) transactionComboBox.getSelectionModel().getSelectedItem();
            if(selectedItem.equals("deposit")) depositChecker();
            else withdrawChecker();
        }
        else {
            amount.setStyle("-fx-text-fill: #a30000");
            finalAmount.setText("invalid input");
            wageAmount.setText("invalid input!");
            finalAmount.setStyle("-fx-text-fill: #a30000");
            wageAmount.setStyle("-fx-text-fill: #a30000");
        }
    }


    public void depositChecker(){
        if(allChars.equals("")) return;
        int amountMoney=Integer.parseInt(allChars);
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
        finalAmount.setStyle("-fx-text-fill: green");
        wageAmount.setStyle("-fx-text-fill: green");
        bankBalance.setText(String.valueOf((int)(bank+(((100-wagePercent)/100)*amountMoney))));
        userBalance.setText(String.valueOf((int)(userMoney-amountMoney)));
        finalAmount.setText(String.valueOf((int)(((100-wagePercent)/100)*amountMoney)));
        wageAmount.setText(String.valueOf((int)(((wagePercent)/100)*amountMoney)));
    }

    public void withdrawChecker(){
        if(allChars.equals("")) return;
        int amountMoney=Integer.parseInt(allChars);
        if(bank-amountMoney<0){
            finalAmount.setText("limit error!");
            wageAmount.setText("limit error!");
            bankBalance.setText(String.valueOf(bank));
            userBalance.setText(String.valueOf(userMoney));
            finalAmount.setStyle("-fx-text-fill: #a30000");
            wageAmount.setStyle("-fx-text-fill: #a30000");
            return;
        }
        finalAmount.setStyle("-fx-text-fill: green");
        wageAmount.setStyle("-fx-text-fill: green");
        bankBalance.setText(String.valueOf((int)(bank-amountMoney)));
        userBalance.setText(String.valueOf((int)(userMoney+((100-wagePercent)/100)*amountMoney)));
        finalAmount.setText(String.valueOf((int)(((100-wagePercent)/100)*amountMoney)));
        wageAmount.setText(String.valueOf((int)(((wagePercent)/100)*amountMoney)));
    }

    public void deposit(){
        String result0="";
        String result1="";
        String receipt0=MakeRequest.makeBankReceiptRequest("deposit",amount.getText(),"-1","10001",description.getText(), Client.getInstance().getBankAccountToken());
        result0=MakeRequest.payReceipt(receipt0);
        if(receipt0.matches("\\d+")){
            String adminToken=MakeRequest.getBankTokenForClient("admin","12345");
            String receipt1=MakeRequest.makeBankReceiptRequest("move",finalAmount.getText(),"10001",bankAccountId.getText(),description.getText(),adminToken);
            result1=MakeRequest.payReceipt(receipt1);
            System.out.println(result1);
            if(result0.equalsIgnoreCase("done successfully")){
                double d=Double.parseDouble(amount.getText());
                System.out.println(d);
                MakeRequest.setUserMoney(String.valueOf(userMoney-d));
                makeAlertBox(result0+" receipt Id is: "+receipt0,"information");
            }
            return;
        }
        else if(receipt0.equalsIgnoreCase("token expired") || receipt0.equalsIgnoreCase("token is invalid")){
            tokenError();
            deposit();
        }
        else {
            makeAlertBox(receipt0,"error");
        }
    }

    public void withDraw(){
        String result0="";
        String result1="";
        String receipt0=MakeRequest.makeBankReceiptRequest("move",amount.getText(),bankAccountId.getText(),"10001",description.getText(),Client.getInstance().getBankAccountToken());
        result0=MakeRequest.payReceipt(receipt0);
        if(receipt0.matches("\\d+")){
            String adminToken=MakeRequest.getBankTokenForClient("admin","12345");
            String receipt1=MakeRequest.makeBankReceiptRequest("withdraw",finalAmount.getText(),"10001","-1",description.getText(),adminToken);
            result1=MakeRequest.payReceipt(receipt1);
            if(result0.equalsIgnoreCase("done successfully")){
                double d=Double.parseDouble(finalAmount.getText());
                MakeRequest.setUserMoney(String.valueOf(userMoney+d));
                makeAlertBox(result0+" receipt Id is: "+receipt0,"information");
            }
            return;
        }
        else if(receipt0.equalsIgnoreCase("token expired") || receipt0.equalsIgnoreCase("token is invalid")){
            tokenError();
            withDraw();
        }
        else {
            makeAlertBox(receipt0,"error");
        }
    }


    public void makeAlertBox(String message , String type){
        Alert alert=null;
        if(type.equalsIgnoreCase("error")){
            alert=new Alert(Alert.AlertType.ERROR);
        }else if(type.equalsIgnoreCase("information")){
            alert=new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }

    public void receiptTyped(KeyEvent keyEvent) {
        transactionList.getItems().clear();
        String all=receiptId.getText()+keyEvent.getText();
        if(all.equals("")) return;
        String returned=MakeRequest.getTransaction(all);
        transactionList.getItems().add(returned);
    }

}
