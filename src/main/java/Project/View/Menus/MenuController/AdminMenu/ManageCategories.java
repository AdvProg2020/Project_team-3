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


public class ManageCategories {
   @FXML ListView categoryList;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
      View.setFonts(pane);
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
      SceneSwitcher.getInstance().setSceneTo("EditCategory");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }
}
