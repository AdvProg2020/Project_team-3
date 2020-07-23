package Project.Client.Menus.MenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MenuController.BuyerMenu.FilePurchaseMenu;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class FileMenuController {

   private static String itemID;
   public Label itemNameLabelBigFont;
   public Label itemNameLabel;
   public Label sellerLabel;
   public Label priceLabel;
   public Label gradeLabel;
   public Label viewLabel;
   public ListView<Comment> commentListView;
   public ImageView itemImage;

   private final ObservableList<Comment> comments= FXCollections.observableArrayList();
   private final ObservableList<Item> allSimpleItems=FXCollections.observableArrayList();

   public ImageView ivTarget;


   @FXML public ImageView rating;

   @FXML private TextArea itemDetails;

   private ArrayList<Item> alternativeOptions;

   public void initialize(){
      MakeRequest.makeAddViewToItem(itemID);
      alternativeOptions = new ArrayList<>();
      MakeRequest.makeUpdateDateAndTimeRequest();
      MusicManager.getInstance().setSongName("second.wav");
      ivTarget.setSmooth(true);
      ivTarget.setPreserveRatio(true);
      Item item= MakeRequest.getItem(itemID);
      itemDetails.setText("Description:\n"+item.getDescription());
      itemNameLabel.setText(item.getName());
      itemNameLabelBigFont.setText(item.getName());
      sellerLabel.setText(item.getSellerName());
      gradeLabel.setText(String.valueOf(item.getRating()));
      priceLabel.setText(String.valueOf(item.getPrice()));
      viewLabel.setText(String.valueOf(item.getViewCount()));
      String messageImageName=null;
      itemImage.setImage(Client.getInstance().getImageFromServer(item.getImageName(),"item"));
      itemImage.setFitHeight(235);
      itemImage.setFitWidth(235);
      commentListViewInitialize();
      commentListView.setCellFactory(new Callback<ListView<Comment>, ListCell<Comment>>() {
         @Override
         public ListCell<Comment> call(ListView<Comment> commentListView){
            return  new imageCommentTextCell();
         }
      });
      //Image ratingImage=new Image(Client.getInstance().getImageFromServer("star","user"),200,28,false,false);
      rating.setImage(Client.getInstance().getImageFromServer("star","user",200,28));
      double frameWidth = (item.getRating() / 5)*200;
      Rectangle mask = new Rectangle(frameWidth, 28);
      rating.setClip(mask);
//      updateSimpleItem();
      // initializeMediaPlayer();
   }


   public static void setItemID(String itemID) {
      FileMenuController.itemID = itemID;
   }

   public static String getItemID() {
      return itemID;
   }

   public void comment(ActionEvent actionEvent) {
      MusicManager.getInstance().playSound("Button");
      if(MakeRequest.isTokenValid()==false){
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setContentText("please login first");
         alert.show();
         return;
      }
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

   public void buy(ActionEvent actionEvent) {
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
      if(MakeRequest.isSellerServerOnline(item.getSellerName())==false){
         MusicManager.getInstance().playSound("error");
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText("ERROR");
         alert.setContentText("this file seller is not online please try again later.");
         alert.show();
         return;
      }
      FilePurchaseMenu.setItemId(itemID);
      SceneSwitcher.getInstance().setSceneTo("FilePurchase",605,305);
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



   public void addCommentDialogBox(){
      SceneSwitcher.getInstance().setSceneAndWait("CommentMenu");
   }


   @FXML
   Polygon star1;
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
            imageView.setImage(Client.getInstance().getImageFromServer(item.getImageName(),"item"));
            String printItem=item.toString();
            label.setText(printItem);
            setGraphic(vBox);
         }
      }
   }

 /*  public void updateSimpleItem(){
      ArrayList<Item> allItems=new ArrayList<>();
      Item item=MakeRequest.getItem(itemID);
      Category category=MakeRequest.getCategory(item.getCategoryName());
      for(String id:category.getAllItemsID()){
         if(id.equals(item.getId())) continue;
         allItems.add(MakeRequest.getItem(id));
      }
      allSimpleItems.setAll(allItems);
      familyItemListView.setItems(allSimpleItems);
      familyItemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
         @Override
         public ListCell<Item> call(ListView<Item> param) {
            return  new simpleItemImageTextCell();
         }
      });
   } */

  /* public void showItem(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      Item selected=familyItemListView.getSelectionModel().getSelectedItem();
      if(selected!=null) {
         ItemMenuController.setItemID(selected.getId());
         SceneSwitcher.getInstance().setSceneTo("ItemMenu");
      }
   } */










}
