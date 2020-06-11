package View.Menus.MenuController;

import Controller.Controller;
import Controller.SceneSwitcher;
import Controller.UserController;
import Model.Users.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MainMenuController {


    public Menu menu;

    public void initialize(){
        if(Controller.getInstance().isLogin()==true){
            MenuItem menuItem=new MenuItem("Logout");
            menu.getItems().add(menuItem);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    UserController.getInstance().logout();
                    menu.getItems().remove(menuItem);
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("logout Successful!");
                    alert.show();
                }
            });
        }
    }
    public void registerBuyer(){
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(){
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void registerAdmin(){
        SceneSwitcher.getInstance().setSceneTo("AdminRegister");
    }

    public void login(){
        SceneSwitcher.getInstance().setSceneTo("Login");
    }

    public void userzone(ActionEvent actionEvent) {
        System.out.println("owowkspwkps");
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        SceneSwitcher.getInstance().setSceneTo("AdminMenu");
    }
}
