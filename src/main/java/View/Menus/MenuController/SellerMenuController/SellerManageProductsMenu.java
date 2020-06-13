package View.Menus.MenuController.SellerMenuController;

import Controller.*;
import View.Menus.ItemMenu;
import View.Menus.MenuController.AdminMenu.ManageRequestIn;
import View.Menus.MenuController.ItemMenuController;
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
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String itemID=listView.getItems().get(index).toString().substring(4,9);
        listView.getSelectionModel().clearSelection();
        if(ItemAndCategoryController.getInstance().isThereItemWithId(itemID)) {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("ItemMenu", 1280, 720);
        }
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
