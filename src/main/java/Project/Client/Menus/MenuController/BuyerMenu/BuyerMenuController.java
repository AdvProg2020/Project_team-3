package Project.Client.Menus.MenuController.BuyerMenu;

import Project.Client.MakeRequest;
import Project.Client.Model.Users.Buyer;
import Server.Controller.Controller;

import Project.Client.Menus.MenuController.ViewRequestUser;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Server.Controller.UserController;
import Project.Client.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class BuyerMenuController {


    @FXML public ImageView userImage;
    @FXML private Label personalInfo;
    @FXML private AnchorPane pane;
    public void initialize(){
        Controller.getInstance().updateDateAndTime();
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
      /*  if(Controller.getInstance().isLogin()==true && Controller.getInstance().getCurrentOnlineUser() instanceof Buyer){
            User onlineUser=Controller.getInstance().getCurrentOnlineUser();
            String path=UserController.getInstance().userImagePath(onlineUser.getUsername());
            File file=new File(path);
            try {
                userImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } */
        //}
        personalInfoUpdate();
    }

    public void personalInfoUpdate(){
        Buyer buyer=(Buyer) MakeRequest.makeGetUserRequest();
        personalInfo.setText(buyer.getPersonalInfo());
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

    public void viewEditPersonalInfo() {
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


