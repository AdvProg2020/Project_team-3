package Project;


import Project.Client.MakeRequest;


import Project.Client.Menus.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application{
    public static void main(String[] args) {
         MakeRequest.makeUpdateDateAndTimeRequest();
         launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneSwitcher.getInstance().setStage(stage);
        stage.setTitle("MVC Shop team 3");
        initializeScreens();
        SceneSwitcher.getInstance().setSceneTo("MainMenu",1280,720);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        MakeRequest.makeLogoutRequest();
    }

    public void initializeScreens() throws IOException {
        SceneSwitcher.getInstance().addSceneFXML("MainMenu","src/main/resources/fxml/MainMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerRegister","src/main/resources/fxml/SellerRegisterMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AdminRegister","src/main/resources/fxml/AdminRegisterMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AssistantRegister","src/main/resources/fxml/AssistantRegisterMenu.fxml");
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
        SceneSwitcher.getInstance().addSceneFXML("AdminEditPersonalInfo","src/main/resources/fxml/AdminMenu/AdminEditPersonalInfo.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerMenu","src/main/resources/fxml/BuyerMenu/BuyerMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerMenu","src/main/resources/fxml/SellerMenu/SellerMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerEditItemMenu","src/main/resources/fxml/SellerMenu/SellerEditItemMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerManageProductsMenu","src/main/resources/fxml/SellerMenu/SellerManageProductsMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerManageOffs","src/main/resources/fxml/SellerMenu/SellerManageOffs.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerEditOff","src/main/resources/fxml/SellerMenu/SellerEditOff.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerAddOff","src/main/resources/fxml/SellerMenu/SellerAddOff.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerAddProductMenu","src/main/resources/fxml/SellerMenu/SellerAddProductMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerDiscountCodes","src/main/resources/fxml/BuyerMenu/BuyerDiscountCodes.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyLog","src/main/resources/fxml/BuyerMenu/BuyLog.fxml");
        SceneSwitcher.getInstance().addSceneFXML("CartMenu","src/main/resources/fxml/CartMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ShopMenu","src/main/resources/fxml/ShopMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("DiscountsMenu","src/main/resources/fxml/BuyerMenu/DiscountsMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("BuyerEditPersonalInfo","src/main/resources/fxml/BuyerMenu/BuyerEditPersonalInfo.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerEditPersonalInfo","src/main/resources/fxml/SellerMenu/SellerEditPersonalInfo.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageProductsIn","src/main/resources/fxml/AdminMenu/ManageProductsIn.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ItemMenu","src/main/resources/fxml/ItemMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("CommentMenu","src/main/resources/fxml/CommentMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("PurchaseMenu","src/main/resources/fxml/BuyerMenu/PurchaseMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageCommercials","src/main/resources/fxml/AdminMenu/ManageCommercials.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageCommercialIn","src/main/resources/fxml/AdminMenu/ManageCommercialIn.fxml");
        SceneSwitcher.getInstance().addSceneFXML("CartMenu","src/main/resources/fxml/CartMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerSalesHistory","src/main/resources/fxml/SellerMenu/SellerSalesHistory.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ViewRequests","src/main/resources/fxml/ViewRequestUser.fxml");
        SceneSwitcher.getInstance().addSceneFXML("CompareMenu","src/main/resources/fxml/compareMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SalesMenu","src/main/resources/fxml/SalesMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("bankLogin","src/main/resources/fxml/bankLogin.fxml");
        SceneSwitcher.getInstance().addSceneFXML("bankRegisterAccount","src/main/resources/fxml/bankRegisterAccount.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AssistantMenu","src/main/resources/fxml/AssistantMenu/AssistantMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("TransactionMenu","src/main/resources/fxml/TransactionMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AssistantEditPersonalInfo","src/main/resources/fxml/AssistantMenu/AssistantEditPersonalInfo.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ViewAssistants","src/main/resources/fxml/BuyerMenu/ViewAssistants.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ChatMenu","src/main/resources/fxml/chat.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageChats","src/main/resources/fxml/AssistantMenu/ManageChats.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AdminTransaction","src/main/resources/fxml/AdminMenu/AdminTransactionMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("SellerStartAuction","src/main/resources/fxml/SellerMenu/SellerStartAuctionMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AllAuctionsMenu","src/main/resources/fxml/AllAuctionsMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AuctionMenu","src/main/resources/fxml/AuctionMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("FileShop","src/main/resources/fxml/FileShop.fxml");
        SceneSwitcher.getInstance().addSceneFXML("FileMenu","src/main/resources/fxml/FileMenu.fxml");
        SceneSwitcher.getInstance().addSceneFXML("AddFile","src/main/resources/fxml/SellerMenu/AddFile.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageFilesSeller","src/main/resources/fxml/SellerMenu/manageFiles.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageFileInSeller","src/main/resources/fxml/SellerMenu/manageFilesIn.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageFiles","src/main/resources/fxml/AdminMenu/ManageFiles.fxml");
        SceneSwitcher.getInstance().addSceneFXML("ManageFileIn","src/main/resources/fxml/AdminMenu/ManageFileIn.fxml");
    }

}


///har ja log out bud token account poch shavad!




