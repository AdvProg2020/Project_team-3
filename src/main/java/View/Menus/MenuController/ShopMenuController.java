package View.Menus.MenuController;

import Controller.*;
import Model.Item;
import View.Menus.ItemMenu;
import View.Menus.MenuController.AdminMenu.ManageRequestIn;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ShopMenuController {

    private String categoryName = "Main";
    private ArrayList<String> itemsToString = new ArrayList<>();
    private ArrayList<String> itemsID = new ArrayList<>();
    private ArrayList<VBox> itemsVBox = new ArrayList<>();
    private ArrayList<String> allCategories = new ArrayList<>();
    private ArrayList<String> subCategories = new ArrayList<>();

    @FXML private ChoiceBox sortChoiceBox;
    @FXML CheckBox availableCheckBox;
    @FXML CheckBox categoryNameCheckBox;
    @FXML CheckBox brandNameCheckBox;
    @FXML CheckBox searchCheckBox;
    @FXML CheckBox priceCheckBox;
    @FXML CheckBox attributeCheckBox;
    @FXML CheckBox sellerNameCheckBox;
    @FXML CheckBox filterSale;

    @FXML TextField attributeKey;
    @FXML TextField attributeValue;
    @FXML TextField minPrice;
    @FXML TextField maxPrice;
    @FXML TextField search;
    @FXML TextField brandName;
    @FXML TextField categoryNameFilter;
    @FXML TextField sellerName;

    @FXML private ListView allCat;
    @FXML private ListView subCat;
    @FXML private Label pageNum;
    @FXML private Button pageNumAdd;
    @FXML private Button pageNumReduce;

    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }


    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label categoryLabel;

    @FXML
    private void initialize(){
        //categoryName = "Main";
        allCategories = Database.getInstance().printFolderContent("Categories");
        updateAllCats();
        sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        updateFilter();
        initLists();
        gridPane.setMaxHeight(500000);

        //bayad item haye shop ro bedast biarim va chizayi mesle filter sort va category ke mitonan
        //arrayliste item haro dastkari konan ro poshesh bedim , inja miaim item haro bargozari mikonim
        //catgory o filter o sort o in kosshera ro badan mizanim
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
            categoryNameFilter.setText(control.getCategoryName());
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
    private void initLists(){
        gridPane.getChildren().removeAll(itemsVBox);
        itemsVBox.clear();
        itemsID.clear();
        itemsToString.clear();
        categoryLabel.setText("Currently Browsing "+categoryName);
        subCategories = ItemAndCategoryController.getInstance().getCategoryByName(categoryName).getSubCategories();
        updateSubCats();
        itemsToString = SortAndFilterController.getInstance().show(categoryName);
        for(String string : itemsToString){
            itemsID.add(string.substring(4,9));
        }
        int row=0,column=0;
        for(String itemID : itemsID){
            gridPane.add(createAndAddItem(itemID),column,row);
            column++;
            if(column==3){
                column=0;
                row++;
            }
        }

    }

    private VBox createAndAddItem(String itemID){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        VBox itemBox = new VBox();

        itemBox.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });
        itemBox.setPrefSize(230,345);

        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));

        Label name = new Label(item.getName());
        Label price = new Label(Double.toString(item.getPrice()));

        itemBox.getChildren().add(imageView);
        itemBox.getChildren().add(name);
        itemBox.getChildren().add(price);

        itemsVBox.add(itemBox);
        return itemBox;
    }




    public void sort(ActionEvent actionEvent) {
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilterController.getInstance().disableSort();
        }else {
            SortAndFilterController.getInstance().activateSort(sort); }
        initLists();
    }

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

    private void updateAllCats(){
        allCat.getItems().clear();
        for(Object string:allCategories){
            allCat.getItems().add(string);
        }
    }

    private void updateSubCats(){
        subCat.getItems().clear();
        for (Object string:subCategories){
            subCat.getItems().add(string);
        }
    }

    public void reset(ActionEvent actionEvent) {
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
        categoryNameFilter.clear();
        sellerName.clear();
        gridPane.getChildren().removeAll(itemsVBox);
    }


    public void categorySelectFromAll() {
        int index=allCat.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String category=allCat.getItems().get(index).toString();
        allCat.getSelectionModel().clearSelection();
        if(ItemAndCategoryController.getInstance().isThereCategoryWithName(category)) {
            categoryName = category;
            initLists();
        }
    }

    public void categorySelectFromSubs() {
        int index=subCat.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String category=subCat.getItems().get(index).toString();
        subCat.getSelectionModel().clearSelection();
        if(ItemAndCategoryController.getInstance().isThereCategoryWithName(category)) {
            categoryName = category;
            initLists();
        }
    }
    public void filterAvailibility(MouseEvent mouseEvent) {
        if(availableCheckBox.isSelected()){
            SortAndFilterController.getInstance().activateFilterAvailability();
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterAvailability();
        initLists();
    }

    public void filterCategoryName(MouseEvent mouseEvent) {
        if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryNameFilter))){
            SortAndFilterController.getInstance().activateFilterCategoryName(categoryNameFilter.getText());
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterCategoryName();
        categoryNameCheckBox.setSelected(false);
        initLists();
    }

    public void filterBrandName(MouseEvent mouseEvent) {
        if((brandNameCheckBox.isSelected())&&(isValidAlphabeticTextField(brandName))){
            SortAndFilterController.getInstance().activateFilterBrandName(brandName.getText());
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterBrandName();
        brandNameCheckBox.setSelected(false);
        initLists();
    }

    public void filterSearch(MouseEvent mouseEvent) {
        if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
            SortAndFilterController.getInstance().activateFilterName(search.getText());
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterName();
        searchCheckBox.setSelected(false);
        initLists();
    }

    public void sellerFilter(MouseEvent mouseEvent) {
        if((sellerNameCheckBox.isSelected())&&(isValidAlphabeticTextField(sellerName))){
            SortAndFilterController.getInstance().activateFilterSellerName(sellerName.getText());
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterSellerName();
        sellerNameCheckBox.setSelected(false);
        initLists();
    }

    public void attributeFilter(MouseEvent mouseEvent) {
        if((attributeCheckBox.isSelected())&&(isValidAlphabeticTextField(attributeKey))&&(isValidAlphabeticTextField(attributeValue))){
            SortAndFilterController.getInstance().activateFilterAttribute(attributeKey.getText(),attributeValue.getText());
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterAttribute();
        attributeCheckBox.setSelected(false);
        initLists();
    }

    public void priceFilter(MouseEvent mouseEvent) {
        if((priceCheckBox.isSelected())&&(isValidPositiveDoubleTextField(minPrice))&&(isValidPositiveDoubleTextField(maxPrice))){
            SortAndFilterController.getInstance().activateFilterPriceRange(Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterPriceRange();
        priceCheckBox.setSelected(false);
        initLists();
    }

    public void saleFilter(MouseEvent mouseEvent) {
        if(filterSale.isSelected()){
            SortAndFilterController.getInstance().activateFilterSale();
            initLists();
            return;
        }
        SortAndFilterController.getInstance().disableFilterSale();
        initLists();
    }
}
