package View.Menus.MenuController.BuyerMenu;


import Controller.UserController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;



public class DiscountsMenu {
   @FXML ListView discountList;


   @FXML public void initialize() {
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
