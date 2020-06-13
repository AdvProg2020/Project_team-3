import Controller.Database;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
        Database.getInstance().initiate();
        View.run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneSwitcher.getInstance().setStage(stage);
        stage.setTitle("MVC Shop team 3");
        initializeScreens();                                          //                                                 ______
        SceneSwitcher.getInstance().setSceneTo("MainMenu");          //   /\                                             \    /
        //stage.setMinWidth(1280);                                  //  /  \    note haye tahe main ro hamishe bekhonid  \  /
        stage.setMaxWidth(1280);                                  // /____\                                             \/
        //stage.setMinHeight(720);
        stage.setMaxHeight(720);
        stage.setResizable(false);
        stage.show();
    }

    public void initializeScreens() throws IOException {
        SceneSwitcher.getInstance().addSceneFXML("MainMenu","src/main/resources/fxml/MainMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerRegister","src/main/resources/fxml/SellerRegisterMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AdminRegister","src/main/resources/fxml/AdminRegisterMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerRegister","src/main/resources/fxml/BuyerRegisterMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AdminMenu","src/main/resources/fxml/AdminMenu/AdminMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageUsers","src/main/resources/fxml/AdminMenu/ManageUsers.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageRequests","src/main/resources/fxml/AdminMenu/ManageRequests.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageProducts","src/main/resources/fxml/AdminMenu/ManageProducts.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageUserIn","src/main/resources/fxml/AdminMenu/ManageUserIn.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageRequestIn","src/main/resources/fxml/AdminMenu/ManageRequestIn.fxml");
        SceneSwitcher.getInstance().addSceneFXML("Login","src/main/resources/fxml/LoginMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("EditDiscountCode","src/main/resources/fxml/AdminMenu/EditDiscountCode.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AddDiscountCode","src/main/resources/fxml/AdminMenu/AddDiscountCode.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageDiscountCodes","src/main/resources/fxml/AdminMenu/ManageDiscountCodes.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageCategories","src/main/resources/fxml/AdminMenu/ManageCategories.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AddCategory","src/main/resources/fxml/AdminMenu/AddCategory.fxml");
        SceneSwitcher.getInstance().addSceneFXML("EditCategory","src/main/resources/fxml/AdminMenu/EditCategory.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerMenu","src/main/resources/fxml/BuyerMenu/BuyerMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerMenu","src/main/resources/fxml/SellerMenu/SellerMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerAddProductMenu","src/main/resources/fxml/SellerMenu/SellerAddProductMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerDiscountCodes","src/main/resources/fxml/BuyerMenu/BuyerDiscountCodes.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerOrders","src/main/resources/fxml/BuyerMenu/BuyerOrders.fxml");
        SceneSwitcher.getInstance().addSceneFXML("CartMenu","src/main/resources/fxml/CartMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ShopMenu","src/main/resources/fxml/ShopMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("DiscountsMenu","src/main/resources/fxml/DiscountsMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("EditPersonalInfo","src/main/resources/fxml/BuyerMenu/EditPersonalInfoMenu.fxml");

    }

}
// hamme scene ha 1280 (tool) dar 720 (ertefa) bashan ke 16:9 bashe va ziadi ham bozorg nabashe
//set resizable ro false kardam ke be ham narize chizi !
// login menu tush ye ghabeliat bashe ke ba hyperlink bere register (?)
// in menu bar hammeja bashe
//havaseton bashe HAMMEJA BAYAD EMKANE LOGIN , REGISTER , LOGOUT , BACK vojod dashte bashe !!