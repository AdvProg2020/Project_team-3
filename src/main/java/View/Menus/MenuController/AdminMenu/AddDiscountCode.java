package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import Controller.SaleAndDiscountCodeController;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class AddDiscountCode {
   @FXML private TextField percent;
   @FXML private TextField usage;
   @FXML private TextField maxDiscount;
   @FXML private DatePicker start;
   @FXML private DatePicker end;
   @FXML private ListView userList;
   private ArrayList<String> allUserName=new ArrayList<>();

   @FXML
   public void initialize() {
      update();
   }

   public void update() {
      userList.getItems().clear();
         for (Object buyer : Database.getInstance().getAllUsername("Buyer")) {
            userList.getItems().add(buyer);
         }
      if(userList.getItems().isEmpty())
         userList.getItems().add("no user");
   }

   public void create(MouseEvent mouseEvent) {
    try{
       int percentInt=Integer.parseInt(percent.getText());
       int usageInt=Integer.parseInt(usage.getText());
       int maxDiscountInt=Integer.parseInt(maxDiscount.getText());
       String startDate=start.getValue().toString();
       String endDate=end.getValue().toString();
       String message=SaleAndDiscountCodeController.getInstance().addDiscountCode(percentInt,getDate(endDate),getDate(startDate),allUserName,usageInt,maxDiscountInt);
       Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setContentText(message);
       alert.showAndWait();
       if(message.startsWith("Successful:")) {
          back(null);
       }else{
          reset(null);
       }
    }catch (Exception e){
       e.printStackTrace();
       Alert alert = new Alert(Alert.AlertType.WARNING);
       alert.setContentText("please fill all the fields correctly");
       alert.showAndWait();
    }
   }

   public void back(ActionEvent actionEvent)  {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void reset(MouseEvent mouseEvent) {
    percent.clear();
    usage.clear();
    maxDiscount.clear();
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

   public void selectUser(MouseEvent mouseEvent) {
      int index=userList.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String username=userList.getItems().get(index).toString();
      System.out.println(username);
      if(allUserName.contains(username)){
         allUserName.remove(username);
      }else {
         allUserName.add(username);
      }
      userList.getSelectionModel().clearSelection();
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
