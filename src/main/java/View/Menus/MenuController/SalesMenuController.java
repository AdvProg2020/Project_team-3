package View.Menus.MenuController;

import Controller.ItemAndCategoryController;
import Controller.SaleAndDiscountCodeController;
import Controller.SortAndFilterController;
import Controller.UserController;
import Model.Item;
import Model.Sale;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class SalesMenuController {

    @FXML
    private GridPane gridPane;

    private int pageNumber=1;
    private ArrayList<String> allItemsID;
    private ArrayList<VBox> allItemsBox;

    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        // SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }


    @FXML
    private void initialize(){
        allItemsID = SaleAndDiscountCodeController.getInstance().getAllItemsIDWithSale();
        allItemsBox = new ArrayList<>();
        initLists();
    }

    private void initLists(){
        gridPane.getChildren().removeAll(allItemsBox);
        allItemsBox.clear();

        int row=0,column=0;
        for(String itemID : allItemsID){
            gridPane.add(createItem(itemID),column,row);
            column++;
            if(column==3){
                column=0;
                row++;
            }
        }
    }

    private VBox createItem(String itemID){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
        VBox itemBox = new VBox();
        itemBox.setOnMouseClicked(event -> {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().saveScene("DiscountsMenu");
            SceneSwitcher.getInstance().setSceneTo("ItemMenu",1280,750);
        });

        itemBox.setPrefSize(230,400);
        ImageView imageView = new ImageView(new Image(new File("src/main/resources/Images/ItemImages/"+item.getImageName()).toURI().toString(),230,230,false,false));

        Label nameAndPrice = new Label(item.getName() + "           " + (item.getPriceWithSale()));

        Image ratingImage=new Image(new File("src/main/resources/Images/star.png").toURI().toString(),108,20,false,false);
        ImageView star=new ImageView(ratingImage);
        double frameWidth = (item.getRating() / 5)*108;
        Rectangle mask = new Rectangle(frameWidth, 20);
        star.setClip(mask);

        itemBox.getChildren().add(new Label(" "));
        itemBox.getChildren().add(imageView);
        itemBox.getChildren().add(star);
        itemBox.getChildren().add(nameAndPrice);

        Sale sale = SaleAndDiscountCodeController.getInstance().getSaleById(item.getSaleId());
        itemBox.getChildren().add(new Label("Sale Start:"+sale.getStartTime().toString()));
        itemBox.getChildren().add(new Label("Sale End:"+sale.getEndTime().toString()));
        itemBox.getChildren().add(new Label("Ends In:"+LocalDateTime.now().until(sale.getEndTime().truncatedTo(ChronoUnit.HOURS),ChronoUnit.HOURS)+" Hours."));
        itemBox.getChildren().add(new Label("Sale is %"+sale.getOffPercentage()));

        allItemsBox.add(itemBox);
        return itemBox;
    }


    private boolean isAValidPage(int num){
        return false;
    }


    @FXML
    private void increasePage(){
        if(isAValidPage(pageNumber+1)){
            //pageNum.setText((pageNumber + 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber++;
            initLists();
        }

    }

    @FXML
    private void decreasePage(){
        if(isAValidPage(pageNumber-1)){
            //pageNum.setText((pageNumber - 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber--;
            initLists();
        }

    }
}
