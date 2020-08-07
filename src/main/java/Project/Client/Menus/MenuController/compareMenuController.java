package Project.Client.Menus.MenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.CLI.View;
import Project.Client.Model.Item;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class compareMenuController {
    @FXML public ListView secondItemListView;
    @FXML public ListView firstItemListView;
    @FXML public ImageView secondItemImageView;
    @FXML public ImageView firstItemImageView;
    @FXML private AnchorPane pane;

    private static String firstItemID;
    private static String secondItemID;


    public static void setFirstItemID(String firstItemID) {
        compareMenuController.firstItemID = firstItemID;
    }

    public static void setSecondItemID(String secondItemID) {
        compareMenuController.secondItemID = secondItemID;
    }

    public void initialize(){
         View.setFonts(pane);
        //MusicManager.getInstance().setSongName("second.wav");
          Item first=MakeRequest.getItem(firstItemID);
          Item second=MakeRequest.getItem(secondItemID);
          String firstPath="src/main/resources/Images/ItemImages/"+first.getImageName();
          String secondPath="src/main/resources/Images/ItemImages/"+second.getImageName();
        File firstFile=new File(firstPath);
        File secondFile=new File(secondPath);
        firstItemImageView.setImage(Client.getInstance().getImageFromServer(first.getImageName(),"item"));
        secondItemImageView.setImage(Client.getInstance().getImageFromServer(second.getImageName(),"item"));
        updateListViews(first,second);
    }

    private void updateListViews(Item first,Item second){
        HashMap<String,String> firstAttributes=first.getAttributes();
        HashMap<String,String> secondAttributes=second.getAttributes();
        firstItemListView.getItems().add("price:"+"           "+first.getPrice());
        secondItemListView.getItems().add("price:"+"           "+second.getPrice());
        firstItemListView.getItems().add("seller:"+"           "+first.getSellerName());
        secondItemListView.getItems().add("seller:"+"           "+second.getSellerName());
        firstItemListView.getItems().add("views:"+"           "+first.getViewCount());
        secondItemListView.getItems().add("views:"+"           "+second.getViewCount());
        firstItemListView.getItems().add("inStock:"+"           "+first.getInStock());
        secondItemListView.getItems().add("inStock:"+"           "+second.getInStock());
        for(String key:firstAttributes.keySet()){
            firstItemListView.getItems().add(key+"           "+firstAttributes.get(key));
        }
        for(String key:secondAttributes.keySet()){
            secondItemListView.getItems().add(key+"          "+secondAttributes.get(key));
        }
    }
}
