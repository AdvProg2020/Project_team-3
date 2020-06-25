package View.Menus.MenuController;

import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import Controller.SortAndFilterController;
import Controller.UserController;
import Model.Item;
import Model.Sale;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SalesMenuController {

    @FXML
    private GridPane gridPane;

    private int pageNumber=1;
    private ArrayList<String> allItemsID;
    private ArrayList<VBox> allItemsBox;

    @FXML private ChoiceBox sortChoiceBox;
    @FXML CheckBox availableCheckBox;
    @FXML CheckBox categoryNameCheckBox;
    @FXML CheckBox brandNameCheckBox;
    @FXML CheckBox searchCheckBox;
    @FXML CheckBox priceCheckBox;
    @FXML CheckBox attributeCheckBox;
    @FXML CheckBox sellerNameCheckBox;

    @FXML TextField attributeKey;
    @FXML TextField attributeValue;
    @FXML TextField minPrice;
    @FXML TextField maxPrice;
    @FXML TextField search;
    @FXML TextField brandName;
    @FXML TextField categoryNameFilter;
    @FXML TextField sellerName;

    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        // SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }


    @FXML
    private void initialize(){
        MusicManager.getInstance().setSongName("second.wav");
        reset(null);
        allItemsBox = new ArrayList<>();
        sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        updateFilter();
        initLists();
    }

    private void initLists(){
        allItemsID = SortAndFilterController.getInstance().show(SaleAndDiscountCodeController.getInstance().getAllItemsIDWithSale());
        gridPane.getChildren().removeAll(allItemsBox);
        allItemsBox.clear();
        int row=0,column=0;
        for(String itemID : allItemsID){
            gridPane.add(createItem(itemID.substring(4,9)),column,row);
            column++;
            if(column==3){
                column=0;
                row++;
            }
        }
    }

    private VBox createItem(String itemID){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        if(item==null){
            System.out.println(itemID);
            return null;
        }
        VBox itemBox = new VBox();
        itemBox.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().saveScene("DiscountsMenu");
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });

        itemBox.setPrefSize(230,400);
        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));

        Label nameAndPrice = new Label(item.getName() + "           " + (item.getPriceWithSale()));

        Image ratingImage=new Image(new File("src/main/resources/Images/star.png").toURI().toString(),108,20,false,false);
        ImageView star=new ImageView(ratingImage);
        double frameWidth = (item.getRating() / 5)*108;
        Rectangle mask = new Rectangle(frameWidth, 20);
        star.setClip(mask);

        itemBox.getChildren().add(new Label(" "));
        itemBox.getChildren().add(imageView);
        itemBox.getChildren().add(star);
        itemBox.getChildren().add(nameAndPrice);

        Sale sale = SaleAndDiscountCodeController.getInstance().getSaleById(item.getSaleId());
        itemBox.getChildren().add(new Label("Sale Start:"+sale.getStartTime().toString()));
        itemBox.getChildren().add(new Label("Sale End:"+sale.getEndTime().toString()));
        itemBox.getChildren().add(new Label("Ends In:"+LocalDateTime.now().until(sale.getEndTime().truncatedTo(ChronoUnit.HOURS),ChronoUnit.HOURS)+" Hours."));
        itemBox.getChildren().add(new Label("Sale is %"+sale.getOffPercentage()));

        allItemsBox.add(itemBox);
        return itemBox;
    }


    private boolean isAValidPage(int num){
        return false;
    }


    @FXML
    private void increasePage(){
        if(isAValidPage(pageNumber+1)){
            //pageNum.setText((pageNumber + 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber++;
            initLists();
        }

    }

    @FXML
    private void decreasePage(){
        if(isAValidPage(pageNumber-1)){
            //pageNum.setText((pageNumber - 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber--;
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
        //gridPane.getChildren().removeAll(itemsVBox);
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

    public void sort(ActionEvent actionEvent) {
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilterController.getInstance().disableSort();
        }else {
            SortAndFilterController.getInstance().activateSort(sort); }
        initLists();
    }

    public void cart(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("ShopMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu");
    }
}