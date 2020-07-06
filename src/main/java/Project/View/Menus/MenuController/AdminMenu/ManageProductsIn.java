package Project.View.Menus.MenuController.AdminMenu;

import Project.Controller.ItemAndCategoryController;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.CLI.View;
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
      SceneSwitcher.getInstance().setSceneTo("ManageProducts");
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
