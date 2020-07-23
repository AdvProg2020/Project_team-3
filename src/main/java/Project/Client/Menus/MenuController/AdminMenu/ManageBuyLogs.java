package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.CLI.View;
import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Item;
import Project.Client.Model.Logs.BuyLog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ManageBuyLogs {
   public ListView<BuyLog> buyLogListView;
   public ListView <Item> detailsListView;
   private final ObservableList<BuyLog> allBuyLogs= FXCollections.observableArrayList();
   private final ObservableList<Item> allItems=FXCollections.observableArrayList();
   public Label buyLogEmptyLabel;
   private BuyLog selected;
   @FXML
   private AnchorPane pane;

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
      initializeBuyLogListView();
      updateBuyLogDetail();
   }



   public void initializeBuyLogListView(){
      ArrayList<BuyLog> allLogs=MakeRequest.makeGetAllBuyLogs();
      if(allLogs.size()==0){
         buyLogEmptyLabel.setText("you did not bought anything!");
         return;
      }
      for(BuyLog buyLog:allLogs){
         allBuyLogs.add(buyLog);
      }
      buyLogListView.setItems(allBuyLogs);
      buyLogListView.setCellFactory(new Callback<ListView<BuyLog>, ListCell<BuyLog>>() {
         @Override
         public ListCell<BuyLog> call(ListView<BuyLog> param) {
            return  new BuyLogTextImageCell();
         }
      });
   }

   class BuyLogTextImageCell extends ListCell<BuyLog> {
      private HBox hBox = new HBox(5);
      private ImageView thumbImage = new ImageView();
      private Label label = new Label();

      public BuyLogTextImageCell() {
         hBox.setAlignment(Pos.CENTER);
         thumbImage.setPreserveRatio(true);
         thumbImage.setFitHeight(100);
         hBox.getChildren().add(thumbImage);
         label.setWrapText(true);
         label.setTextAlignment(TextAlignment.CENTER);
         hBox.getChildren().add(label);
         setPrefWidth(USE_COMPUTED_SIZE);
      }
      @Override
      protected void updateItem(BuyLog item, boolean empty) {
         super.updateItem(item, empty);
         if (empty || item == null) {
            setGraphic(null);
         } else {
            String path = "src/main/resources/Images/ShoppingBasket.jpg";
            File file = new File(path);
            try {
               thumbImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
               label.setText("Time: " + item.getTime() + "\nTotal Price: " + item.totalPrice() +"\nDiscounts:"+item.getDiscountGrandTotal()+"\nAddress:\n"+ item.getAddress());
               setGraphic(hBox);
            } catch (MalformedURLException e) {
               e.printStackTrace();
            }
         }
      }
   }


   class  ItemImageCellText extends ListCell<Item>{
      private HBox hBox = new HBox(5);
      private ImageView thumbImage = new ImageView();
      private Label label = new Label();
      public ItemImageCellText(){
         hBox.setAlignment(Pos.CENTER);
         thumbImage.setPreserveRatio(true);
         thumbImage.setFitHeight(100);
         hBox.getChildren().add(thumbImage);
         label.setWrapText(true);
         label.setTextAlignment(TextAlignment.CENTER);
         hBox.getChildren().add(label);
         setPrefWidth(USE_COMPUTED_SIZE);
      }
      @Override
      protected void updateItem(Item item,boolean empty){
         super.updateItem(item,empty);
         if (empty || item == null) {
            setGraphic(null);
         }
         else{
            String path="src/main/resources/Images/ItemImages/"+item.getImageName();
            File file=new File(path);
            try {
               thumbImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
               if(item.getState().equals("file")) {
                  label.setText("File Id:" + item.getId() + "   " + "price:" + item.getPrice() + "  " + "seller name:" + item.getSellerName());
               }else{
                     label.setText("Item Id:"+item.getId()+"   "+"price:"+item.getPrice()+"  "+"seller name:"+item.getSellerName()+ "  "+"items count:"+ selected.getItemsCount().get(item.getId()));
                  }
               setGraphic(hBox);
            } catch (MalformedURLException e) {
               e.printStackTrace();
            }

         }
      }
   }

   public void showFile(ActionEvent actionEvent) {
   /*   MusicManager.getInstance().playSound("Button");
      if(detailsListView.getSelectionModel().getSelectedItem()==null){
         MusicManager.getInstance().playSound("error");
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setContentText("you did not choose any item!");
         alert.showAndWait();
         return;
      }
      Item item=detailsListView.getSelectionModel().getSelectedItem();
      ItemMenuController.setItemID(item.getId());
      SceneSwitcher.getInstance().saveScene("BuyLog");
      SceneSwitcher.getInstance().setSceneTo("ItemMenu"); */
   }


   public void updateBuyLogDetail(){
      buyLogListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            int index=buyLogListView.getSelectionModel().getSelectedIndex();
            if(index==-1) return;
            BuyLog buyLog=buyLogListView.getItems().get(index);
            if(buyLog.equals(selected))return;
            if(!buyLog.equals(selected)) detailsListView.getItems().clear();
            selected=buyLog;
            buyLogListView.getSelectionModel().clearSelection();
            ArrayList<String> itemIds=buyLog.getAllItemsID();
            for(String id:itemIds){
               allItems.add(MakeRequest.getItem(id));
            }
            detailsListView.setItems(allItems);
            detailsListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
               @Override
               public ListCell<Item> call(ListView<Item> param) {
                 return new ItemImageCellText();
               }
            });
         }
      });
   }


   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("AdminMenu");
   }

   public void logout(ActionEvent actionEvent) {
      MakeRequest.makeLogoutRequest();
      SceneSwitcher.getInstance().clearRecentScene();
      Client.getInstance().setBankAccountToken("");
      SceneSwitcher.getInstance().setSceneTo("MainMenu");
   }

}
