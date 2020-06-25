package View.Menus.MenuController.SellerMenuController;

import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import Controller.UserController;
import Model.Requests.SaleEdit;
import Model.Sale;
import Model.Users.Seller;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SellerManageOffs {

    @FXML
    ListView listView;
    @FXML private AnchorPane anchorPane;

    @FXML
    private void initialize(){
        MusicManager.getInstance().setSongName("first.wav");
        listView.getItems().clear();
        //inja miam listview ro update mikonim
        //click rooye yeki az ona mibare menuye edit sale
        ArrayList<Sale> allSales = SaleAndDiscountCodeController.getInstance().getSellerSales(UserController.getInstance().getCurrentOnlineUser().getUsername());
        ArrayList<String> allSalesString = new ArrayList<>();
        for(Sale sale:allSales){
            allSalesString.add(sale.toSimpleString());
            listView.getItems().add(sale.toSimpleString());
        }
        setFont();
    }

    @FXML
    private void startSale(){
        SceneSwitcher.getInstance().saveScene("SellerManageOffs");
        SceneSwitcher.getInstance().setSceneTo("SellerAddOff");
    }

    private void setFont(){
        for(Node node:anchorPane.getChildren()){
            if(node instanceof Label){
                ((Label)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
            }
            if(node instanceof Text){
                ((Text)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
            }
            if(node instanceof TextArea){
                ((TextArea)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
            }
            if(node instanceof TextField){
                ((TextField)node).setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
            }
        }
    }

    @FXML
    private void saleSelect(){
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String saleID=listView.getItems().get(index).toString().substring(0,5);
        listView.getSelectionModel().clearSelection();
        if(SaleAndDiscountCodeController.getInstance().isThereSaleWithId(saleID)) {
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
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
}
