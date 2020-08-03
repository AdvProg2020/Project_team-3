package Project.Client.Menus.MenuController.BuyerMenu;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Cart;


import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

public class PurchaseMenu {
   public ComboBox transactionBox;
   @FXML TextField address;
   @FXML Label priceLabel;
   @FXML ListView itemListView;
   @FXML ChoiceBox discounts;
   private Boolean discountIsValid=false;
   private String bankAccountId="";
   @FXML private AnchorPane pane;
   @FXML
   public void initialize()  {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      transactionBox.getItems().add("from wallet");
      transactionBox.getItems().add("from bank Account");
      transactionBox.getSelectionModel().selectFirst();
      discounts.getItems().addAll(MakeRequest.makeGetBuyerDiscountCodesRequest());
      discounts.getItems().add("NONE");
      discounts.setValue("NONE");
      priceLabel.setText("price="+String.valueOf(MakeRequest.getCartPriceWithoutDiscount()));
      itemListView.getItems().addAll(Cart.getInstance().toString());
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
      if(validateAddress()==false) {
         MusicManager.getInstance().playSound("notify");
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("enter a valid address");
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
            message = MakeRequest.buyCart(discounts.getValue().toString().substring(16, 21), address.getText(), bankAccountId);
         } else {
            if (selected.equals("from bank Account")) {
               bankAccountWithDraw();
            }
            if(selected.equals("from bank Account") && bankAccountId.equals("")) return;
            message = MakeRequest.buyCart(null, address.getText(), bankAccountId);
         }
         MusicManager.getInstance().playSound("notify");
         SceneSwitcher.getInstance().sendAlert(message.contains("rror"),message);
         if (message.startsWith("Successful")) SceneSwitcher.getInstance().back();
      }
   }

   private Boolean validateAddress(){
      try{
         String text=address.getText();
         if((text.isEmpty())||(text.length()<5)) return false;
         return true;
      }catch (Exception e){
         return false;
      }
   }

   public String getDiscountCode(){
      return discounts.getValue().toString().substring(16,21);
   }

}
