package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// bug dareh!!!

public class AdminTransaction {
    public TextField bankBalance;
    public TextField wagePercentText;
    public TextField minMoneyText;
    public ComboBox getTransaction;
    public ListView transactionList;
    private String wage="";
    private String min="";

    public void initialize(){
        bankBalance.setText(MakeRequest.getBankAccountBalance());
        wagePercentText.setText(MakeRequest.getWagePercent());
        minMoneyText.setText(MakeRequest.getWalletLimit());
        wage=wagePercentText.getText();
        min=minMoneyText.getText();
        getTransactionInitiate();
        getTransaction.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selected=(String) getTransaction.getSelectionModel().getSelectedItem();
                listViewUpdate(selected);
            }
        });
    }


    public void getTransactionInitiate(){
        getTransaction.getItems().add("all"); //*
        getTransaction.getItems().add("input");//+
        getTransaction.getItems().add("output");//-
    }

    public void listViewUpdate(String selected){
        transactionList.getItems().clear();
        String returned="";
        if(selected.equals("all")) returned=MakeRequest.getTransaction("*");
        else if(selected.equals("input")) returned=MakeRequest.getTransaction("+");
        else if(selected.equals("output")) returned=MakeRequest.getTransaction("-");
        if(returned.equals("")) return;
        String [] token=returned.split("}");
        for(String string:token){
            string=string.replaceAll("\"","");
            string=string.replaceAll(",","\n");
            string=string.substring(1,string.length()-1);
            transactionList.getItems().add(string);
        }
    }



    public void changeWage(ActionEvent actionEvent) {
        if(wagePercentText.getStyle().equals("-fx-text-fill: #a30000") || minMoneyText.getStyle().equals("-fx-text-fill: #a30000")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("invalid input!");
            alert.showAndWait();
            return;
        }
        MakeRequest.setTransactionNumbers(wagePercentText.getText(),minMoneyText.getText());
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("successful!");
        alert.showAndWait();
    }

    public void changeMin(ActionEvent actionEvent) {
        if(wagePercentText.getStyle().equals("-fx-text-fill: #a30000") || minMoneyText.getStyle().equals("-fx-text-fill: #a30000")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("invalid input!");
            alert.showAndWait();
            return;
        }
        MakeRequest.setTransactionNumbers(wagePercentText.getText(),minMoneyText.getText());
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("successful!");
        alert.showAndWait();
    }

    public void wageTyped(KeyEvent keyEvent) {
        String allChars=wagePercentText.getText()+keyEvent.getText();
        if(allChars.matches("\\d+")){
            int percent=Integer.parseInt(allChars);
            if(percent>50 || percent<0) wagePercentText.setStyle("-fx-text-fill: #a30000");
            else wagePercentText.setStyle("-fx-text-fill: green");
        }else {
            wagePercentText.setStyle("-fx-text-fill: #a30000");
        }
    }

    public void minimumTyped(KeyEvent keyEvent) {
        String allChars=minMoneyText.getText()+keyEvent.getText();
        if(allChars.matches("\\d+")){
            minMoneyText.setStyle("-fx-text-fill: green");
        }else {
            minMoneyText.setStyle("-fx-text-fill: #a30000");
        }
    }
}
