package Project.View.Menus.MenuController.SellerMenuController;

import Project.Controller.ItemAndCategoryController;
import Project.Controller.UserController;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SellerAddProductMenu {
    @FXML private AnchorPane pane;
    @FXML
    private void back(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void imageChooserOpen(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("JPG","*.jpg")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        if(selected==null) return;
        Path source= Paths.get(selected.getPath());

        String fullPath="src/main/resources/Images/ItemImages/"+selected.getName();
        imageAddress.setText(source.toString());
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageName = selected.getName();
        hasChosenImage = true;
    }

    @FXML
    private void videoChooserOpen(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4","*.mp4"),
                new FileChooser.ExtensionFilter("Matroska","*.mkv")
        );
        File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
        if(selected==null) return;
        Path source= Paths.get(selected.getPath());

        String fullPath="src/main/resources/Images/ItemImages/"+selected.getName();
        videoAddress.setText(source.toString());
        Path des=Paths.get(fullPath);
        try {
            Files.copy(source,des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoName = selected.getName();
        hasChosenVideo = true;
    }

    private String imageName;
    private String videoName;
    private boolean hasChosenImage = false;
    private boolean hasChosenVideo = false;

    @FXML TextField itemName;
    @FXML TextField brandName;
    @FXML TextArea descriptionText;
    @FXML TextField price;
    @FXML TextField count;
    @FXML TextField category;

    @FXML Label itemNameError;
    @FXML Label brandNameError;
    @FXML Label priceError;
    @FXML Label countError;
    @FXML Label categoryError;

    @FXML Label imageAddress;
    @FXML Label videoAddress;

    private TextInputDialog dialog = new TextInputDialog("");

    @FXML
    private void createItem(){
        if(itemName.getText().isEmpty()){
            itemNameError.setText("Mandatory field");
            itemNameError.setTextFill(Color.rgb(255,0,0));
            return;
        }
        else {
            itemNameError.setText("");
        }

        if(brandName.getText().isEmpty()){
            brandNameError.setText("Mandatory field");
            brandNameError.setTextFill(Color.rgb(255,0,0));
            return;
        }
        else {
            brandNameError.setText("");
        }

        if(!isAPositiveDouble(price.getText())){
            priceError.setText("must be a positive number");
            priceError.setTextFill(Color.rgb(255,0,0));
            return;
        }
        else {
            priceError.setText("");
        }

        if(!isAPositiveInteger(count.getText())){
            countError.setText("must be a positive integer");
            countError.setTextFill(Color.rgb(255,0,0));
            return;
        }
        else {
            countError.setText("");
        }

        if(category.getText().isEmpty() || !ItemAndCategoryController.getInstance().isThereCategoryWithName(category.getText())){
            categoryError.setText("enter a valid category");
            categoryError.setTextFill(Color.rgb(255,0,0));
            return;
        }
        else {
            categoryError.setText("");
        }

        String image,video;
        if(hasChosenImage){
            image = imageName;
        }else {
            image = "default.png";
        }
        if(hasChosenVideo){
            video = videoName;
        }else {
            video="";
        }

        HashMap<String,String> attributeValue=new HashMap<>();
        ArrayList<String> attributeKey=ItemAndCategoryController.getInstance().getCategoryByName(category.getText()).getAttributes();

        if(attributeKey!=null) {
            for (String key : attributeKey) {

                setDialogText(key);
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(s -> attributeValue.put(key,s));
            }
        }

        ItemAndCategoryController.getInstance().addItem(itemName.getText(),brandName.getText(),descriptionText.getText(),Double.parseDouble(price.getText()),Integer.parseInt(count.getText()),category.getText(),attributeValue,image,video);
        hasChosenVideo = false;
        hasChosenImage = false;
        clearFields();
        sendAlert();
    }

    private void setDialogText(String attributeKey){
        dialog.setTitle("Enter Attribute Value");
        dialog.setHeaderText(attributeKey);
        dialog.setContentText("Please enter a value:");
        dialog.setGraphic(null);
    }

    private void sendAlert(){
        MusicManager.getInstance().playSound("notify");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Your Item will be added if and once admins approve. ");
        alert.setGraphic(null);
        alert.show();
        alert.setHeight(127);
        alert.setOnHidden(evt -> SceneSwitcher.getInstance().setSceneTo("SellerMenu"));
    }

    private void clearFields(){
        itemName.setText("");
        itemNameError.setText("");
        brandNameError.setText("");
        brandName.setText("");
        descriptionText.setText("");
        price.setText("");
        countError.setText("");
        category.setText("");
        priceError.setText("");
        count.setText("");
        categoryError.setText("");
        imageAddress.setText("");
        videoAddress.setText("");
    }


    public void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        itemName.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        itemNameError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        brandNameError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        brandName.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        descriptionText.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        price.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        countError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        category.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        priceError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        count.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        categoryError.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        imageAddress.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        videoAddress.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
    }



    protected static boolean isAPositiveInteger(String string){
        int anInt=-1;
        try{
            anInt = Integer.parseInt(string);
        }catch (Exception e){
            return false;
        }
        return anInt>0;
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
