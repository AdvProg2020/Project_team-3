package View.Menus.MenuController.SellerMenuController;

import Controller.*;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    @FXML CheckBox sellerNameCheckBox;
    @FXML CheckBox filterSaleCheckBox;

    @FXML TextField attributeKey;
    @FXML TextField attributeValue;
    @FXML TextField minPrice;
    @FXML TextField maxPrice;
    @FXML TextField search;
    @FXML TextField brandName;
    @FXML TextField categoryName;
    @FXML TextField sellerName;
    @FXML
    AnchorPane anchorPane;

    @FXML public void initialize() {
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        update();
        updateFilter();
        setFont();
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

    public void update() {
        listView.getItems().clear();
        listView.getItems().addAll(SortAndFilterController.getInstance().show(UserController.getInstance().getSellerItems()));
        if(listView.getItems().isEmpty()) {
            listView.getItems().add("there are no products right now");
            return;
        }
    }
    public void updateFilter(){
        SortAndFilterController control=SortAndFilterController.getInstance();
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
        if(control.getFilterSellerName()){
            sellerNameCheckBox.setSelected(true);
            sellerName.setText(control.getSellerName());
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
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
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
        MusicManager.getInstance().playSound("Button");
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilterController.getInstance().disableSort();
        }else {
            SortAndFilterController.getInstance().activateSort(sort); }
        update();
    }

    //filters
    private Boolean isValidAlphabeticTextField(TextField textField){
        try{
            String text=textField.getText();
            if(text.isEmpty()) return false;
            Pattern p = Pattern.compile("[^a-zA-Z]");
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
        SortAndFilterController.getInstance().disableFilterAttribute();
        SortAndFilterController.getInstance().disableFilterAvailability();
        SortAndFilterController.getInstance().disableFilterBrandName();
        SortAndFilterController.getInstance().disableFilterCategoryName();
        SortAndFilterController.getInstance().disableFilterName();
        SortAndFilterController.getInstance().disableFilterPriceRange();
        SortAndFilterController.getInstance().disableFilterSellerName();
        availableCheckBox.setSelected(false);
        categoryNameCheckBox.setSelected(false);
        brandNameCheckBox.setSelected(false);
        searchCheckBox.setSelected(false);
        priceCheckBox.setSelected(false);
        attributeCheckBox.setSelected(false);
        sellerNameCheckBox.setSelected(false);
        attributeKey.clear();
        attributeValue.clear();
        minPrice.clear();
        maxPrice.clear();
        search.clear();
        brandName.clear();
        categoryName.clear();
        sellerName.clear();

    }

    public void filterAvailibility(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(availableCheckBox.isSelected()){
            SortAndFilterController.getInstance().activateFilterAvailability();
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterAvailability();
        update();
    }

    public void filterCategoryName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryName))){
            SortAndFilterController.getInstance().activateFilterCategoryName(categoryName.getText());
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterCategoryName();
        categoryNameCheckBox.setSelected(false);
        update();
    }

    public void filterBrandName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((brandNameCheckBox.isSelected())&&(isValidAlphabeticTextField(brandName))){
            SortAndFilterController.getInstance().activateFilterBrandName(brandName.getText());
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterBrandName();
        brandNameCheckBox.setSelected(false);
        update();
    }

    public void filterSearch(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
            SortAndFilterController.getInstance().activateFilterName(search.getText());
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterName();
        searchCheckBox.setSelected(false);
        update();
    }

    public void sellerFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((sellerNameCheckBox.isSelected())&&(isValidAlphabeticTextField(sellerName))){
            SortAndFilterController.getInstance().activateFilterSellerName(sellerName.getText());
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterSellerName();
        sellerNameCheckBox.setSelected(false);
        update();
    }

    public void attributeFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((attributeCheckBox.isSelected())&&(isValidAlphabeticTextField(attributeKey))&&(isValidAlphabeticTextField(attributeValue))){
            SortAndFilterController.getInstance().activateFilterAttribute(attributeKey.getText(),attributeValue.getText());
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterAttribute();
        attributeCheckBox.setSelected(false);
        update();
    }

    public void priceFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((priceCheckBox.isSelected())&&(isValidPositiveDoubleTextField(minPrice))&&(isValidPositiveDoubleTextField(maxPrice))){
            SortAndFilterController.getInstance().activateFilterPriceRange(Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterPriceRange();
        priceCheckBox.setSelected(false);
        update();
    }

    public void saleFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(filterSaleCheckBox.isSelected()){
            SortAndFilterController.getInstance().activateFilterSale();
            update();
            return;
        }
        SortAndFilterController.getInstance().disableFilterSale();
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

    public void filterSellerTextChange(KeyEvent keyEvent) {
        if(sellerNameCheckBox.isSelected()) sellerFilter(null);
    }
}
