package Project.View.Menus.MenuController.AdminMenu;

import Project.Controller.Database;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.Menus.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageDiscountCodes {
   @FXML private ListView listView;
   @FXML private AnchorPane pane;
   @FXML
   public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      listView.getItems().clear();
      for (Object requests : Database.getInstance().printFolderContent("DiscountCodes")) {
         listView.getItems().add(requests);
      }
      if(listView.getItems().isEmpty())
         listView.getItems().add("there are no discount codes right now");
   }

   public void back(ActionEvent actionEvent)  {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void discountCodeSelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=listView.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String discountId=listView.getItems().get(index).toString().substring(16,21);
      listView.getSelectionModel().clearSelection();
      EditDiscountCode.setDiscountId(discountId);
      SceneSwitcher.getInstance().setSceneTo("EditDiscountCode",600,600);
   }

}
