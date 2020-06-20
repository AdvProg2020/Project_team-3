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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainMenuController {


    public Menu menu;
    @FXML Button loginLogout;
    @FXML VBox commercial;
    public void initialize(){
        String commercialItemId= CommercialController.getInstance().getRandomItemId();
        if(commercialItemId.isEmpty()==false){
            Item item = ItemAndCategoryController.getInstance().getItemById(commercialItemId);
            commercial.setOnMouseClicked(event -> {
                ItemMenuController.setItemID(commercialItemId);
                SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
            });
            commercial.setPrefSize(230,345);
            ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));
            Label name = new Label(item.getName());
            Label price = new Label(Double.toString(item.getPrice()));
            commercial.getChildren().add(imageView);
            commercial.getChildren().add(name);
            commercial.getChildren().add(price);
        }else{
            commercial.setVisible(false);
        }
        loginLogout.setText("Login");
        loginHandler();
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
        SceneSwitcher.getInstance().saveScene("MainMenu");;
        SceneSwitcher.getInstance().setSceneTo("Login");
    }

    public void userzone(ActionEvent actionEvent) {
        if(UserController.getInstance().getCurrentOnlineUser() == null){
            SceneSwitcher.getInstance().setSceneTo("Login");
            return;
           // Alert a=new Alert(Alert.AlertType.ERROR);
           // a.setContentText("please login first");
           // a.showAndWait();
            //return;
        }
        if(    UserController.getInstance().getUserType().equals("Admin")){
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("AdminMenu");
        }else if(    UserController.getInstance().getUserType().equals("Seller")){
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("SellerMenu");
        }else{
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
        }
    }

    private void loginHandler(){
        if(Controller.getInstance().isLogin()==true){
            menu.getItems().remove(getMenuItemByName("Log In"));
            loginLogout.setText("Logout");
            addLogoutMenuItem();
        }
    }


    private void addLoginMenuItem(){
        MenuItem login=new MenuItem("Log In");
        menu.getItems().add(0,login);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.getInstance().saveScene("MainMenu");
                SceneSwitcher.getInstance().setSceneTo("Login");
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
        String path=SceneSwitcher.getInstance().getFXMLPath("CartMenu");
        URL urls = null;
        Scene cartScene;
        try {
            urls = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(urls);
            cartScene=new Scene(parent);
            Stage cartStage=new Stage();
            cartStage.setScene(cartScene);
            cartStage.show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShopMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void loginLogout(MouseEvent mouseEvent) {
        if(loginLogout.getText().equals("Logout")){
            UserController.getInstance().logout();
            loginLogout.setText("Login");
            loginHandler();
            return;
        }
        if(loginLogout.getText().equals("Login")){
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("Login");
        }
    }
}
