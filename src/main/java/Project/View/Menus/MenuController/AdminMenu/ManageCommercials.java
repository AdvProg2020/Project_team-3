package Project.View.Menus.MenuController.AdminMenu;

import Project.Controller.CommercialController;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.CLI.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageCommercials {

   @FXML private ListView commercials;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      commercials.getItems().clear();
      commercials.getItems().addAll(CommercialController.getInstance().getAcceptedItemId());
      if(commercials.getItems().isEmpty())
      commercials.getItems().add("there no commercials right now");
   }
   public void removeCommercial(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
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
