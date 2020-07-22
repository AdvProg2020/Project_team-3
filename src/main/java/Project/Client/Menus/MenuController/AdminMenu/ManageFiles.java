package Project.Client.Menus.MenuController.AdminMenu;


import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;


import Project.Client.Model.Category;
import Project.Client.Model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


public class ManageFiles {
   @FXML private ListView listView;

   @FXML public void initialize() {
      MusicManager.getInstance().setSongName("first.wav");
      update();
   }

   public void update() {
      listView.getItems().clear();
      Category category=MakeRequest.getCategory("File");

      for (String id : category.getAllItemsID()) {
         Item item=MakeRequest.getItem(id);
         if(item!=null) listView.getItems().add(item.showIdWithName());
      }
      if(listView.getItems().isEmpty()) {
         listView.getItems().add("there are no files right now");
         return;
      }
   }


   public void back(ActionEvent actionEvent)  {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   @FXML public void fileSelect(MouseEvent mouseEvent) {
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      if(listView.getItems().get(index).toString().isEmpty()) return;
      String id=listView.getItems().get(index).toString().substring(4,9);
      listView.getSelectionModel().clearSelection();
      if(MakeRequest.isThereProductWithId(id)) {
         ManageFileIn.setFileName(id);
         SceneSwitcher.getInstance().setSceneAndWait("ManageFileIn", 392, 173);
      }
   }

   public void refresh(ActionEvent actionEvent) {
      update();
   }
}
