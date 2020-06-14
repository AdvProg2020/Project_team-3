package View.Menus.MenuController.SellerMenuController;

import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import Controller.UserController;
import Model.Requests.SaleEdit;
import Model.Sale;
import Model.Users.Seller;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class SellerManageOffs {

    @FXML
    ListView listView;


    @FXML
    private void initialize(){
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
            SceneSwitcher.getInstance().setSceneTo("SellerEditOff", 1280, 720);
        }
    }

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
}
