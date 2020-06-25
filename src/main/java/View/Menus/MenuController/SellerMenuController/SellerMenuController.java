package View.Menus.MenuController.SellerMenuController;

import Controller.Controller;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import View.Menus.MenuController.ViewRequestUser;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;

public class SellerMenuController {

    @FXML public ImageView sellerImage;
    @FXML private Label personalInfo;
    @FXML private AnchorPane pane;
    public void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        if(Controller.getInstance().isLogin()==true && Controller.getInstance().getCurrentOnlineUser() instanceof Seller){
            User onlineUser=Controller.getInstance().getCurrentOnlineUser();
            String path=UserController.getInstance().userImagePath(onlineUser.getUsername());
            File file=new File(path);
            try {
                sellerImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            personalInfoUpdate();
        }
    }

    public void personalInfoUpdate(){
        String message=UserController.getInstance().viewPersonalInfo(UserController.getInstance().getCurrentOnlineUser().getUsername());
        personalInfo.setText(message);
    }

    @FXML
    private void back(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(){
        MusicManager.getInstance().playSound("Button");
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void manageProducts(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerManageProductsMenu");
    }
    @FXML
    private void manageSales(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerManageOffs");
    }
    @FXML
    private void addProduct()
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerAddProductMenu");
    }
    @FXML
    private void viewSalesHistory()
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerSalesHistory");
    }
    @FXML
    private void viewShop(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("SellerMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }
    @FXML
    private void viewDiscounts(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SalesMenu");
    }

   public void editPersonalInfo(ActionEvent actionEvent) {
       MusicManager.getInstance().playSound("Button");
       SceneSwitcher.getInstance().setSceneTo("SellerEditPersonalInfo");
   }

    public void editPersonalInfoButton() {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerEditPersonalInfo"); }

    public void viewRequests(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().saveScene("SellerMenu");
        ViewRequestUser.setUsername(UserController.getInstance().getCurrentOnlineUserUsername());
        SceneSwitcher.getInstance().setSceneTo("ViewRequests");
   }
}
