package View.Menus.MenuController;

import Controller.Controller;
import Model.Users.Admin;
import View.Menus.*;
import View.Menus.AdminMenu.AdminMenu;
import Controller.UserController;
import View.Menus.SellerMenu.SellerMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class MainMenuController {


    public Menu menu;
    @FXML Button loginLogout;
    public void initialize(){
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
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setContentText("please login first");
            a.showAndWait();
            return;
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
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu");
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
