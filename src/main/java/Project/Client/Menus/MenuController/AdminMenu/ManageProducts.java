package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import Project.Client.Model.SortAndFilter;


import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.regex.Pattern;

public class ManageProducts {
   @FXML private ListView listView;
   @FXML private ChoiceBox sortChoiceBox;

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
   @FXML TextField categoryNameFilter;
   @FXML TextField sellerName;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
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
      listView.getItems().addAll(MakeRequest.showProducts());
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

   public void back(ActionEvent actionEvent)  {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }


   public void sort(ActionEvent actionEvent) {
      String sort=sortChoiceBox.getValue().toString();
      if(sort.equals("sort by view")){
         SortAndFilter.getInstance().disableSort();
      }else {
         SortAndFilter.getInstance().activateSort(sort); }
      update();
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

   public void reset(ActionEvent actionEvent) {
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

   }

   public void filterAvailibility(MouseEvent mouseEvent) {
      if(availableCheckBox.isSelected()){
         SortAndFilter.getInstance().activateFilterAvailability();
         update();
         return;
      }
      SortAndFilter.getInstance().disableFilterAvailability();
      update();
   }

   public void filterCategoryName(MouseEvent mouseEvent) {
      System.out.println("sojsodjo");
      if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryNameFilter))){
         SortAndFilter.getInstance().activateFilterCategoryName(categoryNameFilter.getText());
         update();
         return;
      }
      SortAndFilter.getInstance().disableFilterCategoryName();
      categoryNameCheckBox.setSelected(false);
      update();
   }

   public void filterBrandName(MouseEvent mouseEvent) {
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
      if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
         SortAndFilter.getInstance().activateFilterName(search.getText());
         update();
         return;
      }
      SortAndFilter.getInstance().disableFilterName();
      searchCheckBox.setSelected(false);
      update();
   }

   public void sellerFilter(MouseEvent mouseEvent) {
      if((sellerNameCheckBox.isSelected())&&(isValidAlphabeticTextField(sellerName))){
         SortAndFilter.getInstance().activateFilterSellerName(sellerName.getText());
         update();
         return;
      }
      SortAndFilter.getInstance().disableFilterSellerName();
      sellerNameCheckBox.setSelected(false);
      update();
   }

   public void attributeFilter(MouseEvent mouseEvent) {
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

   public void filterSellerTextChange(KeyEvent keyEvent) {
      if(sellerNameCheckBox.isSelected()) sellerFilter(null);
   }

   public void selectItem(MouseEvent mouseEvent) {
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      if(listView.getItems().get(index).toString().isEmpty()) return;
      String id=listView.getItems().get(index).toString().substring(4,9);
      listView.getSelectionModel().clearSelection();
      if(MakeRequest.isThereProductWithId(id)) {
         ManageProductsIn.setItemId(id);
         SceneSwitcher.getInstance().setSceneAndWait("ManageProductsIn", 392, 173);
      }
   }
}



