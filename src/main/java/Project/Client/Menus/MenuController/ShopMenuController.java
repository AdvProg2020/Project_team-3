package Project.Client.Menus.MenuController;


import Project.Client.Client;
import Project.Client.MakeRequest;

import Project.Client.Model.Category;
import Project.Client.Model.Item;
import Project.Client.Model.SortAndFilter;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import Project.Client.Model.Users.Admin;
import Project.Client.Model.Users.Seller;
import Project.Client.Model.Users.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import kotlin.jvm.internal.MagicApiIntrinsics;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ShopMenuController {

    public Menu menu;
    private String categoryName = "Main";
    private ArrayList<String> itemsToString = new ArrayList<>();
    private ArrayList<String> itemsID = new ArrayList<>();
    private ArrayList<VBox> itemsVBox = new ArrayList<>();
    private ArrayList<String> allCategories = new ArrayList<>();
    private ArrayList<String> subCategories = new ArrayList<>();
    private int pageNumber=1;

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
    @FXML private ListView attributeList;
    @FXML private Label pageNum;


    public void logout(ActionEvent actionEvent) {
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }


    @FXML
    private GridPane gridPane;
    @FXML
    private Label categoryLabel;

    @FXML private AnchorPane pane;
 
    @FXML private void initialize(){
        SortAndFilter.getInstance().reset();
        MakeRequest.resetFilter();
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        allCategories =   MakeRequest.makeGetAllCategoryName();
        pageNumber = 1;
        updateAllCats();
        sortChoiceBox.getItems().addAll(SortAndFilter.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        updateFilter();
        initLists();
        gridPane.setMaxHeight(500000);
        loginHandler();
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
        MakeRequest.makeUpdateDateAndTimeRequest();
        gridPane.getChildren().removeAll(itemsVBox);
        itemsVBox.clear();
        itemsID.clear();
        itemsToString.clear();
        categoryLabel.setText("Currently Browsing "+categoryName);
        subCategories = MakeRequest.getCategory(categoryName).getSubCategories();
        updateSubCats();
        itemsToString = MakeRequest.show(categoryName);
        if(!isAValidPage(pageNumber)) pageNumber=1;
        itemsToString = getItemsInPage(itemsToString,pageNumber);
        for(String string : itemsToString){
           if(string.isEmpty()) return;
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
        pageNum.setText(pageNumber+"/"+(MakeRequest.show(categoryName).size() / 24 + 1));
        attributeList.getItems().clear();
        setAttributes();
    }

    private void loginHandler(){
        if(MakeRequest.isTokenValid()){
            addLogoutMenuItem();
        }else{
            addLoginMenuItem();
        }
    }


    private void addLoginMenuItem(){
        MenuItem login=new MenuItem("Log In");
        menu.getItems().add(0,login);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MusicManager.getInstance().playSound("Button");
                SceneSwitcher.getInstance().setSceneAndWait("Login");
                menu.getItems().remove(getMenuItemByName("Log In"));
                loginHandler();
            }
        });
    }

    private void addLogoutMenuItem(){
        MenuItem logout=new MenuItem("Logout");
        menu.getItems().add(logout);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MusicManager.getInstance().playSound("Button");
                MakeRequest.makeLogoutRequest();
                menu.getItems().remove(getMenuItemByName("Logout"));
                //addLoginMenuItem();
                loginHandler();
                showLogoutAlertBox();
            }
        });
    }


    private MenuItem getMenuItemByName(String menuItemName){
        for(MenuItem menuItem:menu.getItems()){
            if(menuItem.getText().equals(menuItemName)) return menuItem;
        }
        return null;
    }

    private void showLogoutAlertBox(){
        MusicManager.getInstance().playSound("notify");
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("logout successful!");
        alert.show();
    }

    private VBox createAndAddItem(String itemID){
        Item item = MakeRequest.getItem(itemID);
        VBox itemBox = new VBox();

        itemBox.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().saveScene("ShopMenu");
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });
        itemBox.setPrefSize(230,345);

        final StackPane container = new StackPane();
        container.setAlignment(Pos.BOTTOM_RIGHT);
        itemBox.setAlignment(Pos.CENTER);
        Image image=Client.getInstance().getImageFromServer(item.getImageName(),"item",230,230);
        //ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        if(item.getInStock()==0){
           // Image inStock=new Image(new File("src/main/resources/Images/ItemImages/soldOut.png").toURI().toString(),66,65,false,false);
            Image inStock=Client.getInstance().getImageFromServer("soldOut.png","item",66,65);
            ImageView soldOut=new ImageView(inStock);
            container.getChildren().addAll(imageView, soldOut);
        }else if( MakeRequest.isInSaleItem(item.getId())){
            //Image sale=new Image(new File("src/main/resources/Images/ItemImages/sale.png").toURI().toString(),70,64,false,false);
            Image sale=Client.getInstance().getImageFromServer("sale.png","item",70,64);
            ImageView inSale=new ImageView(sale);
            container.getChildren().addAll(imageView, inSale);
        }else{
            container.getChildren().add(imageView);
        }

        Label nameAndPrice = new Label(item.getName() + "           " + (MakeRequest.makeGetItemPriceWithSaleRequest(item.getId())));

        //Image ratingImage=new Image(new File("src/main/resources/Images/star.png").toURI().toString(),108,20,false,false);
        Image ratingImage=Client.getInstance().getImageFromServer("star","user",108,20);
        ImageView star=new ImageView(ratingImage);
        double frameWidth = (item.getRating() / 5)*108;
        Rectangle mask = new Rectangle(frameWidth, 20);
        star.setClip(mask);

        itemBox.getChildren().add(new Label(" "));
        itemBox.getChildren().add(container);
        itemBox.getChildren().add(star);
        itemBox.getChildren().add(nameAndPrice);

        itemBox.setOnMouseEntered(e -> {
            itemBox.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
        });
        itemBox.setOnMouseExited(e -> {
            itemBox.setStyle("");
        });
        itemsVBox.add(itemBox);
        return itemBox;
    }

    private void setAttributes(){
        Category category = MakeRequest.getCategory(categoryName);
        ArrayList<String> attributes = category.getAttributes();
        if(attributes==null) return;
        if(attributes.isEmpty()) return;
        for(Object attribute : attributes){
            attributeList.getItems().add(attribute);
        }
    }

    public void sort(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilter.getInstance().disableSort();
        }else {
            SortAndFilter.getInstance().activateSort(sort); }
        initLists();
    }

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
        if(MakeRequest.isThereCategoryWithName(category)) {
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
        if(MakeRequest.isThereCategoryWithName(category)) {
            categoryName = category;
            initLists();
        }
    }
    public void filterAvailibility(MouseEvent mouseEvent) {
        if(availableCheckBox.isSelected()){
            SortAndFilter.getInstance().activateFilterAvailability();
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterAvailability();
        initLists();
    }

    public void filterCategoryName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryNameFilter))){
            SortAndFilter.getInstance().activateFilterCategoryName(categoryNameFilter.getText());
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterCategoryName();
        categoryNameCheckBox.setSelected(false);
        initLists();
    }

    public void filterBrandName(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((brandNameCheckBox.isSelected())&&(isValidAlphabeticTextField(brandName))){
            SortAndFilter.getInstance().activateFilterBrandName(brandName.getText());
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterBrandName();
        brandNameCheckBox.setSelected(false);
        initLists();
    }

    public void filterSearch(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
            SortAndFilter.getInstance().activateFilterName(search.getText());
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterName();
        searchCheckBox.setSelected(false);
        initLists();
    }

    public void sellerFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((sellerNameCheckBox.isSelected())&&(isValidAlphabeticTextField(sellerName))){
            SortAndFilter.getInstance().activateFilterSellerName(sellerName.getText());
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterSellerName();
        sellerNameCheckBox.setSelected(false);
        initLists();
    }

    public void attributeFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((attributeCheckBox.isSelected())&&(isValidAlphabeticTextField(attributeKey))){
            SortAndFilter.getInstance().activateFilterAttribute(attributeKey.getText(),attributeValue.getText());
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterAttribute();
        attributeCheckBox.setSelected(false);
        initLists();
    }

    public void priceFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if((priceCheckBox.isSelected())&&(isValidPositiveDoubleTextField(minPrice))&&(isValidPositiveDoubleTextField(maxPrice))){
            SortAndFilter.getInstance().activateFilterPriceRange(Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterPriceRange();
        priceCheckBox.setSelected(false);
        initLists();
    }

    public void saleFilter(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(filterSale.isSelected()){
            SortAndFilter.getInstance().activateFilterSale();
            initLists();
            return;
        }
        SortAndFilter.getInstance().disableFilterSale();
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

    private ArrayList<String> getItemsInPage(ArrayList<String> allItems, int page){
        ArrayList<String> ans = new ArrayList<>();
        if(page<1 || page > allItems.size()/24 + 1) return ans;
        for(int i = 24 * (page - 1);i<=24*page-1;i++){
            if(i>= allItems.size()){
                break;
            }
            ans.add(allItems.get(i));
        }
        return ans;
    }

    private boolean isAValidPage(int num){
        return (num>0 && num<=(MakeRequest.show(categoryName).size()/24 +1));
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

    public void cart( ) {
        MusicManager.getInstance().playSound("Button");
        User user=MakeRequest.makeGetUserRequest();
        if(user instanceof Seller ||user instanceof Admin) {
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("only buyers can view Cart Menu!");
            alert.showAndWait();
            return;
        }
        SceneSwitcher.getInstance().saveScene("ShopMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);
    }
}