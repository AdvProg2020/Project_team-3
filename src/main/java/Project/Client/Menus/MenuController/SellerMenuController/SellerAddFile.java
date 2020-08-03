package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


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
      if(isFileValid()==false){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText("please select the file you wish to sell");
         alert.showAndWait();
         return;
      }

      String image,video;
      if(hasChosenImage){
         Client.getInstance().sendImageToServer(srcImagePath,desImagePath);
         image = imageName;
      }else {
         image = "default.png";
      }
      String message=MakeRequest.addFile(itemName.getText()+ext,descriptionText.getText(),Double.parseDouble(price.getText()),image,filePath.getText());
      if(message.startsWith("Successful")){
         //String desPath="src/main/resources/Files/"+MakeRequest.makeGetUserRequest().getUsername()+'_'+itemName.getText()+ext;
         //Client.getInstance().sendFileToServer(srcPath,desPath);
         sendAlert(message);
         return;
      }
      SceneSwitcher.getInstance().sendAlert(true,message);

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

    @FXML Label filePath;
    String srcPath;
    String ext;
   public void fileChooser(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      FileChooser fileChooser=new FileChooser();

      File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
      if(selected==null) return;
      Path source= Paths.get(selected.getPath());
      ext=selected.getName().substring(selected.getName().lastIndexOf("."));
      filePath.setText(selected.getPath());
      srcPath=selected.getPath();
   }

   private boolean isFileValid(){
      if(srcPath==null) return false;
      File file=new File(srcPath);
      return file.exists();
   }

   private String srcImagePath="";
   private String desImagePath="";

   @FXML private void imageChooserOpen(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      FileChooser fileChooser=new FileChooser();
      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("PNG","*.png"),
              new FileChooser.ExtensionFilter("JPG","*.jpg")
      );
      File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
      if(selected==null) return;
      String fullPath="src/main/resources/Images/ItemImages/"+selected.getName();
      imageAddress.setText(selected.getPath());
      imageName = selected.getName();
      hasChosenImage = true;
      srcImagePath=selected.getPath();
      desImagePath=fullPath;
   }

   private String imageName;
   private boolean hasChosenImage = false;
   @FXML Label imageAddress;

}
