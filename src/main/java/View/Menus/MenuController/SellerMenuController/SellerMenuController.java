package View.Menus.MenuController.SellerMenuController;

import Controller.Controller;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import View.Menus.MenuController.ViewRequestUser;
import View.Menus.SceneSwitcher;
import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class SellerMenuController {

    @FXML public ImageView sellerImage;
    @FXML private Label personalInfo;

    public void initialize(){
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
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void manageProducts(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageProductsMenu");
    }
    @FXML
    private void manageSales(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageOffs");
    }
    @FXML
    private void addProduct(){
        SceneSwitcher.getInstance().setSceneTo("SellerAddProductMenu");
    }
    @FXML
    private void viewSalesHistory(){
        SceneSwitcher.getInstance().setSceneTo("SellerSalesHistory");
    }
    @FXML
    private void viewShop(){
        SceneSwitcher.getInstance().saveScene("SellerMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }
    @FXML
    private void viewDiscounts(){
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }

   public void editPersonalInfo(ActionEvent actionEvent) {SceneSwitcher.getInstance().setSceneTo("SellerEditPersonalInfo"); }

    public void viewRequests(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("SellerMenu");
        ViewRequestUser.setUsername(UserController.getInstance().getCurrentOnlineUserUsername());
        SceneSwitcher.getInstance().setSceneTo("ViewRequests");
    }
}
