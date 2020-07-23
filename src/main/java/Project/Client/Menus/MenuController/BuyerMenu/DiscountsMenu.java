package Project.Client.Menus.MenuController.BuyerMenu;


import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;



public class DiscountsMenu {
   @FXML ListView discountList;
   @FXML private AnchorPane pane;

   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      String response=MakeRequest.makeGetBuyerDiscountCodesRequest();
      discountList.getItems().addAll(response.split("\n"));
      if(discountList.getItems().isEmpty())
         discountList.getItems().add("you dont have any discount codes");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
   }

   public void logout(ActionEvent actionEvent) {
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }
}
