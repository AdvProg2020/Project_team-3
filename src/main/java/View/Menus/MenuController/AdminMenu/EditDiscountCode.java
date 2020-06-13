package View.Menus.MenuController.AdminMenu;

import Controller.SaleAndDiscountCodeController;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditDiscountCode {
   private static String discountId;
   @FXML
   private TextField percent;
   @FXML private TextField usage;
   @FXML private TextField maxDiscount;
   @FXML private DatePicker end;
   public static void setDiscountId(String discountId) {
      EditDiscountCode.discountId = discountId;
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageDiscountCodes");
   }

   public void changePercent(MouseEvent mouseEvent) {
      if(percent.getStyle().toString().contains("green")){
         int percentInt=Integer.parseInt(percent.getText());
         showAlertBox(SaleAndDiscountCodeController.getInstance().editDiscountCodePercentage(discountId,percentInt),"INFORMATION");
         return;
      }
      showAlertBox("incorrect percent value","ERROR");
   }

   public void changeMaxDiscount(MouseEvent mouseEvent) {
      if(maxDiscount.getStyle().toString().contains("green")){
         int maxDiscountInt=Integer.parseInt(maxDiscount.getText());
         showAlertBox(SaleAndDiscountCodeController.getInstance().editDiscountCodeMaxDiscount(discountId,maxDiscountInt),"INFORMATION");
         return;
      }
      showAlertBox("incorrect max discount value","ERROR");
   }

   public void changeUsage(MouseEvent mouseEvent) {
      if(usage.getStyle().toString().contains("green")){
         int usageInt=Integer.parseInt(usage.getText());
         showAlertBox(SaleAndDiscountCodeController.getInstance().editDiscountCodeUsageCount(discountId,usageInt),"INFORMATION");
         return;
      }
      showAlertBox("incorrect usage value","ERROR");
   }


   public void changeEndDate(MouseEvent mouseEvent) {
      try{
         String date=end.getValue().toString();
         showAlertBox(SaleAndDiscountCodeController.getInstance().editDiscountCodeEndTime(discountId,getDate(date)),"INFORMATION");
      }catch (Exception e){
         showAlertBox("error date field is empty","ERROR");
      }
   }

   public void delete(MouseEvent mouseEvent) {
      System.out.println("hah");
      System.out.println(discountId);
    String message= SaleAndDiscountCodeController.getInstance().deleteDiscountCode(discountId);
    if(message.startsWith("Error")) {
       showAlertBox(message, "ERROR");
       return;
    }else if(message.startsWith("Successful")){
       showAlertBox(message,"INFORMATION");
       SceneSwitcher.getInstance().setSceneTo("ManageDiscountCodes");
    }
   }

   private void showAlertBox(String message,String type){
      Alert alert = new Alert(Alert.AlertType.valueOf(type));
      alert.setContentText(message);
      alert.showAndWait();
   }

   public void updatePercent(KeyEvent keyEvent) {
      String text=percent.getText();
      if(text.isEmpty())  return;
      try {
         int per = Integer.parseInt(text);
         if ((per > 100) || (per <= 0)) {
            percent.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
         } else{
            percent.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
         }
      }catch (Exception e){
         percent.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
      }
   }

   public void updateUsage(KeyEvent keyEvent) {
      String text=usage.getText();
      if(text.isEmpty() ) return;
      try {
         int number = Integer.parseInt(text);
         if(number<0){
            usage.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            return;
         }
         usage.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
      }catch (Exception e){
         usage.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
      }
   }

   public void updateMaxDiscount(KeyEvent keyEvent) {
      String text=maxDiscount.getText();
      if(text.isEmpty() ) return;
      try {
         int number = Integer.parseInt(text);
         if(number<0){
            maxDiscount.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            return;
         }
         maxDiscount.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
      }catch (Exception e){
         maxDiscount.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
      }
   }

   private LocalDateTime getDate(String dateString){
      LocalDateTime date;
      dateString=dateString.substring(8,10)+"/"+dateString.substring(5,7)+"/"+dateString.substring(0,4)+" 12:12";
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      try{
         date = LocalDateTime.parse(dateString,dateTimeFormatter);
         return date;
      }catch (Exception e){
         System.out.println(View.ANSI_RED+"Invalid date. Try again."+View.ANSI_RESET);
         return null;
      }
   }
}
