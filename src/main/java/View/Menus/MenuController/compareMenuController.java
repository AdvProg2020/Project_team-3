package View.Menus.MenuController;

import Controller.ItemAndCategoryController;
import Model.Item;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class compareMenuController {
    public ListView secondItemListView;
    public ListView firstItemListView;
    public ImageView secondItemImageView;
    public ImageView firstItemImageView;

    private static String firstItemID;
    private static String secondItemID;

    public static void setFirstItemID(String firstItemID) {
        compareMenuController.firstItemID = firstItemID;
    }

    public static void setSecondItemID(String secondItemID) {
        compareMenuController.secondItemID = secondItemID;
    }

    public void initialize(){
        Item first= ItemAndCategoryController.getInstance().getItemById(firstItemID);
        Item second=ItemAndCategoryController.getInstance().getItemById(secondItemID);
        String firstPath="src/main/resources/Images/ItemImages/"+first.getImageName();
        String secondPath="src/main/resources/Images/ItemImages/"+second.getImageName();
        File firstFile=new File(firstPath);
        File secondFile=new File(secondPath);
        try {
            firstItemImageView.setImage(new Image(String.valueOf(firstFile.toURI().toURL())));
            secondItemImageView.setImage(new Image(String.valueOf(secondFile.toURI().toURL())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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