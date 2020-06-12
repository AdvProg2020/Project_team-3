package View.Menus.MenuController.AdminMenu;

import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class AddDiscountCode {
   @FXML private TextField percent;
   @FXML private TextField usage;
   @FXML private TextField maxDiscount;
   @FXML private DatePicker start;
   @FXML private  DatePicker end;
   public void back(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void reset(MouseEvent mouseEvent) {
    percent.clear();
    usage.clear();
    maxDiscount.clear();
   }

   public void create(MouseEvent mouseEvent) {

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
         maxDiscount.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
      }catch (Exception e){
         maxDiscount.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
      }
   }

}
