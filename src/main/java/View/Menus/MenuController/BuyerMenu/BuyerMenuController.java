package View.Menus.MenuController.BuyerMenu;

import Controller.Controller;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.MenuController.ViewRequestUser;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.FileHandler;

public class BuyerMenuController {


    @FXML public ImageView userImage;
    @FXML private Label personalInfo;

    public void initialize(){
        MusicManager.getInstance().setSongName("first.wav");
        if(Controller.getInstance().isLogin()==true && Controller.getInstance().getCurrentOnlineUser() instanceof Buyer){
            User onlineUser=Controller.getInstance().getCurrentOnlineUser();
            String path=UserController.getInstance().userImagePath(onlineUser.getUsername());
            File file=new File(path);
            try {
                userImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        personalInfoUpdate();
    }

    public void personalInfoUpdate(){
        String message=UserController.getInstance().viewPersonalInfo(UserController.getInstance().getCurrentOnlineUser().getUsername());
        personalInfo.setText(message);
    }

    @FXML
    private void back(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }
    @FXML
    private void logout(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void viewOrders()
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("BuyLog");
    }

    @FXML
    private void viewDiscountCodes(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }

    @FXML
    private void viewShop(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    @FXML
    private void viewCart(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);
    }

    public void viewEditPersonalInfo(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerMenu");
        SceneSwitcher.getInstance().setSceneTo("BuyerEditPersonalInfo");
    }

    public void viewRequests(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("BuyerMenu");
        ViewRequestUser.setUsername(UserController.getInstance().getCurrentOnlineUserUsername());
        SceneSwitcher.getInstance().setSceneTo("ViewRequests");
    }
}


