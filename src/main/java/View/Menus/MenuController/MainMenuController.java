package View.Menus.MenuController;

import Controller.CommercialController;
import Controller.ItemAndCategoryController;
import Model.Item;
import Model.Users.Admin;
import View.Menus.*;
import View.Menus.AdminMenu.AdminMenu;
import Controller.Controller;
import Controller.UserController;
import View.Menus.SellerMenu.SellerMenu;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainMenuController {


    public Menu menu;
    @FXML Button loginLogout;
    @FXML VBox commercial;
    @FXML Label slideCount;
    @FXML Button nextButton;
    @FXML Button previousButton;
    public void initialize(){
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
        if(allCommercials.isEmpty()==false){
            showCommercial(0);
        }else{
            commercial.setVisible(false);
            slideCount.setVisible(false);
            nextButton.setVisible(false);
            previousButton.setVisible(false);
        }
        loginLogout.setText("Login");
        loginHandler();
    }


    public void nextCommercial(MouseEvent mouseEvent) {
        if(slideCount.isVisible()) {
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
            int index = Integer.parseInt(slideCount.getText().split("/")[0])-1;
            if(index+1<allCommercials.size()){
                showCommercial(index+1);
            }else{
                showCommercial(0);
            }
        }
    }

    public void previousCommercial(MouseEvent mouseEvent) {
        if(slideCount.isVisible()) {
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
            int index = Integer.parseInt(slideCount.getText().split("/")[0])-1;
            if(index-1>-1){
                showCommercial(index-1);
            }else{
                showCommercial(allCommercials.size()-1);
            }
        }
    }

    private void fadeCommercial(){
        FadeTransition ft = new FadeTransition(Duration.millis(2000), commercial);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
    }
    private void showCommercial(int index){
        commercial.getChildren().clear();
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
        String commercialItemId=allCommercials.get(index);
        Item item = ItemAndCategoryController.getInstance().getItemById(commercialItemId);
        commercial.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(commercialItemId);
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });
        commercial.setPrefSize(230,345);
        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));
        Label name = new Label(item.getName());
        Label price = new Label(Double.toString(item.getPrice()));
        commercial.getChildren().add(imageView);
        commercial.getChildren().add(name);
        commercial.getChildren().add(price);
        index++;
        slideCount.setText(index+"/"+allCommercials.size());
        fadeCommercial();
    }

    public void registerBuyer(){
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(){
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void login(){
        SceneSwitcher.getInstance().setSceneAndWait("Login");
        loginHandler();
    }

    public void userzone(ActionEvent actionEvent) {
        if(UserController.getInstance().getCurrentOnlineUser() == null){
            SceneSwitcher.getInstance().setSceneAndWait("Login");
            loginHandler();
            return;
        }
        SceneSwitcher.getInstance().saveScene("MainMenu");
        if(    UserController.getInstance().getUserType().equals("Admin")){
            SceneSwitcher.getInstance().setSceneTo("AdminMenu");
        }else if(    UserController.getInstance().getUserType().equals("Seller")){
            SceneSwitcher.getInstance().setSceneTo("SellerMenu");
        }else{
            SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
        }
    }

    private void loginHandler(){
        if(Controller.getInstance().isLogin()==true){
            menu.getItems().remove(getMenuItemByName("Log In"));
            loginLogout.setText("Logout");
            addLogoutMenuItem();
        }else{
            loginLogout.setText("Login");
        }
    }


    private void addLoginMenuItem(){
        MenuItem login=new MenuItem("Log In");
        menu.getItems().add(0,login);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.getInstance().setSceneAndWait("Login");
                loginHandler();
            }
        });
    }

    private void addLogoutMenuItem(){
        MenuItem logout=new MenuItem("Logout");
        menu.getItems().add(logout);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserController.getInstance().logout();
                menu.getItems().remove(getMenuItemByName("Logout"));
                addLoginMenuItem();
                loginHandler();
                showLogoutAlertBox();
            }
        });
    }


    private MenuItem getMenuItemByName(String menuItemName){
        for(MenuItem menuItem:menu.getItems()){
            if(menuItem.getText().equals(menuItemName)) return menuItem;
        }
        return null;
    }

    private void showLogoutAlertBox(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("logout successful!");
        alert.show();
    }


    @FXML
    private void shop(){
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }

    public void cartMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);
    }

    public void ShopMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void loginLogout(MouseEvent mouseEvent) {
        if(loginLogout.getText().equals("Logout")){
            UserController.getInstance().logout();
            SceneSwitcher.getInstance().clearRecentScene();
            loginLogout.setText("Login");
            loginHandler();
            return;
        }
        if(loginLogout.getText().equals("Login")){
            SceneSwitcher.getInstance().setSceneAndWait("Login");
            loginHandler();
        }
    }
}
