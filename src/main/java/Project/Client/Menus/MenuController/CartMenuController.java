package Project.Client.Menus.MenuController;

import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import Project.Client.Model.Cart;
import Project.Client.Model.Item;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;

public class CartMenuController {


    public  ListView <Item> itemListView;
    private final ObservableList<Item> allItems= FXCollections.observableArrayList();
    @FXML public Label totalPrice;
    @FXML private AnchorPane pane;

    public void initialize(){
        MakeRequest.makeUpdateDateAndTimeRequest();
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("second.wav");
        updateItemAgain();
        if(itemListView.getItems().size()==0) totalPrice.setText("you did not add any item!");
        else {
            totalPrice.setText(String.valueOf(MakeRequest.getCartPriceWithoutDiscount()));
        }
    }



    public void increaseItem(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        if(itemListView.getItems().size()==0){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you have not added any item!");
            alert.show();
            return;
        }
        Item selected=itemListView.getSelectionModel().getSelectedItem();
        if(selected==null){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please select an item in the cart!");
            alert.show();
            return;
        }
        System.out.println(Cart.getInstance().cartIncreaseDecrease(selected.getId(),1));
        System.out.println(Cart.getInstance().getAllItemCount().toString());
        itemListView.getItems().clear();
        updateItemAgain();
        itemListView.getSelectionModel().select(selected);
        totalPrice.setText(String.valueOf(MakeRequest.getCartPriceWithoutDiscount()));
    }

    public void decreaseItem(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        if(itemListView.getItems().size()==0){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you have not added any item!");
            alert.show();
            return;
        }
        Item selected=itemListView.getSelectionModel().getSelectedItem();
        if(selected==null){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please select an item in the cart!");
            alert.show();
            return;
        }
        Cart.getInstance().cartIncreaseDecrease(selected.getId(),-1);
        itemListView.getItems().clear();
        updateItemAgain();
        itemListView.getSelectionModel().select(selected);
        totalPrice.setText(String.valueOf(MakeRequest.getCartPriceWithoutDiscount()));
    }

    public void showItem(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        Item selected=itemListView.getSelectionModel().getSelectedItem();
        if(selected==null) {
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you have not added any item!");
            alert.show();
            return;
        }
        ItemMenuController.setItemID(selected.getId());
        SceneSwitcher.getInstance().saveScene("CartMenu");
        SceneSwitcher.getInstance().setSceneTo("ItemMenu");
    }

    public void clearCartPressed(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        Cart.getInstance().empty();
        itemListView.getItems().clear();
    }

    public void buy(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        if(itemListView.getItems().size()==0){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("cart is empty");
            alert.showAndWait();
            return;
        }
        if(MakeRequest.makeGetUserRequest()==null){
            MusicManager.getInstance().playSound("notify");
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("please login to buy items");
            alert.showAndWait();
            SceneSwitcher.getInstance().saveScene("CartMenu");
            SceneSwitcher.getInstance().setSceneAndWait("Login");
            return;
        }
        if(MakeRequest.makeGetUserRequest().getType().equals("Buyer")==false){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must be a buyer to buy items");
            alert.showAndWait();
            return;
        }
        SceneSwitcher.getInstance().saveScene("CartMenu");
        SceneSwitcher.getInstance().setSceneTo("PurchaseMenu");
    }

    public void back(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }

    public void goToShopMenu(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("ShopMenu");
    }


    class ItemTextCell extends ListCell<Item> {
        private HBox vBox=new HBox(5);
        private ImageView imageView=new ImageView();
        private Label label=new Label();

        public ItemTextCell(){
            vBox.setAlignment(Pos.CENTER_LEFT);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(60);
            vBox.getChildren().add(imageView);
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().add(label);
            setPrefWidth(USE_COMPUTED_SIZE);
        }
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || item==null) {
                setGraphic(null);
            }
            else{
                String path="src/main/resources/Images/ItemImages/"+item.getImageName();
                File file=new File(path);
                try {
                    imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String printItem=item.toString() +"\nitem Count: "+Cart.getInstance().getItemCount(item.getId())+"\ntotal Price:"+item.getPrice()*Cart.getInstance().getItemCount(item.getId());
                label.setText(printItem);
                setGraphic(vBox);
            }
        }
    }

    public void updateItemAgain(){
        Item item=null;
        for(String id:Cart.getInstance().getAllItemId()){
           item=MakeRequest.getItem(id);
            allItems.add(item);
        }
        itemListView.setItems(allItems);
        itemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                return new ItemTextCell();
            }
        });
    }




}
