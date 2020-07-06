package Project.View.Menus.MenuController.BuyerMenu;


import Project.Controller.UserController;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.CLI.View;
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
      discountList.getItems().addAll(UserController.getInstance().getBuyerDiscountCode());
      if(discountList.getItems().isEmpty())
         discountList.getItems().add("you dont have any discount codes");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
   }

   public void logout(ActionEvent actionEvent) {
      UserController.getInstance().logout();
      SceneSwitcher.getInstance().clearRecentScene();
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }
}
