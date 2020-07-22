package Project.Client.Menus.MenuController.BuyerMenu;

import Project.Client.CLI.View;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;

import Project.Client.Model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FilePurchaseMenu {
   private static String itemId;
   @FXML Label name;
   @FXML Label price;
   @FXML Label path;

   @FXML
   private void initialize(){
     update();
   }

   private void update(){
      Item item=MakeRequest.getItem(itemId);
      name.setText(item.getName());
      price.setText(String.valueOf(item.getPrice()));
   }




   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("FileMenu");
   }

   public static void setItemId(String itemId) {
      FilePurchaseMenu.itemId = itemId;
   }

   public void fileChooser(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      FileChooser fileChooser=new FileChooser();
      File selected=fileChooser.showOpenDialog(SceneSwitcher.getInstance().getStage());
      if(selected==null) return;
      Path source= Paths.get(selected.getPath());
      System.out.println(source);
   }

   public void buy(MouseEvent mouseEvent) {

   }
}
