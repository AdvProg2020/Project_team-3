package Project.Client.Menus.MenuController;


import Project.Client.CLI.View;
import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Item;
import Project.Client.Model.SortAndFilter;

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

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileShopController {
   public Menu menu;
   private ArrayList<String> itemsToString = new ArrayList<>();
   private ArrayList<String> itemsID = new ArrayList<>();
   private ArrayList<VBox> itemsVBox = new ArrayList<>();
   private int pageNumber=1;

   @FXML private ChoiceBox sortChoiceBox;

   @FXML CheckBox searchCheckBox;
   @FXML CheckBox priceCheckBox;
   @FXML CheckBox sellerNameCheckBox;



   @FXML TextField minPrice;
   @FXML TextField maxPrice;
   @FXML TextField search;

   @FXML TextField sellerName;


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


   @FXML private AnchorPane pane;

   @FXML private void initialize(){
      SortAndFilter.getInstance().reset();
      MakeRequest.resetFilter();
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      pageNumber = 1;
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
      ArrayList<String> allFiles=MakeRequest.showFiles();
      itemsToString = allFiles;
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
      pageNum.setText(pageNumber+"/"+(allFiles.size() / 24 + 1));
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
         SceneSwitcher.getInstance().saveScene("FileShop");
         FileMenuController.setItemID(itemID);
         SceneSwitcher.getInstance().setSceneTo("FileMenu",1280,750);
      });
      itemBox.setPrefSize(230,345);

      final StackPane container = new StackPane();
      container.setAlignment(Pos.BOTTOM_RIGHT);
      itemBox.setAlignment(Pos.CENTER);
      Image image=Client.getInstance().getImageFromServer(item.getImageName(),"item",230,230);
      ImageView imageView=new ImageView();
      imageView.setImage(image);
      container.getChildren().add(imageView);


      Label nameAndPrice = new Label(item.getName() + "           " + (MakeRequest.makeGetItemPriceWithSaleRequest(item.getId())));

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

   public void reset(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SortAndFilter.getInstance().reset();
      searchCheckBox.setSelected(false);
      priceCheckBox.setSelected(false);
      sellerNameCheckBox.setSelected(false);
      minPrice.clear();
      maxPrice.clear();
      search.clear();
      sellerName.clear();
      gridPane.getChildren().removeAll(itemsVBox);
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

   public void filterSearchTextChange(KeyEvent keyEvent) {
      if(searchCheckBox.isSelected())  filterSearch(null);
   }

   public void filterPriceTextChange(KeyEvent keyEvent) {
      if(priceCheckBox.isSelected()) priceFilter(null);
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
      return (num>0 && num<=(MakeRequest.showFiles().size()/24 +1));
   }

   @FXML
   private void increasePage(){
      if(isAValidPage(pageNumber+1)){
        // pageNum.setText((pageNumber + 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
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


}
