package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MenuController.ItemMenuController;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import Project.Client.Model.SortAndFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class SellerManageProductsMenu {
    @FXML private ListView listView;
    @FXML private ChoiceBox sortChoiceBox;
    @FXML private AnchorPane pane;
    @FXML CheckBox availableCheckBox;
    @FXML CheckBox categoryNameCheckBox;
    @FXML CheckBox brandNameCheckBox;
    @FXML CheckBox searchCheckBox;
    @FXML CheckBox priceCheckBox;
    @FXML CheckBox attributeCheckBox;
    @FXML CheckBox filterSaleCheckBox;

    @FXML TextField attributeKey;
    @FXML TextField attributeValue;
    @FXML TextField minPrice;
    @FXML TextField maxPrice;
    @FXML TextField search;
    @FXML TextField brandName;
    @FXML TextField categoryName;



    @FXML public void initialize() {
        SortAndFilter.getInstance().reset();
        MakeRequest.resetFilter();
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        sortChoiceBox.getItems().addAll(SortAndFilter.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        update();
        updateFilter();
    }

    public void update() {
        listView.getItems().clear();
        listView.getItems().addAll(MakeRequest.makeGetAllSellerItems());
        SortAndFilter.getInstance().disableFilterSellerName();
        if(listView.getItems().isEmpty()) {
            listView.getItems().add("there are no products right now");
            return;
        }
    }

    public void updateFilter(){
        SortAndFilter control=SortAndFilter.getInstance();
        if(control.getFilterAttribute()){
            attributeCheckBox.setSelected(true);
            attributeKey.setText(control.getAttributeKey());
            attributeValue.setText(control.getAttributeValue());
        }
        if(control.getFilterAvailability()){
            availableCheckBox.setSelected(true);
        }
        if(control.getFilterBrand()){
            brandNameCheckBox.setSelected(true);
            brandName.setText(control.getBrandName());
        }
        if(control.getFilterCategoryName()){
            categoryNameCheckBox.setSelected(true);
            categoryName.setText(control.getCategoryName());
        }
        if(control.getFilterPriceRange()){
            priceCheckBox.setSelected(true);
            minPrice.setText(control.getMinPrice());
            maxPrice.setText(control.getMaxPrice());
        }
        if(control.getFilterName()){
            searchCheckBox.setSelected(true);
            search.setText(control.getName());
        }
    }
    @FXML
    private void requestSelect(){
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String itemID=listView.getItems().get(index).toString().substring(4,9);
        listView.getSelectionModel().clearSelection();
        if(MakeRequest.isThereProductWithId(itemID)) {
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
        if(MakeRequest.isThereProductWithId(itemID)) {
            SellerEditItemMenu.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("SellerEditItemMenu", 1280, 720);
        }else{
            manageError.setText("Invalid Item ID.");
            manageError.setTextFill(Color.rgb(255,0,0));
        }

    }

    @FXML
    private void addProduct(){
        SceneSwitcher.getInstance().saveScene("SellerManageProductsMenu");
        SceneSwitcher.getInstance().setSceneTo("SellerAddProductMenu");
    }
    @FXML
    private void back(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        MusicManager.getInstance().playSound("Button");
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void itemSelect(MouseEvent mouseEvent) {
            int index=listView.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            if(index==-1)
                return;
            String itemId=listView.getItems().get(index).toString().substring(4,9);
            listView.getSelectionModel().clearSelection();
            if(MakeRequest.isThereProductWithId(itemId)) {
                SellerEditItemMenu.setItemID(itemId);
                SceneSwitcher.getInstance().setSceneTo("SellerEditItemMenu", 1280, 720);
             }
    }

    public void sort(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilter.getInstance().disableSort();
        }else {
            SortAndFilter.getInstance().activateSort(sort); }
        update();
    }

    //filters
    private Boolean isValidAlphabeticTextField(TextField textField){
        try{
            String text=textField.getText();
            if(text.isEmpty()) return false;
            Pattern p = Pattern.compile("[^a-zA-Z0-9]");
            return !p.matcher(text).find();
        }catch (Exception e){
            return false;
        }
    }

    private Boolean isValidPositiveDoubleTextField(TextField textField){
        try{
            if(textField.getText().isEmpty()) return false;
            double number=Double.parseDouble(textField.getText());
            return number>=0;
        }catch (Exception e){
            return false;
        }
    }

    public void reset(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SortAndFilter.getInstance().disableFilterAttribute();
        SortAndFilter.getInstance().disableFilterAvailability();
        SortAndFilter.getInstance().disableFilterBrandName();
        SortAndFilter.getInstance().disableFilterCategoryName();
        SortAndFilter.getInstance().disableFilterName();
        SortAndFilter.getInstance().disableFilterPriceRange();
        SortAndFilter.getInstance().disableFilterSellerName();
        availableCheckBox.setSelected(false);
        categoryNameCheckBox.setSelected(false);
        brandNameCheckBox.setSelected(false);
        searchCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        attributeCheckBox.setSelected(false);
        attributeKey.clear();
        attributeValue.clear();
        minPrice.clear();
        maxPrice.clear();
        search.clear();
        brandName.clear();
        categoryName.clear();
    }

    public void filterAvailibility(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(availableCheckBox.isSelected()){
            SortAndFilter.getInstance().activateFilterAvailability();
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterAvailability();
        update();
    }

    public void filterCategoryName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryName))){
            SortAndFilter.getInstance().activateFilterCategoryName(categoryName.getText());
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterCategoryName();
        categoryNameCheckBox.setSelected(false);
        update();
    }

    public void filterBrandName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((brandNameCheckBox.isSelected())&&(isValidAlphabeticTextField(brandName))){
            SortAndFilter.getInstance().activateFilterBrandName(brandName.getText());
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterBrandName();
        brandNameCheckBox.setSelected(false);
        update();
    }

    public void filterSearch(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
            SortAndFilter.getInstance().activateFilterName(search.getText());
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterName();
        searchCheckBox.setSelected(false);
        update();
    }

    public void attributeFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((attributeCheckBox.isSelected())&&(isValidAlphabeticTextField(attributeKey))&&(isValidAlphabeticTextField(attributeValue))){
            SortAndFilter.getInstance().activateFilterAttribute(attributeKey.getText(),attributeValue.getText());
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterAttribute();
        attributeCheckBox.setSelected(false);
        update();
    }

    public void priceFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((priceCheckBox.isSelected())&&(isValidPositiveDoubleTextField(minPrice))&&(isValidPositiveDoubleTextField(maxPrice))){
            SortAndFilter.getInstance().activateFilterPriceRange(Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterPriceRange();
        priceCheckBox.setSelected(false);
        update();
    }

    public void saleFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(filterSaleCheckBox.isSelected()){
            SortAndFilter.getInstance().activateFilterSale();
            update();
            return;
        }
        SortAndFilter.getInstance().disableFilterSale();
        update();
    }

    public void filterCategoryTextChange(KeyEvent keyEvent) {
        if(categoryNameCheckBox.isSelected()) filterCategoryName(null);
    }

    public void filterBrandNameTextChange(KeyEvent keyEvent) {
        if(brandNameCheckBox.isSelected()) filterBrandName(null);
    }

    public void filterSearchTextChange(KeyEvent keyEvent) {
        if(searchCheckBox.isSelected())  filterSearch(null);
    }

    public void filterPriceTextChange(KeyEvent keyEvent) {
        if(priceCheckBox.isSelected()) priceFilter(null);
    }

    public void filterAttributeTextChange(KeyEvent keyEvent) {
        if(attributeCheckBox.isSelected()) attributeFilter(null);
    }


}
