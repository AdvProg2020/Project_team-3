package Project.Client.Menus.MenuController.BuyerMenu;

import Project.Client.CLI.View;
import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;

import Project.Client.Model.Cart;
import Project.Client.Model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class FilePurchaseMenu {
   private static String itemId;
   public ComboBox transactionBox;
   @FXML Label priceLabel;
   @FXML ChoiceBox discounts;
   @FXML Label pathLabel;
   private Boolean discountIsValid=false;
   private String bankAccountId="";

   @FXML public void initialize()  {
      Cart.getInstance().empty();
      Cart.getInstance().add(itemId);
      System.out.println(Cart.getInstance().getAllItemId().toString());
      MusicManager.getInstance().setSongName("first.wav");
      transactionBox.getItems().add("from wallet");
      transactionBox.getItems().add("from bank Account");
      transactionBox.getSelectionModel().selectFirst();
      discounts.getItems().addAll(MakeRequest.makeGetBuyerDiscountCodesRequest());
      discounts.getItems().add("NONE");
      discounts.setValue("NONE");
      priceLabel.setText("price="+String.valueOf(MakeRequest.getCartPriceWithoutDiscount()));
      update();
   }

   public void update(){
      if(discountIsValid)
         priceLabel.setText("cart price before discount="+MakeRequest.getCartPriceWithoutDiscount()+"\ncart price after discount="+MakeRequest.makeGetCartPriceWithDiscountCode(discounts.getValue().toString().substring(16,21)));
   }

   public void discountChange(ActionEvent actionEvent) {
      if(discounts.getValue().toString().equals("NONE")){
         discountIsValid=false;
      }else{
         discountIsValid=true;
         update();
      }
   }

   public void back(MouseEvent mouseEvent){
      Cart.getInstance().empty();
      SceneSwitcher.getInstance().setSceneTo("CartMenu");
   }

   public void bankAccountWithDraw(){
      if(Client.getInstance().getBankAccountToken().equals(""))  SceneSwitcher.getInstance().setSceneAndWait("bankLogin" ,600 , 526);
      TextInputDialog dialog=new TextInputDialog("");
      dialog.setGraphic(null);
      dialog.setTitle("enter valid bank Account Id");
      dialog.setHeaderText("bank Account Id:");
      Optional<String> wrote=dialog.showAndWait();
      wrote.ifPresent(s -> bankAccountId=s);
      System.out.println(bankAccountId+" bank account id is: ");
   }

   public void buy(MouseEvent mouseEvent){
      if(path.isEmpty()==true) {
         MusicManager.getInstance().playSound("notify");
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("enter a  download destination");
         alert.showAndWait();
         return;
      }
      Item item=MakeRequest.getItem(itemId);
      if(MakeRequest.isSellerServerOnline(item.getSellerName())==false){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("this file seller is not online please try again later.");
         alert.showAndWait();
         return;
      }
        if(Client.getInstance().getBankAccountToken().equals(""))  SceneSwitcher.getInstance().setSceneAndWait("bankLogin" ,600 , 526);
        if(!Client.getInstance().getBankAccountToken().equals("")) {
         String message = "";
         String selected = (String) transactionBox.getSelectionModel().getSelectedItem();
         if (discountIsValid) {
            if (selected.equals("from bank Account")) {
               bankAccountWithDraw();
            }
            if(selected.equals("from bank Account") && bankAccountId.equals("")) return;
            message = MakeRequest.buyCart(discounts.getValue().toString().substring(16, 21), path, bankAccountId);
         } else {
            if (selected.equals("from bank Account")) {
               bankAccountWithDraw();
            }
            if(selected.equals("from bank Account") && bankAccountId.equals("")) return;
            message = MakeRequest.buyCart(null, path, bankAccountId);
         }
         MusicManager.getInstance().playSound("notify");
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.showAndWait();
         if (message.startsWith("Successful")){
            Client.getInstance().getFileFromServer(item,path);
            SceneSwitcher.getInstance().back();
         }
      }
   }



   public String getDiscountCode(){
      return discounts.getValue().toString().substring(16,21);
   }

   public static void setItemId(String itemId) {
      FilePurchaseMenu.itemId = itemId;
   }



   private String path="";
   public void pathChooser(MouseEvent mouseEvent) {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      File selectedDirectory = directoryChooser.showDialog(SceneSwitcher.getInstance().getStage());
      if(selectedDirectory == null){
         path="";
      }else{
         System.out.println(selectedDirectory.getAbsolutePath());
         path=selectedDirectory.getAbsolutePath();
         pathLabel.setText(path);
      }
   }
}
