package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SellerAddFile {
   
   @FXML
   private void back(){
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("SellerMenu");
   }
   @FXML
   private void logout(){
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

   @FXML TextField itemName;
   @FXML TextArea descriptionText;
   @FXML TextField price;
   @FXML Label itemNameError;
   @FXML Label priceError;


   @FXML private void createItem(){
      if(itemName.getText().isEmpty()){
         itemNameError.setText("Mandatory field");
         itemNameError.setTextFill(Color.rgb(255,0,0));
         return;
      }
      else {
         itemNameError.setText("");
      }
      if(!isAPositiveDouble(price.getText())){
         priceError.setText("must be a positive number");
         priceError.setTextFill(Color.rgb(255,0,0));
         return;
      }
      else {
         priceError.setText("");
      }
      sendAlert(MakeRequest.addFile(itemName.getText(),descriptionText.getText(),Double.parseDouble(price.getText())));
      clearFields();
   }


   private void sendAlert(String result){
      MusicManager.getInstance().playSound("notify");
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("");
      alert.setHeaderText(null);
      alert.setContentText(result);
      alert.setGraphic(null);
      alert.show();
      alert.setHeight(127);
      alert.setOnHidden(evt -> SceneSwitcher.getInstance().setSceneTo("SellerMenu"));
   }

   private void clearFields(){
      itemName.setText("");
      itemNameError.setText("");
      descriptionText.setText("");
      price.setText("");
      priceError.setText("");
}


   public void initialize(){
      MusicManager.getInstance().setSongName("first.wav");
      itemName.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
      itemNameError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
      descriptionText.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
      price.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
      priceError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
   }


   protected static boolean isAPositiveDouble(String string){
      double aDouble=-1;
      try{
         aDouble = Double.parseDouble(string);
      }catch (Exception e){
         return false;
      }
      return aDouble>0;
   }

}
