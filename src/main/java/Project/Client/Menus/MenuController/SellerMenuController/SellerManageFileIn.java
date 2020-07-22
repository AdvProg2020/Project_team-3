package Project.Client.Menus.MenuController.SellerMenuController;


import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.ArrayList;

import java.util.Optional;

public class SellerManageFileIn {
   private static String fileName;

   public static void setFileName(String fileName) {
      SellerManageFileIn.fileName = fileName;
   }


   @FXML private ListView listView;
   @FXML private Label label;
   @FXML private Button removeItem;
   @FXML private ListView buyersListView;

   private TextInputDialog dialog = new TextInputDialog("");

   @FXML
   private void initialize(){
      MakeRequest.getItem(fileName);
      MusicManager.getInstance().setSongName("first.wav");
      label.setText("You are editing "+fileName);
      updateAttributes();
      updateBuyers();
   }


   private void updateAttributes(){
      Item item = MakeRequest.getItem(fileName);
      listView.getItems().clear();
      listView.getItems().add("Name:" +  item.getName());
      listView.getItems().add("Price:" +item.getPrice());
      listView.getItems().add("Description:"+item.getDescription());
   }

   private void updateBuyers(){
      buyersListView.getItems().clear();
      Item item = MakeRequest.getItem(fileName);
      ArrayList<String> buyers = item.getBuyerUserName();
      for(String buyer:buyers){
         buyersListView.getItems().add(buyer);
      }
      if(buyersListView.getItems().isEmpty()){
         buyersListView.getItems().add("This file has never been bought.");
      }
   }

   @FXML
   private void keySelect(){
      int index=listView.getSelectionModel().getSelectedIndex();
      if(index==-1)
         return;
      String string=listView.getItems().get(index).toString();
      int ind = string.indexOf(":");
      string = string.substring(0,ind);
      final String key = string.toLowerCase();
      // alert dialog miad value migire
      setDialogText("Enter a new value for "+key);
      Optional<String> result = dialog.showAndWait();
      result.ifPresent(s -> sendEditRequest(key,s));
   }

   private void setDialogText(String attributeKey){
      dialog.setTitle("Enter Attribute Value");
      dialog.setHeaderText(attributeKey);
      dialog.setContentText("Please enter a value:");
      dialog.setGraphic(null);
   }

   private void sendEditRequest(String key,String value){
      if(isAValidValue(key,value)) {
         MakeRequest.makeEditProductRequest(fileName,key,value);
         sendAlert("Editing request has been sent.", "ManageFileInSeller");
      }
      else {
         sendAlert("Error: New value is invalid.", "ManageFileInSeller");
      }
   }

   private boolean isAValidValue(String key,String value){
      if(value.isEmpty()) return false;
      if(key.equalsIgnoreCase("price")){
         return SellerAddProductMenu.isAPositiveDouble(value);
      }
      return true;
   }

   private void sendAlert(String text,String nextScene){
      MusicManager.getInstance().playSound("notify");
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("");
      alert.setHeaderText(null);
      alert.setContentText(text);
      alert.setGraphic(null);
      alert.show();
      alert.setHeight(127);
      alert.setOnHidden(evt -> SceneSwitcher.getInstance().setSceneTo(nextScene));
   }


   @FXML
   private void back(){
      SceneSwitcher.getInstance().setSceneTo("ManageFilesSeller");
   }

   @FXML
   private void logout(){
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }


   @FXML
   private void removeItem(){
      sendAlert(MakeRequest.makeRemoveProductSellerRequest(fileName),"SellerManageProductsMenu");
   }



}
