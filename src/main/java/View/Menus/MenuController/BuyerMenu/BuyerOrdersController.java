package View.Menus.MenuController.BuyerMenu;

import Controller.UserController;
import Model.Item;
import Model.Logs.BuyLog;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import Controller.*;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BuyerOrdersController {
    public ListView<BuyLog>buyLogListView;
    public ListView <Item> detailsListView;
    private final ObservableList<BuyLog>allBuyLogs= FXCollections.observableArrayList();
    private final ObservableList<Item> allItems=FXCollections.observableArrayList();
    public Label buyLogEmptyLabel;
    private BuyLog selected;

    public void initialize(){
        initializeBuyLogListView();
        updateBuyLogDetail();
    }



    private void initializeBuyLogListView(){
        Buyer buyer=(Buyer) Controller.getInstance().getCurrentOnlineUser();
        ArrayList<BuyLog> allLogs=buyer.getBuyLogs();
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
                    label.setText("Time: " + item.getTime() + "\nTotal Price: " + item.totalPrice() +"\nDiscounts:"+item.getDiscountGrandTotal()+"\nBuyer name: "+item.getBuyerName()+"\nAddress:\n"+ item.getAddress());
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
                    label.setText("Item Id:"+item.getId()+"   "+"price:"+item.getPrice()+"  "+"seller name:"+item.getSellerName()+ "  "+"items count:"+
                            selected.getItemsCount().get(item.getId()));
                    setGraphic(hBox);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void showItem(ActionEvent actionEvent) {
        if(detailsListView.getSelectionModel().getSelectedItem()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you did not choose any item!");
            alert.showAndWait();
            return;
        }
        Item item=detailsListView.getSelectionModel().getSelectedItem();
        ItemMenuController.setItemID(item.getId());
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("ItemMenu");
    }


    private void updateBuyLogDetail(){
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
                    allItems.add(ItemAndCategoryController.getInstance().getItemById(id));
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

    public void goToUserZone(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void goToCart(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("CartMenu",620,427);
    }

    public void goToShopMenu(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }

    public void goToDiscounts(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("DiscountsMenu");
    }


    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }

    public void viewEditPersonalInfo(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().saveScene("BuyLog");
        SceneSwitcher.getInstance().setSceneTo("EditPersonalInfo");
    }

}
