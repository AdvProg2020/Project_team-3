package Project.Client.Menus.MenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;


import Project.Client.Model.Users.Admin;
import Project.Client.Model.Users.Seller;
import Project.Client.Model.Users.User;


import Project.Client.CLI.View;
import Project.Client.Menus.*;
import javafx.animation.AnimationTimer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Menu;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainMenuController {


    public Menu menu;
    @FXML Button loginLogout;
    @FXML VBox commercial;
    @FXML Label slideCount;
    @FXML Button nextButton;
    @FXML Button previousButton;

    private double timeSinceLastTransition = 0;
    private AnimationTimer animationTimer;
    @FXML private AnchorPane pane;
    public void initialize(){
        MakeRequest.makeUpdateDateAndTimeRequest();//////42
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("second.wav");
       /* ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
       if(allCommercials.isEmpty()==false){
            showCommercial(0);
        }else{
            commercial.setVisible(false);
            slideCount.setVisible(false);
            nextButton.setVisible(false);
            previousButton.setVisible(false);
        } */
        loginLogout.setText("Login");
        loginHandler();
//        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                timeSinceLastTransition += 0.016;//16 milisanie
//               // updateTime();
//            }
//        };
//        animationTimer.start();
    }

   /* private void updateTime(){
        if(timeSinceLastTransition >= 4){
            if(CommercialController.getInstance().getAcceptedItemId().size()>1)
            nextCommercial(null);
            timeSinceLastTransition = 0;
        }
    }


    public void nextCommercial(MouseEvent mouseEvent) {
        if(slideCount.isVisible()) {
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
            int index = Integer.parseInt(slideCount.getText().split("/")[0])-1;
            if(index+1<allCommercials.size()){
                showCommercial(index+1);
            }else{
                showCommercial(0);
            }
        }
    }

//    public void previousCommercial(MouseEvent mouseEvent) {
//        if(slideCount.isVisible()) {
//        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
//            int index = Integer.parseInt(slideCount.getText().split("/")[0])-1;
//            if(index-1>-1){
//                showCommercial(index-1);
//            }else{
//                showCommercial(allCommercials.size()-1);
//            }
//        }
//    }

    private void fadeCommercial(){
        FadeTransition ft = new FadeTransition(Duration.millis(2000), commercial);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
    }
    private void showCommercial(int index){
        commercial.getChildren().clear();
        ArrayList<String> allCommercials=CommercialController.getInstance().getAcceptedItemId();
        String commercialItemId=allCommercials.get(index);
        Item item = ItemAndCategoryController.getInstance().getItemById(commercialItemId);
        commercial.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(commercialItemId);
            SceneSwitcher.getInstance().saveScene("MainMenu");
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });
        commercial.setPrefSize(230,345);
        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));
        Label name = new Label(item.getName());
        Label price = new Label(Double.toString(item.getPrice()));
        commercial.getChildren().add(imageView);
        commercial.getChildren().add(name);
        commercial.getChildren().add(price);
        index++;
        slideCount.setText(index+"/"+allCommercials.size());
        fadeCommercial();
    } */

    public void registerBuyer(){
        animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
    }

    public void registerSeller(){
       // animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("SellerRegister");
    }

    public void login(){
        SceneSwitcher.getInstance().setSceneAndWait("Login");
        loginHandler();
    }

    public void userzone(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        if((Client.getInstance().getToken()==null)||(MakeRequest.isTokenValid()==false)){
            SceneSwitcher.getInstance().setSceneAndWait("Login");
            loginHandler();
            return;
        }
        //animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        if(MakeRequest.makeGetUserRequest().type.equals("Admin")){
            SceneSwitcher.getInstance().setSceneTo("AdminMenu");
        }else if(MakeRequest.makeGetUserRequest().type.equals("Seller")){
            SceneSwitcher.getInstance().setSceneTo("SellerMenu");
        }else if(MakeRequest.makeGetUserRequest().type.equals("Buyer")){
            SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
        }else{
            SceneSwitcher.getInstance().setSceneTo("AssistantMenu");
        }
    }

    private void loginHandler(){
        if(MakeRequest.isTokenValid()==true){  //this mean the user is loggined
            menu.getItems().remove(getMenuItemByName("Log In"));
            loginLogout.setText("Logout");
            addLogoutMenuItem();
        }else{
            loginLogout.setText("Login");
        }
    }


    private void addLoginMenuItem(){
        MenuItem login=new MenuItem("Log In");
        menu.getItems().add(0,login);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MusicManager.getInstance().playSound("Button");
                SceneSwitcher.getInstance().setSceneAndWait("Login");
                loginHandler();
            }
        });
    }

    private void addLogoutMenuItem(){
        MenuItem logout=new MenuItem("Logout");
        menu.getItems().add(logout);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MusicManager.getInstance().playSound("Button");
                MakeRequest.makeLogoutRequest();
                menu.getItems().remove(getMenuItemByName("Logout"));
                addLoginMenuItem();
                loginHandler();
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
        MusicManager.getInstance().playSound("notify");
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("logout successful!");
        alert.show();
    }


    @FXML
    private void shop(){
//        animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }

    public void cartMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        //animationTimer.stop();
        User user=MakeRequest.makeGetUserRequest();
        if(user instanceof Seller || user instanceof Admin) {
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("only buyers can view Cart Menu!");
            alert.showAndWait();
            return;
        }
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);

    }

    public void ShopMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        //animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void loginLogout(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        if(loginLogout.getText().equals("Logout")){
            MakeRequest.makeLogoutRequest();
            SceneSwitcher.getInstance().clearRecentScene();
            loginLogout.setText("Login");
            loginHandler();
            return;
        }
        if(loginLogout.getText().equals("Login")){
            SceneSwitcher.getInstance().setSceneAndWait("Login");
            loginHandler();
        }
    }

    public void saleShop(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        //animationTimer.stop();
        SceneSwitcher.getInstance().saveScene("MainMenu");
        SceneSwitcher.getInstance().setSceneTo("SalesMenu");
    }
}
