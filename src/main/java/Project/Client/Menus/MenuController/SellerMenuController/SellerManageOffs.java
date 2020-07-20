package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class SellerManageOffs {
    @FXML private AnchorPane pane;
    @FXML
    ListView listView;


    @FXML
    private void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        listView.getItems().clear();
        listView.getItems().addAll(MakeRequest.makeGetSellerSaleToSimpleString());
        if(listView.getItems().isEmpty())
        listView.getItems().add("you dont have any sales");
    }

    @FXML
    private void startSale(){
        if(MakeRequest.makeGetAllSellerItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you dont have any product!");
            alert.showAndWait();
            return;
        }
        SceneSwitcher.getInstance().saveScene("SellerManageOffs");
        SceneSwitcher.getInstance().setSceneTo("SellerAddOff");
    }

    @FXML
    private void saleSelect(){
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String saleID=listView.getItems().get(index).toString().substring(0,5);
        listView.getSelectionModel().clearSelection();
        if(MakeRequest.isThereSaleWithId(saleID)) {
            SellerEditOff.setOffID(saleID);
            SceneSwitcher.getInstance().saveScene("SellerManageOffs");
            SceneSwitcher.getInstance().setSceneTo("SellerEditOff", 1280, 720);
        }
    }

    @FXML
    private void back(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
}
