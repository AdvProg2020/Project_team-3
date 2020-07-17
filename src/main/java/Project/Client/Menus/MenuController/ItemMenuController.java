package Project.Client.Menus.MenuController;

import Project.Client.CLI.View;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Category;
import Project.Client.Model.Comment;
import Project.Client.Model.Item;
import Project.Client.Model.Users.Buyer;
import Project.Client.Model.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
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
    @FXML public ImageView rating;
    public Label videoLabel;
    public Label priceAfterSaleLabel;
    public ComboBox itemComoBox;
    private MediaPlayer mediaPlayer;
    @FXML private TextArea itemDetails;
    private boolean playing=false;

    private ArrayList<Item> alternativeOptions;

    public void initialize(){
        alternativeOptions = new ArrayList<>();
        MakeRequest.makeUpdateDateAndTimeRequest();
        View.setFonts(anchorPane);
        MusicManager.getInstance().setSongName("second.wav");
        attributeListView.getItems().clear();
        familyItemListView.getItems().clear();
        ivTarget.setSmooth(true);
        ivTarget.setPreserveRatio(true);
        playPause.setText("play");
        MakeRequest.makeAddViewToItem(itemID);
        Item item= MakeRequest.getItem(itemID);
        itemDetails.setText("Description:\n"+item.getDescription());
        itemNameLabel.setText(item.getName());
        itemNameLabelBigFont.setText(item.getName());
        categoryLabel.setText(item.getCategoryName());
        brandLabel.setText(item.getBrand());
        sellerLabel.setText(item.getSellerName());
        stockLabel.setText(String.valueOf(item.getInStock()));
        gradeLabel.setText(String.valueOf(item.getRating()));
        priceLabel.setText(String.valueOf(item.getPrice()));
        priceAfterSaleLabel.setText(String.valueOf(MakeRequest.makeGetItemPriceWithSaleRequest(itemID)));
        viewLabel.setText(String.valueOf(item.getViewCount()));
        updateAlternates();
        String path="src/main/resources/Images/ItemImages/"+item.getImageName();
        String messagePath="src/main/resources/Images/ItemImages/";
        String messageImageName=null;
        File file=new File(path);
        try {
            itemImage.setImage(new Image(String.valueOf(file.toURI().toURL())));
            itemImage.setFitHeight(235);
            itemImage.setFitWidth(235);
            if(item.getInStock()==0){
                messageImageName=messagePath+"soldOut.png";
            }
           if(MakeRequest.isInSaleItem(itemID)==true && item.getInStock()!=0) {
                messageImageName=messagePath+"sale.png";
            }
            if(messageImageName!=null){
                File message=new File(messageImageName);
                messageImageView.setImage(new Image(String.valueOf(message.toURI().toURL())));
                messageImageView.setTranslateX(-10);
                messageImageView.setTranslateY(230);
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
        Image ratingImage=new Image(new File("src/main/resources/Images/star.png").toURI().toString(),200,28,false,false);
        rating.setImage(ratingImage);
        double frameWidth = (item.getRating() / 5)*200;
        Rectangle mask = new Rectangle(frameWidth, 28);
        rating.setClip(mask);
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
        MusicManager.getInstance().playSound("Button");
        User user=MakeRequest.makeGetUserRequest();
        if(user==null){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must login in our system for adding comment!");
            alert.show();
            return;
        }
        if((user instanceof Buyer)==false){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("only buyer Users can leave comment!");
            alert.show();
            return;
        }
        commentMenuController.setItemID(itemID);
        commentMenuController.setFatherCommentId(null);
        addCommentDialogBox();
    }


    public void rate(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        int rating=getRating();
        if(rating==0){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please choose a rating first.");
            alert.showAndWait();
            return;
        }
        String message=MakeRequest.makeRatingRequest(rating,itemID);
        MusicManager.getInstance().playSound("notify");
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
        initialize();
    }

    public  void addAttributeListView(){
         Item item=MakeRequest.getItem(itemID);
         HashMap<String , String> attributes=item.getAttributes();
         String print=null;
           for(String key:attributes.keySet()){
             print=key+"                   "+attributes.get(key);
              attributeListView.getItems().add(print);
         }
    }

    public void back(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }

    public void commentListViewInitialize(){
        Item item=MakeRequest.getItem(itemID);
         for(Comment comment:item.getAllComments()){
              comments.add(comment);
         }
         commentListView.setItems(comments);
    }

    public void addToCart(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        User user=MakeRequest.makeGetUserRequest();
        Item item=MakeRequest.getItem(itemID);
        if( user!=null &&(user instanceof Buyer)==false){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("you are not a buyer");
            alert.show();
            return;
        }
        if(MakeRequest.cartIncludesItem(itemID)){
            MusicManager.getInstance().playSound("notify");
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("you have added this item to your cart for increasing or decreasing item counts go to cart Menu.");
            alert.show();
            return;
        }
            if(item.getInStock()==0){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("sold out Item!");
            alert.showAndWait();
            return;
        }
     // mirza   MakeRequest.addItemToCart(itemID);
        MusicManager.getInstance().playSound("notify");
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("item has been added to cart.");
        alert.show();
    }

    public void zoom(MouseEvent mouseEvent) throws ArrayIndexOutOfBoundsException {
        int x=(int)mouseEvent.getX();
        int y=(int)mouseEvent.getY();
        if(x < itemImage.getTranslateX() + 25){
            x = (int)itemImage.getTranslateX() + 25;
        }
        if(x > itemImage.getTranslateX() + 210){
            x = (int)itemImage.getTranslateX() + 210;
        }
        if(y <  itemImage.getTranslateY() + 25){
            y = (int)itemImage.getTranslateY() + 25;
        }
        if(y > itemImage.getTranslateX() + 210){
            y = (int)itemImage.getTranslateY() + 210;
        }
        Image image=itemImage.getImage();
        ivTarget.setImage(image);
        Rectangle2D viewPort=new Rectangle2D(x-25,y-25,50,50);
        ivTarget.setViewport(viewPort);
    }

    public void removeImage(MouseEvent mouseEvent) {
        ivTarget.setImage(null);
    }

    public void compare(ActionEvent actionEvent) {
        MusicManager.getInstance().playSound("Button");
        String selectedItem=(String) itemComoBox.getSelectionModel().getSelectedItem();
        if(selectedItem==null){
            MusicManager.getInstance().playSound("error");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select an item first!");
            alert.showAndWait();
            return;
        }
        String [] token=selectedItem.split(" ");
        String secondItemId=token[1].substring(3);
        compareMenuController.setFirstItemID(itemID);
        compareMenuController.setSecondItemID(secondItemId);
        SceneSwitcher.getInstance().setSceneAndWait("CompareMenu",600,469);
    }

    public void updateItemComoBox(MouseEvent mouseEvent) {
        Item item=MakeRequest.getItem(itemID);
        Category category= MakeRequest.getCategory(item.getCategoryName());
        ObservableList<String>allItems=FXCollections.observableArrayList();
           for(String id:category.getAllItemsID()){
              if(id.equals(itemID)) continue;
             allItems.add(MakeRequest.getItem(id).getName()+" id:"+id);
          }
            itemComoBox.setItems(allItems);
    }


    class imageCommentTextCell extends ListCell<Comment>{
        private VBox vBox=new VBox(5);
        private ImageView imageView=new ImageView();
        private TextArea textArea=new TextArea();
        private Label status=new Label();
        private Label username=new Label();
        private Button reply=new Button("reply");
        public imageCommentTextCell(){
            vBox.setAlignment(Pos.CENTER_LEFT);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(30);
           // hBox.getChildren().add(imageView);
           // label.setWrapText(true);
           // label.setTextAlignment(TextAlignment.CENTER);
           // hBox.getChildren().add(label);
            setPrefWidth(USE_PREF_SIZE);
        }
        @Override
      protected  void updateItem(Comment comment,boolean empty){
            super.updateItem(comment,empty);
            if(empty||comment==null){
                setGraphic(null);
            }
            else {
                addReplyAction(reply,comment);
                String path= MakeRequest.makeUserImagePathRequest();
                File file=new File(path);
                try {
                    imageView.setImage(new Image(String.valueOf(file.toURI().toURL())));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                status.setText("has Bought?:"+comment.hasBought());
                status.setTextFill(Color.rgb(0,0,255));
                textArea.setText(comment.getText());
                textArea.setStyle("-fx-font-size: 15");
                textArea.setEditable(false);
                username.setText(comment.getUsername());
                HBox hBox=new HBox(imageView,username);
                if(comment.getFatherCommentId()==null) hBox.getChildren().add(reply);
                vBox.getChildren().add(hBox);
                vBox.getChildren().add(textArea);
                HBox hBox1=new HBox(status);
                if(comment.getAllReplies().size()!=0){
                    Hyperlink hyperlink=new Hyperlink("view Replies");
                    hBox1.getChildren().add(hyperlink);
                    viewReplyAction(vBox,hyperlink,comment);
                }
                vBox.getChildren().add(hBox1);
                setGraphic(vBox);
            }
        }
    }

    public void viewReplyAction(VBox father,Hyperlink hyperlink, Comment main){
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Comment> commentObservableList=FXCollections.observableArrayList();
                ListView<Comment> allReps=new ListView<>();
                for(Comment comment:main.getAllReplies()){
                    commentObservableList.add(comment);
                }
                allReps.setItems(commentObservableList);
                VBox viewReps=new VBox();
                viewReps.setAlignment(Pos.CENTER_RIGHT);
                viewReps.getChildren().add(allReps);
                father.getChildren().add(viewReps);
                allReps.setCellFactory(new Callback<ListView<Comment>, ListCell<Comment>>() {
                    @Override
                    public ListCell<Comment> call(ListView<Comment> param) {
                        return new imageCommentTextCell();
                    }
                });
                hyperlink.setText("hide Replies!");
                hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        hyperlink.setText("view Replies");
                        father.getChildren().remove(viewReps);
                        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                hyperlink.setText("view Replies");
                                viewReplyAction(father,hyperlink,main);
                            }
                        });
                    }
                });
            }
        });

    }

    public void addReplyAction(Button button,Comment comment){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(MakeRequest.makeGetUserRequest()==null){
                    MusicManager.getInstance().playSound("error");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("you must login first!");
                    alert.showAndWait();
                    return;
                }
                if(!(MakeRequest.makeGetUserRequest() instanceof Buyer)){
                    MusicManager.getInstance().playSound("error");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("only Buyers can leave comment here!");
                    alert.showAndWait();
                    return;
                }
                commentMenuController.setItemID(itemID);
                commentMenuController.setFatherCommentId(comment.getCommentId());
                addCommentDialogBox();
            }
        });

    }

    public void initializeMediaPlayer(){
      /*  Item item=MakeRequest.getItem(itemID);
         if(item.getVideoName().equals("")){
            videoLabel.setText("no video for playing!");
            return;
        }
        String fullPath="src/main/resources/Images/ItemImages/"+item.getVideoName();
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
        }); */
    }

    public void playPauseButtonPressed(ActionEvent actionEvent) {
     /*   MusicManager.getInstance().playSound("Button");
        //    Item item=MakeRequest.makeGetItemById(itemID);
        if(item.getVideoName().equals("")){
            MusicManager.getInstance().playSound("error");
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
        } */
    }

    public void addCommentDialogBox(){
        SceneSwitcher.getInstance().setSceneAndWait("CommentMenu");
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
        Item item=MakeRequest.getItem(itemID);
      /*  Category category=MakeRequest.getCategoryByName(item.getCategoryName());
        for(String id:category.getAllItemsID()){
            if(id.equals(item.getId())) continue;
            allItems.add(MakeRequest.makeGetItemById(id));
        }
        allSimpleItems.setAll(allItems);
        familyItemListView.setItems(allSimpleItems);
        familyItemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                return  new simpleItemImageTextCell();
            }
        }); */
    }

    public void showItem(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        Item selected=familyItemListView.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            ItemMenuController.setItemID(selected.getId());
            SceneSwitcher.getInstance().setSceneTo("ItemMenu");
        }
    }

    protected boolean itemsAreEqual(Item item1,Item item2){
        if(!item1.getName().equals(item2.getName())) return false;
        if(!item1.getBrand().equals(item2.getBrand())) return false;
        if(!item1.getCategoryName().equals(item2.getCategoryName())) return false;
        for(String key:item1.getAttributes().keySet()){
            if(!item2.getAttributes().containsKey(key)) return false;
            if(!item1.getAttributes().get(key).equals(item2.getAttributes().get(key))) return false;
        }
        return true;
    }

    private void updateAlternates(){
            alternativeOptions.clear();
        Item thisItem = MakeRequest.getItem(itemID);
        for(Item item : MakeRequest.getAllItem()){
            if(itemsAreEqual(thisItem,item)){
                alternativeOptions.add(item);
            }
        }
        updateChoiceBox();
    }

    @FXML private ChoiceBox sellerChoiceBox;
    private void updateChoiceBox(){
        sellerChoiceBox.getItems().clear();
        ArrayList<String> sellers = new ArrayList<>();
        for(Item item:alternativeOptions){
            sellers.add(item.getSellerName());
        }
        sellerChoiceBox.getItems().addAll(sellers);
    }

    @FXML
    private void setSeller(){
        String sellerName = sellerChoiceBox.getValue().toString();
        for(Item item:alternativeOptions){
            if(item.getSellerName().equals(sellerName)){
                ItemMenuController.setItemID(item.getId());
                SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,720);
                return;
            }
        }
    }

}

