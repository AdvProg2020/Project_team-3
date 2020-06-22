package View.Menus.MenuController;

import Controller.Controller;
import Controller.ItemAndCategoryController;
import Controller.UserController;
import Controller.CartController;
import Model.Cart;
import Model.Category;
import Model.Comment;
import Model.Item;
import Model.Users.Buyer;
import Model.Users.User;
import View.Menus.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemMenuController {
    private static String itemID;
    public Label itemNameLabelBigFont;
    public Label itemNameLabel;
    public Label categoryLabel;
    public Label brandLabel;
    public Label sellerLabel;
    public Label stockLabel;
    public Label priceLabel;
    public Label gradeLabel;
    public Label viewLabel;
    public ListView<Comment> commentListView;
    public ImageView itemImage;
    public Button playPause;
    public AnchorPane anchorPane;
    public MediaView mediaView;
    private final ObservableList<Comment> comments= FXCollections.observableArrayList();
    private final ObservableList<Item> allSimpleItems=FXCollections.observableArrayList();
    public ListView attributeListView;
    public ImageView ivTarget;
    public ImageView messageImageView;
    public ListView<Item> familyItemListView;
    private MediaPlayer mediaPlayer;
    private boolean playing=false;

    public void initialize(){
        ivTarget.setSmooth(true);
        ivTarget.setPreserveRatio(true);
        playPause.setText("play");
        ItemAndCategoryController.getInstance().addView(itemID);
        Item item= ItemAndCategoryController.getInstance().getItemById(itemID);
        item.addViewsBy(1);
        itemNameLabel.setText(item.getName());
        itemNameLabelBigFont.setText(item.getName());
        categoryLabel.setText(item.getCategoryName());
        brandLabel.setText(item.getBrand());
        sellerLabel.setText(item.getSellerName());
        stockLabel.setText(String.valueOf(item.getInStock()));
        gradeLabel.setText(String.valueOf(item.getRating()));
        priceLabel.setText(String.valueOf(item.getPrice()));
        viewLabel.setText(String.valueOf(item.getViewCount()));
        String path="src/main/resources/Images/ItemImages/"+item.getImageName();
        String messagePath="src/main/resources/Images/ItemImages/";
        String messageImageName=null;
        File file=new File(path);
        try {
            itemImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
            itemImage.setFitHeight(200);
            itemImage.setFitWidth(200);
            if(item.getInStock()==0){
                messageImageName=messagePath+"soldOut.png";
            }
            if(item.isInSale()==true && item.getInStock()!=0) {
                messageImageName=messagePath+"sale.png";
            }
            if(messageImageName!=null){
                File message=new File(messageImageName);
                messageImageView.setImage(new Image(String.valueOf(message.toURI().toURL())));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        addAttributeListView();
        commentListViewInitialize();
        commentListView.setCellFactory(new Callback<ListView<Comment>, ListCell<Comment>>() {
            @Override
            public ListCell<Comment> call(ListView<Comment> commentListView){
                return  new imageCommentTextCell();
            }
        });
        updateSimpleItem();
        initializeMediaPlayer();
    }


    public static void setItemID(String itemID) {
        ItemMenuController.itemID = itemID;
    }


    public static String getItemID() {
        return itemID;
    }


    public void comment(ActionEvent actionEvent) {
        User user=UserController.getInstance().getCurrentOnlineUser();
        if(user==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must login in our system for adding comment!");
            alert.show();
            return;
        }
        if((user instanceof Buyer)==false){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("only buyer Users can leave comment!");
            alert.show();
            return;
        }
        commentMenuController.setItemID(itemID);
        addCommentDialogBox();
    }


    public void rate(ActionEvent actionEvent) {
        int rating=getRating();
        if(rating==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please choose a rating first.");
            alert.showAndWait();
            return;
        }
        String message=ItemAndCategoryController.getInstance().rate(rating,itemID);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public  void addAttributeListView(){
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        HashMap<String , String> attributes=item.getAttributes();
        String print=null;
        for(String key:attributes.keySet()){
            print=key+"                   "+attributes.get(key);
            attributeListView.getItems().add(print);
        }
    }

    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }

    public void commentListViewInitialize(){
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        for(Comment comment:item.getAllComments()){
            comments.add(comment);
        }
        commentListView.setItems(comments);
    }

    public void playPauseButtonPressed(ActionEvent actionEvent) {
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        if(item.getVideoName().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("this item doesn't contain any video!");
            alert.showAndWait();
            return;
        }
        playing=!playing;
        if(playing){
            playPause.setText("Pause");
            mediaPlayer.play();
        }else{
            playPause.setText("Play");
            mediaPlayer.pause();
        }

    }

    public void addToCart(ActionEvent actionEvent) {
        User user=Controller.getInstance().getCurrentOnlineUser();
        if( user!=null &&(user instanceof Buyer)==false){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("you are not a buyer");
            alert.show();
            return;
        }
        Cart cart=Controller.getInstance().getCurrentShoppingCart();
        if(cart.includesItem(itemID)){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("you have added this item to your cart for increasing or decreasing item counts go to cart Menu.");
            alert.show();
            return;
        }
        CartController.getInstance().addItemToCart(itemID);
        System.out.println(itemID);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("item has been added to cart.");
        alert.show();
    }

    public void zoom(MouseEvent mouseEvent) throws ArrayIndexOutOfBoundsException {
        int x=(int)mouseEvent.getX();
        int y=(int)mouseEvent.getY();
        if(x<0 || y<0)return;
        if(x>150 || x<50) return;
        if(y>140 || y< 50)return;
        Image image=itemImage.getImage();
        Image newFrame=new WritableImage(image.getPixelReader(),x-50,y-50,50,50);
        ivTarget.setImage(newFrame);
    }

    public void removeImage(MouseEvent mouseEvent) {
        ivTarget.setImage(null);
    }



    class imageCommentTextCell extends ListCell<Comment>{
        private VBox vBox=new VBox(5);
        private ImageView imageView=new ImageView();
        private Label label=new Label();

        public imageCommentTextCell(){
            vBox.setAlignment(Pos.CENTER);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            vBox.getChildren().add(imageView);
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().add(label);
            setPrefWidth(USE_PREF_SIZE);
        }
        @Override
        protected  void updateItem(Comment comment,boolean empty){
            super.updateItem(comment,empty);
            if(empty||comment==null){
                setGraphic(null);
            }
            else {
                String path= UserController.getInstance().userImagePath(comment.getUsername());
                File file=new File(path);
                try {
                    imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String textToShow=comment.getUsername()+" : "+comment.getText();
                label.setText(textToShow);
                setGraphic(vBox);
            }
        }
    }

    public void initializeMediaPlayer(){
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        if(item.getVideoName().equals("")) return;
        String fullPath="src/main/resources/Images/ItemImages/"+item.getVideoName();
        System.out.println(fullPath +" hello");
        File file=new File(fullPath);
        Media media = null;
        try {
            media=new Media(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mediaPlayer=new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playing=false;
                playPause.setText("Play");
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.pause();
            }
        });

    }



    public void addCommentDialogBox(){
        Stage stage=new Stage();
        String path=SceneSwitcher.getInstance().getFXMLPath("CommentMenu");
        URL urls = null;
        try {
            urls = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(urls);
            stage.setScene(new Scene(parent));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }


    @FXML Polygon star1;
    @FXML Polygon star2;
    @FXML Polygon star3;
    @FXML Polygon star4;
    @FXML Polygon star5;
    public void star1Update(MouseEvent mouseEvent) {
     star1.setFill(Color.GOLD);
     star2.setFill(Color.WHITE);
     star3.setFill(Color.WHITE);
     star4.setFill(Color.WHITE);
     star5.setFill(Color.WHITE);
    }

    public void star2Update(MouseEvent mouseEvent) {
        star1.setFill(Color.GOLD);
        star2.setFill(Color.GOLD);
        star3.setFill(Color.WHITE);
        star4.setFill(Color.WHITE);
        star5.setFill(Color.WHITE);
    }

    public void star3Update(MouseEvent mouseEvent) {
        star1.setFill(Color.GOLD);
        star2.setFill(Color.GOLD);
        star3.setFill(Color.GOLD);
        star4.setFill(Color.WHITE);
        star5.setFill(Color.WHITE);
    }

    public void star4Update(MouseEvent mouseEvent) {
        star1.setFill(Color.GOLD);
        star2.setFill(Color.GOLD);
        star3.setFill(Color.GOLD);
        star4.setFill(Color.GOLD);
        star5.setFill(Color.WHITE);
    }

    public void star5Update(MouseEvent mouseEvent) {
        star1.setFill(Color.GOLD);
        star2.setFill(Color.GOLD);
        star3.setFill(Color.GOLD);
        star4.setFill(Color.GOLD);
        star5.setFill(Color.GOLD);
    }

    private int getRating(){
        System.out.println(star5.getFill());
        if(star5.getFill().equals(Color.GOLD)) return 5;
        if(star4.getFill().equals(Color.GOLD)) return 4;
        if(star3.getFill().equals(Color.GOLD)) return 3;
        if(star2.getFill().equals(Color.GOLD)) return 2;
        if(star1.getFill().equals(Color.GOLD)) return 1;
        return 0;
    }

    class simpleItemImageTextCell extends ListCell<Item> {
        private HBox vBox=new HBox(5);
        private ImageView imageView=new ImageView();
        private Label label=new Label();
        public simpleItemImageTextCell() {
            vBox.setAlignment(Pos.CENTER);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
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
            else {
                String path="src/main/resources/Images/ItemImages/"+item.getImageName();
                File file=new File(path);
                try {
                    imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String printItem=item.toString();
                label.setText(printItem);
                setGraphic(vBox);
            }
        }
    }

    public void updateSimpleItem(){
        ArrayList<Item> allItems=new ArrayList<>();
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        Category category=ItemAndCategoryController.getInstance().getCategoryByName(item.getCategoryName());
        for(String id:category.getAllItemsID()){
            if(id.equals(item.getId())) continue;
            allItems.add(ItemAndCategoryController.getInstance().getItemById(id));
        }
        allSimpleItems.setAll(allItems);
        familyItemListView.setItems(allSimpleItems);
        familyItemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                return  new simpleItemImageTextCell();
            }
        });
    }
    public void showItem(MouseEvent mouseEvent) {
        Item selected=familyItemListView.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            ItemMenuController.setItemID(selected.getId());
            SceneSwitcher.getInstance().setSceneTo("ItemMenu");
        }
    }



}

