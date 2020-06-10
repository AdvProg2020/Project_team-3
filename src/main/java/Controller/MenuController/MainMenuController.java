package Controller.MenuController;

import Controller.SceneSwitcher;

public class MainMenuController {


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
        //scene ro bebare rooye login menu
    }
}
