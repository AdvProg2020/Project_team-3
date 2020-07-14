package Project.Client.Menus.MenuController.BuyerMenu;


import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;


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
      discountList.getItems().addAll(response);
      if(discountList.getItems().isEmpty())
         discountList.getItems().add("you dont have any discount codes");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
   }

   public void logout(ActionEvent actionEvent) {
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }
}
