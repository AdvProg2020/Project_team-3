package View.Menus.MenuController.AdminMenu;

import Controller.CommercialController;
import Controller.Database;
import Controller.RequestController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ManageCommercials {

   @FXML private ListView commercials;

   @FXML public void initialize() {
      update();
   }

   public void update() {
      commercials.getItems().clear();
      commercials.getItems().addAll(CommercialController.getInstance().getAcceptedItemId());
      if(commercials.getItems().isEmpty())
      commercials.getItems().add("there no commercials right now");
   }
   public void removeCommercial(MouseEvent mouseEvent) {
      int index=commercials.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;

      String Id=commercials.getItems().get(index).toString();
      CommercialController.getInstance().removeCommercial(Id);
      update();
      commercials.getSelectionModel().clearSelection();
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
