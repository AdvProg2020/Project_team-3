package View.Menus.MenuController.SellerMenuController;

import Controller.Database;
import Controller.SortAndFilterController;
import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SellerManageProductsMenu {




    // didane hamme product ha + emkane delete ya edit
    @FXML
    private ListView listView;
    @FXML
    private void initialize(){
        listView.getItems().clear();
        for (Object item: SortAndFilterController.getInstance().show(UserController.getInstance().getSellerItems())){
            listView.getItems().add(item);
        }
        if(listView.getItems().isEmpty())
            listView.getItems().add("You have no items");
    }

    @FXML
    private void requestSelect(){

    }



    @FXML
    private void addProduct(){
        SceneSwitcher.getInstance().setSceneTo("SellerAddProductMenu");
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
