package View.Menus.MenuController.AdminMenu;

import Controller.ItemAndCategoryController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageProductsIn {
   static String itemId;
   @FXML private AnchorPane pane;
   public static void setItemId(String itemId) {
      ManageProductsIn.itemId = itemId;
   }

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");}

   public void close(MouseEvent mouseEvent) {
       MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("ManageProductsIn");
      SceneSwitcher.getInstance().closeSecondStage();
   }

   public void delete(MouseEvent mouseEvent) {
       MusicManager.getInstance().playSound("Button");
    String message= ItemAndCategoryController.getInstance().deleteItem(itemId);
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText(message);
    alert.showAndWait();
    close(null);
   }

}
