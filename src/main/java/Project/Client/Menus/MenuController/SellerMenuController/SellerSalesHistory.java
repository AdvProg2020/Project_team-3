package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.CLI.View;
import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Item;
import Project.Client.Model.Logs.SaleLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class SellerSalesHistory {
    public ListView <SaleLog> saleLog;
    public ListView <Item> itemListView;
    public Label SaleLogLabel;
    private final ObservableList<SaleLog> saleLogs= FXCollections.observableArrayList();
    private final ObservableList<Item> allItems=FXCollections.observableArrayList();
    private SaleLog selected;
    @FXML private AnchorPane pane;
    public void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        updateSaleLogListView();
        updateSaleLogItem();
    }

    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }


    class SaleLogImageText extends ListCell<SaleLog> {
        private HBox hBox = new HBox(5);
        private ImageView thumbImage = new ImageView();
        private Label label = new Label();

        public SaleLogImageText() {
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
        protected void updateItem(SaleLog item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                thumbImage.setImage(Client.getInstance().getImageFromServer("ShoppingBasket","user"));
                label.setText(item.toString());
                setGraphic(hBox);
            }
        }
    }

    private void updateSaleLogListView(){
        ArrayList<SaleLog> allLogs=MakeRequest.makeGetSaleLogRequest();
        if(allLogs.size()==0){
            SaleLogLabel.setText("you did not sell anyThing!");
            return;
        }
        for(SaleLog saleLog:allLogs){
            saleLogs.add(saleLog);
        }
        saleLog.setItems(saleLogs);
        saleLog.setCellFactory(new Callback<ListView<SaleLog>, ListCell<SaleLog>>() {
            @Override
            public ListCell<SaleLog> call(ListView<SaleLog> param) {
                return  new SaleLogImageText();
            }
        });
    }

    class  ItemImageText extends ListCell<Item>{
        private HBox hBox = new HBox(5);
        private ImageView thumbImage = new ImageView();
        private Label label = new Label();
        public ItemImageText() {
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
        protected  void updateItem(Item item,boolean empty){
            super.updateItem(item,empty);
            if(empty || item==null){
                setGraphic(null);
            }
            else{
                String path = "src/main/resources/Images/ItemImages/"+item.getImageName();
                File file = new File(path);
                thumbImage.setImage(Client.getInstance().getImageFromServer(item.getImageName(),"item"));
                label.setText(item.toString());
                setGraphic(hBox);
            }
        }
    }

    private void updateSaleLogItem(){
        saleLog.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int index=saleLog.getSelectionModel().getSelectedIndex();
                if(index==-1) return;
                SaleLog saleLog1=saleLog.getSelectionModel().getSelectedItem();
                if(saleLog1.equals(selected)) return;
                if(!saleLog1.equals(selected)) itemListView.getItems().clear();
                selected=saleLog1;
                String itemId=saleLog1.getItemId();
                if(MakeRequest.isThereProductWithId(itemId)) {
                    Item item = MakeRequest.getItem(itemId);
                    allItems.add(item);
                    itemListView.setItems(allItems);
                    itemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
                        @Override
                        public ListCell<Item> call(ListView<Item> param) {
                            return new ItemImageText();
                        }
                    });
                }
            }
        });

    }
}
