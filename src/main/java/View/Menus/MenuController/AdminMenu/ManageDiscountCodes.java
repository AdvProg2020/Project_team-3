package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ManageDiscountCodes {
   @FXML
   private ListView listView;

   @FXML
   public void initialize() {
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
