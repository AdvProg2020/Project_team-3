package View.Menus.MenuController.BuyerMenu;

import Controller.Controller;
import Model.Users.Buyer;
import Model.Users.User;
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
    private void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void viewOrders(){
        SceneSwitcher.getInstance().setSceneTo("BuyerOrders");
    }

    @FXML
    private void viewDiscountCodes(){
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }

    @FXML
    private void viewShop(){
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    @FXML
    private void viewCart(){
        String path=SceneSwitcher.getInstance().getFXMLPath("CartMenu");
        Stage stage=new Stage();
        stage.setHeight(427);
        stage.setWidth(620);
        URL urls = null;
        try {
            urls = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(urls);
            stage.setScene(new Scene(parent));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public void viewEditPersonalInfo(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerEditPersonalInfo");
    }
}


