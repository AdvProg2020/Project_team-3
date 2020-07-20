package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// bug dareh!!!

public class AdminTransaction {
    public TextField bankBalance;
    public TextField wagePercentText;
    public TextField minMoneyText;
    private String wage="";
    private String min="";

    public void initialize(){
        bankBalance.setText(MakeRequest.getBankAccountBalance());
        wagePercentText.setText(MakeRequest.getWagePercent());
        minMoneyText.setText(MakeRequest.getWalletLimit());
        wage=wagePercentText.getText();
        min=minMoneyText.getText();
    }

    public void changeWage(ActionEvent actionEvent) {
        if(wagePercentText.getStyle().equals("-fx-text-fill: #a30000") || minMoneyText.getStyle().equals("-fx-text-fill: #a30000")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("invalid input!");
            alert.showAndWait();
            return;
        }
        ///MakeRequest.setTransactionNumbers(wagePercentText.getText(),minMoneyText.getText());
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
        //MakeRequest.setTransactionNumbers(wagePercentText.getText(),minMoneyText.getText());
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
