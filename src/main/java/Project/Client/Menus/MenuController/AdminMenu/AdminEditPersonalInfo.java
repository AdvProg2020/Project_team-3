package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Users.Admin;
import Project.Client.Model.Users.User;


import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminEditPersonalInfo {
   @FXML private TextField name;
   @FXML private TextField surname;
   @FXML private TextField email;
   @FXML private TextField number;
   @FXML private ListView personalInfo;
   @FXML private TextField passwordTextField;
   @FXML private ImageView imageView;
   @FXML private PasswordField passwordField;
   @FXML private CheckBox passwordCheckBox;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      passwordTextField.managedProperty().bind(passwordCheckBox.selectedProperty());
      passwordTextField.visibleProperty().bind(passwordCheckBox.selectedProperty());
      passwordField.managedProperty().bind(passwordCheckBox.selectedProperty().not());
      passwordField.visibleProperty().bind(passwordCheckBox.selectedProperty().not());
      passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
      update();
   }

   public void update(){
      personalInfo.getItems().clear();
      Admin admin=(Admin) MakeRequest.makeGetUserRequest();
      personalInfo.getItems().addAll(admin.getPersonalInfo());
      name.clear();
      surname.clear();
      email.clear();
      number.clear();
      String path=MakeRequest.makeUserImagePathRequest();
      File file=new File(path);
      try {
         imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
      } catch (MalformedURLException e) {
         e.printStackTrace();
      }
   }

   public void changeName(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(name.getText().isEmpty()) return;
      if(name.getStyle().toString().contains("red")){
         showAlertBox("incorrect name field value","ERROR");
         return;
      }
      MakeRequest.makeEditPersonalInfoRequest("Name",name.getText());
      showAlertBox("Successful","INFORMATION");
      update();
   }

   public void changeSurname(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(surname.getText().isEmpty()) return;
      if(surname.getStyle().toString().contains("red")){
         showAlertBox("incorrect surname field value","ERROR");
         return;
      }
      MakeRequest.makeEditPersonalInfoRequest("Surname",surname.getText());
      showAlertBox("Successful","INFORMATION");
      update();
   }

   public void changeEmail(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(email.getText().isEmpty()) return;
      if(email.getStyle().toString().contains("red")){
         showAlertBox("incorrect email field value","ERROR");
         return;
      }
      MakeRequest.makeEditPersonalInfoRequest("Email",email.getText());
      showAlertBox("Successful","INFORMATION");
      update();
   }

   public void changeNumber(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(number.getText().isEmpty()) return;
    if(number.getStyle().toString().contains("red")){
       showAlertBox("incorrect Number field value","ERROR");
       return;
    }
      MakeRequest.makeEditPersonalInfoRequest("Number",number.getText());
    showAlertBox("Successful","INFORMATION");
    update();
   }

   public void updateName(KeyEvent keyEvent) {
    String textName=name.getText();
    String textSurName=surname.getText();
    if(isAlphabetic(textName)==false){
       name.setStyle("-fx-text-fill: red;");
    }else{
       name.setStyle("-fx-text-fill: green;");
    }
    if(isAlphabetic(textSurName)==false){
       surname.setStyle("-fx-text-fill: red;");
    }else {
       surname.setStyle("-fx-text-fill: green;");
    }
   }

   public void updateEmail(KeyEvent keyEvent) {
      String text=email.getText();
      if(isValidEmail(text)){
         email.setStyle("-fx-text-fill: green;");
         return;
      }
      email.setStyle("-fx-text-fill: red;");
   }

   public void updateNumber(KeyEvent keyEvent) {
      String text=number.getText();
      if(isValidPhoneNumber(text)){
         number.setStyle("-fx-text-fill: green;");
         return;
      }
      number.setStyle("-fx-text-fill: red;");
   }

   public Boolean isAlphabetic(String text){
      Pattern p = Pattern.compile("[^a-zA-Z]");
      return !p.matcher(text).find();
   }

   private void showAlertBox(String message,String type){
      if(type.equalsIgnoreCase("Error")){
         MusicManager.getInstance().playSound("error");
      }else {
         MusicManager.getInstance().playSound("notify");
      }
      SceneSwitcher.getInstance().sendAlert(type.equalsIgnoreCase("error"),message);
   }

   public void back(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void removeImage(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      User user=MakeRequest.makeGetUserRequest();
      String path=MakeRequest.makeUserImagePathRequest();
      if(!path.contains("default.jpg"))MakeRequest.deleteImageFromServer(path);
      else return;
      imageView.setImage(Client.getInstance().getImageFromServer("default","user"));
      update();
   }

   public void changeImage(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      User user=MakeRequest.makeGetUserRequest();
      FileChooser fileChooser=new FileChooser();
      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("PNG","*.png"),
              new FileChooser.ExtensionFilter("JPG","*.jpg")
      );
      File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
      if(selected==null) return;
      String removed=MakeRequest.makeUserImagePathRequest();
      if(removed.contains("default.jpg")){}
      else MakeRequest.deleteImageFromServer(removed);
      String srcPath=selected.getPath();
      String ext=selected.getName().substring(selected.getName().lastIndexOf("."));
      String desPath="src/main/resources/Images/"+user.getUsername()+ext;
      Client.getInstance().sendImageToServer(srcPath,desPath);
      imageView.setImage(Client.getInstance().getImageFromServer(MakeRequest.makeGetUserRequest().username,"user"));
      update();
   }

   public void changePassword(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      if(passwordTextField.getText().equals("")){
         showAlertBox("incorrect password field value","ERROR");
         return;
      }
      MakeRequest.makeEditPersonalInfoRequest("Password",passwordTextField.getText());
      showAlertBox("Successful","INFORMATION");
      passwordTextField.clear();
      update();
   }

   public boolean isValidEmail(String email) {
      return getMatcher(email, "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$").matches();
   }

   public boolean isValidPhoneNumber(String number) {
      return getMatcher(number, "\\d\\d\\d\\d\\d(\\d+)$").matches();
   }

   public static Matcher getMatcher(String string, String regex) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(string);
   }
}
