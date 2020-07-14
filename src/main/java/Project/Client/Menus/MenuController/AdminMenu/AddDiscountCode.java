package Project.Client.Menus.MenuController.AdminMenu;


import Project.Client.MakeRequest;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.util.ArrayList;


public class AddDiscountCode {
   @FXML private TextField percent;
   @FXML private TextField usage;
   @FXML private TextField maxDiscount;
   @FXML private DatePicker start;
   @FXML private DatePicker end;
   @FXML private ListView userList;
   @FXML private ListView selectedUserList;
   @FXML private AnchorPane pane;
   private ArrayList<String> allUserName=new ArrayList<>();

   @FXML
   public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }



   public void update() {
      userList.getItems().clear();
         for (Object buyer : MakeRequest.makeGetAllUserRequest("Buyer").split("\n")) {
            userList.getItems().add(buyer);
         }
      if(userList.getItems().isEmpty())
         userList.getItems().add("no user");
   }

   public Boolean valid(){
      if(percent.getStyle().toString().contains("red"))
         return false;
      if(usage.getStyle().toString().contains("red"))
         return false;
      if(maxDiscount.getStyle().toString().contains("red"))
         return false;
      return true;
   }

   public void create(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(valid()==false){
         showAlertBox("please fill all the fields correctly","WARNING");
         return;
      }
    try{
       int percentInt=Integer.parseInt(percent.getText());
       int usageInt=Integer.parseInt(usage.getText());
       int maxDiscountInt=Integer.parseInt(maxDiscount.getText());
       String startDate=start.getValue().toString();
       String endDate=end.getValue().toString();
       String message=MakeRequest.makeAddDiscountCodeRequest(percentInt,endDate,startDate,allUserName,usageInt,maxDiscountInt);
       showAlertBox(message,"INFORMATION");
       if(message.startsWith("Successful:")) {
          back(null);
       }else{
          reset(null);
       }
    }catch (Exception e){
       e.printStackTrace();
       showAlertBox("please fill all the fields correctly","WARNING");
    }
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void reset(MouseEvent mouseEvent) {
    MusicManager.getInstance().playSound("Button");
    percent.clear();
    usage.clear();
    maxDiscount.clear();
    selectedUserList.getItems().clear();
    update();
   }


   public void updatePercent(KeyEvent keyEvent) {
      String text=percent.getText();
      if(text.isEmpty())  return;
      try {
         int per = Integer.parseInt(text);
         if ((per > 100) || (per <= 0)) {
            percent.setStyle("-fx-text-fill: red;");
         } else{
            percent.setStyle("-fx-text-fill: green;");
         }
      }catch (Exception e){
         percent.setStyle("-fx-text-fill: red;");
      }
   }

   public void updateUsage(KeyEvent keyEvent) {
      String text=usage.getText();
      if(text.isEmpty() ) return;
      try {
         int number = Integer.parseInt(text);
         if(number<0){
            usage.setStyle("-fx-text-fill: red;");
            return;
         }
         usage.setStyle("-fx-text-fill: green;");
      }catch (Exception e){
         usage.setStyle("-fx-text-fill: red;");
      }
   }

   public void updateMaxDiscount(KeyEvent keyEvent) {
      String text=maxDiscount.getText();
      if(text.isEmpty() ) return;
      try {
         int number = Integer.parseInt(text);
         if(number<0){
            maxDiscount.setStyle("-fx-text-fill: red;");
            return;
         }
         maxDiscount.setStyle("-fx-text-fill: green;");
      }catch (Exception e){
         maxDiscount.setStyle("-fx-text-fill: red;");
      }
   }

   public void selectUser(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=userList.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String username=userList.getItems().get(index).toString();
      System.out.println(username);
      if(allUserName.contains(username)){
         allUserName.remove(username);
         selectedUserList.getItems().remove(username);
      }else {
         allUserName.add(username);
         selectedUserList.getItems().add(username);
      }
      userList.getSelectionModel().clearSelection();
   }

   private void showAlertBox(String message,String type){
      MusicManager.getInstance().playSound("notify");
      Alert alert = new Alert(Alert.AlertType.valueOf(type));
      alert.setContentText(message);
      alert.showAndWait();
   }

}
