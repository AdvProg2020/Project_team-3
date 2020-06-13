package View.Menus.MenuController.AdminMenu;

import Controller.SortAndFilterController;
import View.Menus.MenuController.Filter;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ManageProducts {
   @FXML private ListView listView;
   @FXML private ChoiceBox sortChoiceBox;
   @FXML private ListView filters;

   @FXML public void initialize() {
      sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
      sortChoiceBox.getItems().add("sort by view");
      sortChoiceBox.setValue("sort by view");
      update();
      filters.getItems().addAll(SortAndFilterController.getInstance().showActiveFilters());
   }

   public void update() {
      listView.getItems().clear();
      listView.getItems().addAll(SortAndFilterController.getInstance().show("Main"));

      if(listView.getItems().isEmpty()) {
         listView.getItems().add("there are no products right now");
         return;
      }
   }

   public void back(ActionEvent actionEvent)  {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }


   public void sort(ActionEvent actionEvent) {
      String sort=sortChoiceBox.getValue().toString();
      if(sort.equals("sort by view")){
         SortAndFilterController.getInstance().disableSort();
      }else {
         SortAndFilterController.getInstance().activateSort(sort); }
      update();
   }

   public void filterMenu(ActionEvent actionEvent) {
      Filter.setSceneName("ManageProducts");
      SceneSwitcher.getInstance().setSceneTo("Filters",323,444);
   }
}



