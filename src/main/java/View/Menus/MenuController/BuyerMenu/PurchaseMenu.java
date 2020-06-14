package View.Menus.MenuController.BuyerMenu;

import Controller.CartController;
import Controller.UserController;
import Model.Users.User;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.net.MalformedURLException;

public class PurchaseMenu {
   @FXML TextField address;
   @FXML Label priceLabel;
   @FXML ListView itemListView;
   @FXML ChoiceBox discounts;
   private Boolean discountIsValid=false;

   @FXML
   public void initialize()  {
      discounts.getItems().addAll(UserController.getInstance().getBuyerDiscountCode());
      discounts.getItems().add("NONE");
      discounts.setValue("NONE");
      itemListView.getItems().addAll(CartController.getInstance().showCart());
      update();
   }

   public void update(){
   if(discountIsValid)
      priceLabel.setText("cart price before discount="+CartController.getInstance().getCartPriceWithoutDiscountCode()+"\ncart price after discount="+CartController.getInstance().getCartPriceWithDiscountCode(discounts.getValue().toString()));
   }

   public void discountChange(ActionEvent actionEvent) {
      if(discounts.getValue().toString().equals("NONE")){
         discountIsValid=false;
      }else{
         discountIsValid=true;
      }
   }

   public void back(MouseEvent mouseEvent){
      SceneSwitcher.getInstance().setSceneTo("CartMenu");
   }

   public void buy(MouseEvent mouseEvent){
      if(validateAddress()==false) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("enter a valid address");
         alert.showAndWait();
         return;
      }
      String message="";
      if(discountIsValid) {
         message = CartController.getInstance().buy(address.getText(), discounts.getValue().toString());
      }else {
         message=CartController.getInstance().buy(address.getText());
      }
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(message);
      alert.showAndWait();
      if(message.startsWith("Successful")) return;
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


}
