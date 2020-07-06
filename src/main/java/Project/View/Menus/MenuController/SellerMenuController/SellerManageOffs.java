package Project.View.Menus.MenuController.SellerMenuController;

import Project.Controller.SaleAndDiscountCodeController;
import Project.Controller.UserController;
import Project.Model.Sale;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.CLI.View;
import javafx.fxml.FXML;
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
        //inja miam listview ro update mikonim
        //click rooye yeki az ona mibare menuye edit sale
        ArrayList<Sale> allSales = SaleAndDiscountCodeController.getInstance().getSellerSales(UserController.getInstance().getCurrentOnlineUser().getUsername());
        ArrayList<String> allSalesString = new ArrayList<>();
        for(Sale sale:allSales){
            allSalesString.add(sale.toSimpleString());
            listView.getItems().add(sale.toSimpleString());
        }

    }

    @FXML
    private void startSale(){
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
