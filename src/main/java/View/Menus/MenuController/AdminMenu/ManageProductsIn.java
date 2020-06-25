package View.Menus.MenuController.AdminMenu;

import Controller.ItemAndCategoryController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class ManageProductsIn {
   static String itemId;

   public static void setItemId(String itemId) {
      ManageProductsIn.itemId = itemId;
   }

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
