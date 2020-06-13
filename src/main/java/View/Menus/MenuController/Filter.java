package View.Menus.MenuController;

import Controller.SortAndFilterController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class Filter {
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
   @FXML TextField categoryName;
   @FXML TextField sellerName;

   private static  String sceneName;

   @FXML public void initialize() {
      update();
   }

   public void update(){
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

   public void back(ActionEvent actionEvent) {
      SortAndFilterController control=SortAndFilterController.getInstance();
      if(availableCheckBox.isSelected()){
         control.activateFilterAvailability();
      }
      if((sellerNameCheckBox.isSelected())&&(isValidAlphabeticTextField(sellerName))){
         control.activateFilterSellerName(sellerName.getText());
      }
      if((brandNameCheckBox.isSelected())&&(isValidAlphabeticTextField(brandName))){
         control.activateFilterBrandName(brandName.getText());
      }
      if((categoryNameCheckBox.isSelected())&&(isValidAlphabeticTextField(categoryName))){
         control.activateFilterCategoryName(categoryName.getText());
      }
      if ((attributeCheckBox.isSelected())&&(isValidAlphabeticTextField(attributeKey))&&(isValidAlphabeticTextField(attributeValue))){
         control.activateFilterAttribute(attributeKey.getText(),attributeValue.getText());
      }
      if((searchCheckBox.isSelected())&&(isValidAlphabeticTextField(search))){
         control.activateFilterName(search.getText());
      }
      if((priceCheckBox.isSelected())&&(isValidPositiveDoubleTextField(minPrice))&&(isValidPositiveDoubleTextField(maxPrice))){
         control.activateFilterPriceRange(Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
      }
      SceneSwitcher.getInstance().setSceneTo(sceneName);
   }

   private Boolean isValidAlphabeticTextField(TextField textField){
      try{
         String text=textField.getText();
         Pattern p = Pattern.compile("[^a-zA-Z]");
         return !p.matcher(text).find();
      }catch (Exception e){
         return false;
      }
   }

   private Boolean isValidPositiveDoubleTextField(TextField textField){
      try{
         double number=Double.parseDouble(textField.getText());
         return number>=0;
      }catch (Exception e){
         return false;
      }
   }

   public static void setSceneName(String sceneName) {
      Filter.sceneName = sceneName;
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
       categoryName.clear();
      sellerName.clear();

   }
}
