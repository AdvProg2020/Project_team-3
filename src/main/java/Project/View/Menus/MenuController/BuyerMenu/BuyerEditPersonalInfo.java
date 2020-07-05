package Project.View.Menus.MenuController.BuyerMenu;

import Project.Controller.Controller;
import Project.Controller.UserController;
import Project.Model.Users.User;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.Menus.View;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

public class BuyerEditPersonalInfo {
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
        personalInfo.getItems().addAll(UserController.getInstance().viewPersonalInfo(UserController.getInstance().getCurrentOnlineUser().getUsername()));
        name.clear();
        surname.clear();
        email.clear();
        number.clear();
        String path=UserController.getInstance().userImagePath(UserController.getInstance().getCurrentOnlineUserUsername());
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
        UserController.getInstance().editPersonalInfo(UserController.getInstance().getCurrentOnlineUserUsername(),"Name",name.getText());
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
        UserController.getInstance().editPersonalInfo(UserController.getInstance().getCurrentOnlineUserUsername(),"Surname",surname.getText());
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
        UserController.getInstance().editPersonalInfo(UserController.getInstance().getCurrentOnlineUserUsername(),"Email",email.getText());
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
        UserController.getInstance().editPersonalInfo(UserController.getInstance().getCurrentOnlineUserUsername(),"Number",number.getText());
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
        if(UserController.getInstance().isValidEmail(text)){
            email.setStyle("-fx-text-fill: green;");
            return;
        }
        email.setStyle("-fx-text-fill: red;");
    }

    public void updateNumber(KeyEvent keyEvent) {
        String text=number.getText();
        if(UserController.getInstance().isValidPhoneNumber(text)){
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
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void back(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }


    public void removeImage(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        User user=Controller.getInstance().getCurrentOnlineUser();
        String path=UserController.getInstance().userImagePath(user.getUsername());
        File file=new File(path);
        if(!file.getName().equals("default.jpg"))file.delete();
        else return;
        File defaultImage=new File("src/main/resources/Images/default.jpg");
        try {
            imageView.setImage(new Image(String.valueOf(defaultImage.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        update();
    }

    public void changeImage(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        User user=Controller.getInstance().getCurrentOnlineUser();
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("JPG","*.jpg")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        if(selected==null) return;
        File removed=new File(UserController.getInstance().userImagePath(user.getUsername()));
        if(removed.getName().equals("default.jpg")){}
        else removed.delete();
        Path source= Paths.get(selected.getPath());
        String ext=selected.getName().substring(selected.getName().lastIndexOf("."));
        String fullPath="src/main/resources/Images/"+user.getUsername()+ext;
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path=UserController.getInstance().userImagePath(user.getUsername());
        File file=new File(path);
        try {
            imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        update();
    }

    public void changePassword(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(passwordTextField.getText().equals("")){
            showAlertBox("incorrect password field value","ERROR");
            return;
        }
        UserController.getInstance().editPersonalInfo(UserController.getInstance().getCurrentOnlineUserUsername(),"Password",passwordTextField.getText());
        showAlertBox("Successful","INFORMATION");
        passwordTextField.clear();
        update();
    }

    public void logout(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
}
