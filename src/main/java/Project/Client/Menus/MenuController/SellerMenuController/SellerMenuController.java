package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Model.Users.Seller;

import Project.Client.Menus.MenuController.ViewRequestUser;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import Server.Controller.TransactionController;
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
        MakeRequest.makeUpdateDateAndTimeRequest();
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        if(MakeRequest.isTokenValid() && MakeRequest.makeGetUserRequest() instanceof Seller){
            Image image= Client.getInstance().getImageFromServer(MakeRequest.makeGetUserRequest().getUsername(),"user");
            sellerImage.setImage(image);
            personalInfoUpdate();
        }
    }

    public void personalInfoUpdate(){
        Project.Client.Model.Users.Seller seller=(Project.Client.Model.Users.Seller) MakeRequest.makeGetUserRequest();
        personalInfo.setText(seller.getPersonalInfo());
    }

    @FXML
    private void back(){
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }
    @FXML
    private void logout(){
        MusicManager.getInstance().playSound("Button");
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
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
        SceneSwitcher.getInstance().saveScene("SellerMenu");
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
        ViewRequestUser.setUsername(MakeRequest.makeGetUserRequest().getUsername());
        SceneSwitcher.getInstance().setSceneTo("ViewRequests");
   }

   public void startAuction(){
       SceneSwitcher.getInstance().setSceneTo("SellerStartAuction");
   }

    public void goToTransactionMenu(ActionEvent actionEvent) {
//        MakeRequest.setMainAccountRequest();
        if(Client.getInstance().getBankAccountToken().equals("")){
            SceneSwitcher.getInstance().saveScene("SellerMenu");
            SceneSwitcher.getInstance().setSceneAndWait("bankLogin" ,600 , 526);
        }
        else {
            SceneSwitcher.getInstance().saveScene("SellerMenu");
            String result=MakeRequest.getBankAccountBalance();
            if(result.equals("token expired") || result.equals("token is invalid")){
                SceneSwitcher.getInstance().setSceneAndWait("bankLogin" ,600 , 526);
            }
            SceneSwitcher.getInstance().setSceneTo("TransactionMenu");
        }
    }

    public void addFile(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AddFile");
    }

}
