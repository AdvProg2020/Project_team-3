package View.Menus.MenuController;

import Controller.ItemAndCategoryController;
import Controller.SortAndFilterController;
import Controller.UserController;
import Model.Item;
import View.Menus.ItemMenu;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShopMenuController {

    private String categoryName = "Main";
    private ArrayList<String> itemsToString = new ArrayList<>();
    private ArrayList<String> itemsID = new ArrayList<>();
    private ArrayList<VBox> itemsVBox = new ArrayList<>();

    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }


    @FXML
    GridPane gridPane;

    @FXML
    private void initialize(){
        initLists();
        //bayad item haye shop ro bedast biarim va chizayi mesle filter sort va category ke mitonan
        //arrayliste item haro dastkari konan ro poshesh bedim , inja miaim item haro bargozari mikonim
        //har item mishe 1 Vbox 3 ghesmate , 1 aks + 1 gheymat (agge off bashe strikethrough + gheymat jadid) + 1 esm
        //click rooye vbox mibare maro be itemMenu on item
        //catgory o filter o sort o in kosshera ro badan mizanim


    }

    private void initLists(){
        categoryName = "Main";
        itemsToString = SortAndFilterController.getInstance().show(categoryName);
        for(String string : itemsToString){
            itemsID.add(string.substring(4,9));
        }
        int column=0,row=0;
        for(String itemID : itemsID){
            gridPane.add(createAndAddItem(itemID),column,row);
            if(column==2){
                column = 0;
                row++;
            }
            column++;
        }

    }

    private VBox createAndAddItem(String itemID){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        VBox itemBox = new VBox();

        itemBox.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("ItemMenu");
        });
        itemBox.setPrefSize(230,345);

        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));

        Label name = new Label(item.getName());
        Label price = new Label(Double.toString(item.getPrice()));

        itemBox.getChildren().add(imageView);
        itemBox.getChildren().add(name);
        itemBox.getChildren().add(price);

        itemsVBox.add(itemBox);
        return itemBox;
    }



}
