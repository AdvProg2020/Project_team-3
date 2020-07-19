package Project.Client.Menus.MenuController;

/// for buyer and seller!!!!

import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TransactionMenu {

    public TextField bankAccountIdLabel;
    public ComboBox transactionComboBox;
    public Label userBalanceLabel;
    public Label bankAccountBalanceLabel;
    public Label finalAmountLabel;
    public TextField amountLabel;
    public Label wageAmountLabel;

    public void initialize(){


    }
    public void pay(ActionEvent actionEvent) {
    }


    public void amountTyped(KeyEvent keyEvent) {
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }
}
