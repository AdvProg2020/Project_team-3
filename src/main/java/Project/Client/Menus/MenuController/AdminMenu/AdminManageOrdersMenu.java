package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Logs.BuyLog;
import Project.Client.Model.Users.Buyer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AdminManageOrdersMenu {

    @FXML private void back(){
        SceneSwitcher.getInstance().setSceneTo("AdminMenu");
    }


    @FXML private ListView allBuyers;
    @FXML private ListView allLogs;
    @FXML private Label errorLabel;
    private ArrayList<String> buyersUsername;
    private Buyer buyer;
    private int logIndex;

    @FXML private void initialize(){
        allBuyers.getItems().clear();
        buyersUsername = new ArrayList<>();
        buyersUsername = MakeRequest.makeGetAllUserRequest("Buyer",false);
        allBuyers.getItems().addAll(buyersUsername);
        logIndex = -1;
        errorLabel.setText("");
        errorLabel.setTextFill(Color.RED);
    }

    @FXML private void buyerSelect(){
        int index=allBuyers.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index==-1)
            return;
        String buyerID=allBuyers.getItems().get(index).toString();
        allBuyers.getSelectionModel().clearSelection();
        buyer = (Buyer) MakeRequest.makeGetUserByUsernameRequest(buyerID);
        logIndex = -1;
        updateLogsList();
    }

    private void updateLogsList(){
        allLogs.getItems().clear();
        ArrayList<BuyLog> logs = buyer.getBuyLogs();
        for(BuyLog log:logs){
            allLogs.getItems().add(log.toString());
        }
    }

    @FXML private void logSelect(){
        logIndex=allLogs.getSelectionModel().getSelectedIndex();
        System.out.println(logIndex);
        if(logIndex==-1)
            return;
        allLogs.getSelectionModel().clearSelection();
    }

    @FXML private void changeState(){
        if(logIndex==-1){
            errorLabel.setText("Invalid selection");
            return;
        }
        if(buyer.getBuyLogs().size() < logIndex){
            errorLabel.setText("Invalid selection");
            return;
        }
        if(buyer.getBuyLogs().get(logIndex).getState().equals("Being Delivered")){
            errorLabel.setText("Order is already being delivered");
            return;
        }
        MakeRequest.deliverLog(buyer.getUsername(),logIndex);
        initialize();
    }
}
