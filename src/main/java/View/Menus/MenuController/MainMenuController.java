package View.Menus.MenuController;

import Controller.Controller;
import Model.Users.Admin;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MainMenuController {


    public Menu menu;

    public void initialize(){
        loginHandler();
    }

    public void registerBuyer(){
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(){
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void login(){
        SceneSwitcher.getInstance().setSceneTo("Login");
    }

    public void userzone(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        SceneSwitcher.getInstance().setSceneTo("AdminMenu");
    }

    private void loginHandler(){
        if(Controller.getInstance().isLogin()==true){
            menu.getItems().remove(getMenuItemByName("Log In"));
            if(Controller.getInstance().getCurrentOnlineUser() instanceof Admin) addAdminRegisterMenuItem();
            addLogoutMenuItem();
        }
    }

    private void addAdminRegisterMenuItem(){
        MenuItem adminRegister=new MenuItem("Register Admin");
        menu.getItems().add(0,adminRegister);
        adminRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.getInstance().setSceneTo("AdminRegister");
            }
        });
    }

    private void addLoginMenuItem(){
        MenuItem login=new MenuItem("Log In");
        menu.getItems().add(0,login);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                menu.getItems().remove(getMenuItemByName("Logout"));
                if(Controller.getInstance().getCurrentOnlineUser() instanceof Admin) menu.getItems().remove(getMenuItemByName("RegisterAdmin"));
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


}
