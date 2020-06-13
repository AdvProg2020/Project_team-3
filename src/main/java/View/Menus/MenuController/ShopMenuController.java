package View.Menus.MenuController;

import Controller.ItemAndCategoryController;
import Controller.SortAndFilterController;
import Controller.UserController;
import Model.Item;
import View.Menus.ItemMenu;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    @FXML private ChoiceBox sortChoiceBox;
    @FXML private ListView filters;

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
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label categoryLabel;

    @FXML
    private void initialize(){
        categoryName = "Main";
        sortChoiceBox.getItems().addAll(SortAndFilterController.getInstance().showAllAvailableSorts().split("\n"));
        sortChoiceBox.getItems().add("sort by view");
        sortChoiceBox.setValue("sort by view");
        filters.getItems().addAll(SortAndFilterController.getInstance().showActiveFilters());
        initLists();
        gridPane.setMaxHeight(500000);

        //bayad item haye shop ro bedast biarim va chizayi mesle filter sort va category ke mitonan
        //arrayliste item haro dastkari konan ro poshesh bedim , inja miaim item haro bargozari mikonim
        //catgory o filter o sort o in kosshera ro badan mizanim
    }

    private void initLists(){
        categoryLabel.setText("Currently Browsing "+categoryName);
        itemsToString = SortAndFilterController.getInstance().show(categoryName);
        for(String string : itemsToString){
            itemsID.add(string.substring(4,9));
        }
        int row=0,column=0;
        for(String itemID : itemsID){
            gridPane.add(createAndAddItem(itemID),column,row);
            column++;
            if(column==3){
                column=0;
                row++;
            }
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


    public void filterMenu(ActionEvent actionEvent) {
        Filter.setSceneName("ShopMenu");
        SceneSwitcher.getInstance().setSceneTo("Filters",323,444);
    }

    public void sort(ActionEvent actionEvent) {
        String sort=sortChoiceBox.getValue().toString();
        if(sort.equals("sort by view")){
            SortAndFilterController.getInstance().disableSort();
        }else {
            SortAndFilterController.getInstance().activateSort(sort); }
        initLists();
    }
}
