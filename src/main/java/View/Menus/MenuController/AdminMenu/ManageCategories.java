package View.Menus.MenuController.AdminMenu;

import Controller.Database;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;


public class ManageCategories {
   @FXML ListView categoryList;

   @FXML public void initialize() {
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      categoryList.getItems().clear();
      for (Object requests : Database.getInstance().printFolderContent("Categories")) {
         categoryList.getItems().add(requests);
      }
      if(categoryList.getItems().isEmpty())
         categoryList.getItems().add("there are no categories right now");
   }

   public void categorySelect(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      int index=categoryList.getSelectionModel().getSelectedIndex();
      System.out.println(index);
      if(index==-1)
         return;
      String categoryName=categoryList.getItems().get(index).toString();
      categoryList.getSelectionModel().clearSelection();
      EditCategory.setCategoryName(categoryName);
      SceneSwitcher.getInstance().setSceneTo("EditCategory",353,415);
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
