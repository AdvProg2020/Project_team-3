package View.Menus.MenuController.SellerMenuController;

import Controller.*;
import View.Menus.ItemMenu;
import View.Menus.MenuController.AdminMenu.ManageRequestIn;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SellerManageProductsMenu {
    @FXML private ListView listView;
    @FXML private ChoiceBox sortChoiceBox;

    @FXML public void initialize() {
        sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        update();
    }

    public void update() {
        listView.getItems().clear();
        listView.getItems().addAll(SortAndFilterController.getInstance().show("Main"));

        if(listView.getItems().isEmpty()) {
            listView.getItems().add("there are no products right now");
            return;
        }
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
    private TextField itemTextField;
    @FXML
    private Label manageError;
    @FXML
    private void manageProduct(){
        manageError.setText("");
        String itemID = itemTextField.getText();
        if(ItemAndCategoryController.getInstance().isThereItemWithId(itemID)) {
            SellerEditItemMenu.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("SellerEditItemMenu", 1280, 720);
        }else{
            manageError.setText("Invalid Item ID.");
            manageError.setTextFill(Color.rgb(255,0,0));
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

    public void itemSelect(MouseEvent mouseEvent) {
            int index=listView.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            if(index==-1)
                return;
            String itemId=listView.getItems().get(index).toString().substring(4,9);
            listView.getSelectionModel().clearSelection();
            if(ItemAndCategoryController.getInstance().isThereItemWithId(itemId)) {
                SellerEditItemMenu.setItemID(itemId);
                SceneSwitcher.getInstance().setSceneTo("SellerEditItemMenu", 1280, 720);
             }
    }

    public void sort(ActionEvent actionEvent) {
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilterController.getInstance().disableSort();
        }else {
            SortAndFilterController.getInstance().activateSort(sort); }
        update();
    }
}
